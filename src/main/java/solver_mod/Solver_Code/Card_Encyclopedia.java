package solver_mod.Solver_Code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Map;

public class Card_Encyclopedia {
    // AbstractCard has fields with all necessary info (color, rarity, damage,
    // type, etc.)
    public static Map<String, AbstractCard> dict;
    static {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            dict.put(c.name, c);
        }
    }
}