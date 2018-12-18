package solver_mod;

import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import solver_mod.Solver_Code.Action;
import solver_mod.Solver_Code.State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.combatRewardScreen;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnRandomNonCampfireRelic;

@SpireInitializer
public class SolverMod implements PostDungeonInitializeSubscriber,
        PostDrawSubscriber, PreTurnSubscriber, OnStartBattleSubscriber,
        SeeRewardSubscriber, PreCardRewardSubscriber {

    private int count, totalCount;
    private CardGroup cards = null;

    private void resetCounts() {
        totalCount = count = 0;
    }

    public SolverMod() {
        BaseMod.subscribe(this);
        resetCounts();
    }

    public static void initialize() {
        new SolverMod();
    }

    @Override
    public void receivePostDungeonInitialize() {
        resetCounts();
    }

    @Override
    public void receivePostDraw(AbstractCard c) {
        //AbstractRelic r = returnRandomNonCampfireRelic(AbstractRelic.RelicTier.COMMON);
        //AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f,
         //       Settings.HEIGHT / 2.0f, r);
    }


    
    @Override
    public void receivePreTurn() {
        AbstractRelic r = returnRandomNonCampfireRelic(AbstractRelic.RelicTier.COMMON);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f,
                Settings.HEIGHT / 2.0f, r);
        // gather all the possible targets in one list
        List<AbstractCreature> creatures =
                new ArrayList<AbstractCreature>(AbstractDungeon.getCurrRoom().monsters.monsters);
        creatures.add(AbstractDungeon.player);
        
        // fetch cards and potions
        List<AbstractCard> cards = new ArrayList<AbstractCard>(AbstractDungeon.player.hand.group);
        List<AbstractPotion> potions = AbstractDungeon.player.potions;
        
        // filter out cards that require too much energy
        Iterator<AbstractCard> iter = cards.iterator();
        while (iter.hasNext()) {
            AbstractCard c = iter.next();
            if (!c.freeToPlayOnce && c.costForTurn > AbstractDungeon.player.energy.energy) {
                iter.remove();
            }
        }


        //if (card.canUse(AbstractDungeon.player, (AbstractMonster)creat)) {
        //    AbstractDungeon.player.useCard(card, (AbstractMonster)creat, 0);
        //    return;
        //}

        
        Action act = new Action();
        State currentState = State.getState(),
              chosenState = currentState;
        for (AbstractCreature creature : creatures) {
            for (AbstractCard c : cards) {
                // instead of throwing an exception for invalid actions, return
                // null (to avoid using try-catch here)
                State permutation = act.use_a_card(c.name, creature.name,
                                                   currentState);
                if (permutation != null && permutation.score() > chosenState.score()) {
                    chosenState = permutation;
                }
            }
            
            for (AbstractPotion p : potions) {
                State permutation = act.use_a_potion(p.name, creature.name,
                                                     currentState);
                if (permutation != null && permutation.score() > chosenState.score()) {
                    chosenState = permutation;
                }
            }
        }
        State.setState(chosenState); // or some other way to "pick" this action/state
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
        screen.update();
    }
}