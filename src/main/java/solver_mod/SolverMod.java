package solver_mod;

import basemod.interfaces.PostDrawSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.returnRandomNonCampfireRelic;

@SpireInitializer
public class SolverMod implements PostExhaustSubscriber,
        PostBattleSubscriber, PostDungeonInitializeSubscriber,
        PostDrawSubscriber {

    private int count, totalCount;

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
    public void receivePostExhaust(AbstractCard c) {
        count++;
        totalCount++;
    }

    @Override
    public void receivePostBattle(AbstractRoom r) {
        System.out.println(count + " cards were exhausted this battle, " +
                totalCount + " cards have been exhausted so far this act.");
        count = 0;
    }

    @Override
    public void receivePostDungeonInitialize() {
        resetCounts();
    }

    @Override
    public void receivePostDraw(AbstractCard c) {
        AbstractRelic r = returnRandomNonCampfireRelic(AbstractRelic.RelicTier.COMMON);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f,
                Settings.HEIGHT / 2.0f, r);
    }
}