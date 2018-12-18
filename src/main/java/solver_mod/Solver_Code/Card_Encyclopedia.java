package solver_mod.Solver_Code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;
import java.util.Map;

//map c.name --> {attack -> int, block -> int, strength_it_adds -> int, health_it_adds -> int, dexterity_it_adds -> 
                //vulnerable_it_applies -> int, weak_it_applies -> int, frail_it_applies -> int, 
                //energy_it_gives -> int, energy_it_takes -> int, target -> String}

public class Card_Encyclopedia {
    // Relevant fields in AbstractCard: baseDamage, type (this is an enum with
    // values ATTACK, SKILL, POWER, STATUS, CURSE), cost (energy), baseBlock,
    // target (an enum with values ENEMY, ALL_ENEMY, SELF, NONE, SELF_AND_ENEMY,
    // ALL), color (enum with values RED, GREEN, BLUE, COLORLESS, CURSE)
    public static Map<String, AbstractCard> dict = new HashMap<String, AbstractCard>();
    static {
        update();
    }
    public static void update() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            dict.put(c.name, c);
        }
    }
}