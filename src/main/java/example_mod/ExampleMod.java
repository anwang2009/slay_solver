package example_mod;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;

@SpireInitializer
public class ExampleMod implements PostExhaustSubscriber,
        PostBattleSubscriber, PostDungeonInitializeSubscriber {

    private int count, totalCount;

    private void resetCounts() {
        totalCount = count = 0;
    }

    public ExampleMod() {
        BaseMod.subscribe(this);
        resetCounts();
    }

    public static void initialize() {
        new ExampleMod();
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

}