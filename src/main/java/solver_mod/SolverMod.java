package solver_mod;

import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import solver_mod.Solver_Code.Action;
import solver_mod.Solver_Code.State;

import java.util.*;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.combatRewardScreen;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnRandomNonCampfireRelic;

@SpireInitializer
public class SolverMod implements PreTurnSubscriber, OnStartBattleSubscriber,
        SeeRewardSubscriber, PreCardRewardSubscriber {

    private int count, totalCount;
    private CardGroup cards = null;

    public SolverMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new SolverMod();
    }
    
    @Override
    public void receivePreTurn() {
        //AbstractRelic r = returnRandomNonCampfireRelic(AbstractRelic.RelicTier.COMMON);
        //AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f,
        //       Settings.HEIGHT / 2.0f, r);
        // gather all the possible targets in one list
        List<AbstractCreature> creatures =
                new ArrayList<AbstractCreature>(AbstractDungeon.getCurrRoom().monsters.monsters);
        creatures.add(AbstractDungeon.player);
        
        /*// fetch cards and potions
        List<AbstractCard> cards = new ArrayList<AbstractCard>(AbstractDungeon.player.hand.group);
        List<AbstractPotion> potions = AbstractDungeon.player.potions;
        
        // filter out cards that require too much energy
        Iterator<AbstractCard> iter = cards.iterator();
        while (iter.hasNext()) {
            AbstractCard c = iter.next();
            if (!c.freeToPlayOnce && c.costForTurn > AbstractDungeon.player.energy.energy) {
                iter.remove();
            }
        }*/
        
        Action act = new Action();
        State currentState = new State();
        Queue<State> curStates = new LinkedList<>();
        curStates.add(currentState);

        currentState.initialise();
        State chosenState = currentState;

        int count = 0;
        while (!curStates.isEmpty()) {
            currentState = curStates.remove();
            if (count > 100000) {
                break;
                //throw new IllegalArgumentException("current state energy " + currentState.Energy);
            }
            for (AbstractCreature creature : creatures) {
                if (AbstractDungeon.player == creature) {
                    continue;
                }
                for (AbstractCard c : currentState.Cards) {
                    if (c.cost > currentState.Energy || !c.canUse(AbstractDungeon.player, (AbstractMonster)creature)) {
                        continue;
                    }
                    // instead of throwing an exception for invalid actions, return
                    // null (to avoid using try-catch here)
                    State permutation = act.use_a_card(c, (AbstractMonster)creature,
                            currentState);
                    curStates.add(permutation);
                    if (permutation != null && permutation.score() > chosenState.score()) {
                        chosenState = permutation;
                    }
                    count++;
                }

                for (AbstractPotion p : currentState.Potions) {
                    if (!p.canUse()) {
                        continue;
                    }
                    State permutation = act.use_a_potion(p, (AbstractMonster)creature,
                            currentState);
                    curStates.add(permutation);
                    if (permutation != null && permutation.score() > chosenState.score()) {
                        chosenState = permutation;
                    }
                    count++;
                }
            }
        }
        //if (count >= 0)
        //throw new IllegalArgumentException("" + count);

        // Trigger ideal actions for best state outcome
        //throw new IllegalArgumentException("num actions in chosen state: " + chosenState.actions.size());

        for (int i = 0; i < chosenState.actions.size(); i++) {
            Object abstract_obj = chosenState.actions.get(i);
            AbstractMonster target = chosenState.action_targets.get(i);
            if (abstract_obj instanceof AbstractCard) {
                AbstractCard ac = (AbstractCard) abstract_obj;
                if (ac.canUse(AbstractDungeon.player, target)) {
                    AbstractDungeon.player.useCard(ac, target, 0);
                }
            } else if (abstract_obj instanceof AbstractPotion) {
                AbstractPotion ap = (AbstractPotion) abstract_obj;
                if (ap.canUse()) {
                    ap.use(target);
                    AbstractDungeon.player.removePotion(ap);
                }
            }
        }

        // picking a specific state
        //State.setState(chosenState); // or some other way to "pick" this action/state
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        cards = new CardGroup(AbstractDungeon.player.masterDeck, CardGroupType.MASTER_DECK);
    }


    @Override
    public void receiveSeeReward() {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            return;
        }
        List<RewardItem> rewards = AbstractDungeon.combatRewardScreen.rewards;
        for (RewardItem reward : rewards) {
            if (reward.type != RewardItem.RewardType.CARD) {
                reward.hb.clicked = true;
                reward.update();
            }
        }
    }

    @Override
    public void receivePreCardReward(CardRewardScreen screen) {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.CARD_REWARD) {
            return;
        }
        List<AbstractCard> rewards = screen.rewardGroup;
        AbstractCard selected = rewards.get(0);
        selected.hb.hovered = true;
        selected.hb.justHovered = true;
        selected.hb.clicked = true;
        InputHelper.justClickedLeft = false;
        AbstractDungeon.effectsQueue.add(new FastCardObtainEffect(selected, selected.current_x, selected.current_y));
        if (screen.rItem != null) {
            AbstractDungeon.combatRewardScreen.rewards.remove(screen.rItem);
            AbstractDungeon.combatRewardScreen.positionRewards();
            if (AbstractDungeon.combatRewardScreen.rewards.isEmpty()) {
                AbstractDungeon.combatRewardScreen.hasTakenAll = true;
                AbstractDungeon.overlayMenu.proceedButton.show();
            }
        }
        AbstractDungeon.closeCurrentScreen();
    }
}