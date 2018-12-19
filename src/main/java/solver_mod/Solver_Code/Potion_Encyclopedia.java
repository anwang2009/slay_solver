package solver_mod.Solver_Code;

import com.megacrit.cardcrawl.potions.*;

import java.util.Arrays;
import java.util.List;

//map p.name --> {attack -> int, block -> int, strength_it_adds -> int, health_it_adds -> int, dexterity_it_adds -> 
                //vulnerable_it_applies -> int, weak_it_applies -> int, frail_it_applies -> int, 
                //energy_it_gives -> int, energy_it_takes -> int, target -> String}

public class Potion_Encyclopedia {
    // Relevant fields/methods in AbstractPotion: color (enum with values POISON,
    // BLUE, FIRE, GREEN, EXPLOSIVE, WEAK, FEAR, STRENGTH, and more), getPotency()
    // (the actual value associated with the potion's effect).
    public static final List<AbstractPotion> potions = Arrays.asList(
            new AncientPotion(), new AttackPotion(), new BlockPotion(),
            new BloodPotion(), new DexterityPotion(), new EnergyPotion(),
            new EntropicBrew(), new EssenceOfSteel(), new ExplosivePotion(),
            new FairyPotion(), new FearPotion(), new FirePotion(), new FocusPotion(),
            new FruitJuice(), new GamblersBrew(), new GhostInAJar(),
            new LiquidBronze(), new PoisonPotion(), new PotionSlot(0),
            new PowerPotion(), new RegenPotion(), new SkillPotion(),
            new SmokeBomb(), new SneckoOil(), new SpeedPotion(),
            new SteroidPotion(), new StrengthPotion(), new SwiftPotion(),
            new WeakenPotion()
    );
}