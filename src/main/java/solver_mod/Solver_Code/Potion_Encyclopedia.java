package solver_mod.Solver_Code;

import com.megacrit.cardcrawl.potions.*;

import java.util.HashMap;
import java.util.Map;

//map p.name --> {attack -> int, block -> int, strength_it_adds -> int, health_it_adds -> int, dexterity_it_adds -> 
                //vulnerable_it_applies -> int, weak_it_applies -> int, frail_it_applies -> int, 
                //energy_it_gives -> int, energy_it_takes -> int, target -> String}

public class Potion_Encyclopedia {
    // AbstractPotion has fields for color, rarity, size, and a getPotency()
    // method. These appear to be the only attributes in each potion class
    public static Map<String, AbstractPotion> dict;
    private static AbstractPotion[] potions = {
            new AncientPotion(), new AttackPotion(), new BlockPotion(),
            new BloodPotion(), new DexterityPotion(), new EnergyPotion(),
            new EntropicBrew(), new EssenceOfSteel(), new ExplosivePotion(),
            new FairyPotion(), new FirePotion(), new FocusPotion(),
            new FruitJuice(), new GamblersBrew(), new GhostInAJar(),
            new LiquidBronze(), new PoisonPotion(), /*new PotionSlot(),*/
            new PowerPotion(), new RegenPotion(), new SkillPotion(),
            new SmokeBomb(), new SneckoOil(), new SpeedPotion(),
            new SteroidPotion(), new StrengthPotion(), new SwiftPotion(),
            new WeakenPotion()
            };
    static {
        for (AbstractPotion p : potions) {
            dict.put(p.name, p);
        }
    }
}