package solver_mod.Solver_Code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Map;

//map c.name --> {attack -> int, block -> int, strength_it_adds -> int, health_it_adds -> int, dexterity_it_adds -> 
                //vulnerable_it_applies -> int, weak_it_applies -> int, frail_it_applies -> int, 
                //energy_it_gives -> int, energy_it_takes -> int, target -> String}

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