package solver_mod.Solver_Code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.green.Backflip;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;
import java.util.ArrayList;

//map c.name --> {attack -> int, block -> int, strength_it_adds -> int, health_it_adds -> int, dexterity_it_adds -> 
                //vulnerable_it_applies -> int, weak_it_applies -> int, frail_it_applies -> int, 
                //energy_it_gives -> int, energy_it_takes -> int, target -> String}

public class Card_Encyclopedia {
    // Relevant fields in AbstractCard: baseDamage, type (this is an enum with
    // values ATTACK, SKILL, POWER, STATUS, CURSE), cost (energy), baseBlock,
    // target (an enum with values ENEMY, ALL_ENEMY, SELF, NONE, SELF_AND_ENEMY,
    // ALL), color (enum with values RED, GREEN, BLUE, COLORLESS, CURSE)
    public static List<AbstractCard> cards = new ArrayList<>();
    public static void update() {
        //red cards
        cards.add(new Bash());
        cards.add(new Defend_Red());
        cards.add(new Strike_Red());
        cards.add(new Anger());
        cards.add(new Armaments());
        cards.add(new BodySlam());
        cards.add(new Clash());
        cards.add(new Cleave());
        cards.add(new Clothesline());
        cards.add(new Flex());
        cards.add(new Havoc());
        cards.add(new Headbutt());
        cards.add(new HeavyBlade());
        cards.add(new IronWave());
        cards.add(new PerfectedStrike());
        cards.add(new PommelStrike());
        cards.add(new ShrugItOff());
        cards.add(new SwordBoomerang());
        cards.add(new ThunderClap());
        cards.add(new TrueGrit());
        cards.add(new TwinStrike());
        cards.add(new Warcry());
        cards.add(new WildStrike());
        cards.add(new BattleTrance());
        cards.add(new BloodForBlood());
        cards.add(new Bloodletting());
        cards.add(new BurningPact());
        cards.add(new Carnage());
        cards.add(new Combust());
        cards.add(new Corruption());
        cards.add(new Disarm());
        cards.add(new Dropkick());
        cards.add(new DualWield());
        cards.add(new Entrench());
        cards.add(new Evolve());
        cards.add(new FeelNoPain());
        cards.add(new FireBreathing());
        cards.add(new FlameBarrier());
        cards.add(new GhostlyArmor());
        cards.add(new Hemokinesis());
        cards.add(new InfernalBlade());
        cards.add(new Inflame());
        cards.add(new Intimidate());
        cards.add(new Metallicize());
        cards.add(new PowerThrough());
        cards.add(new Pummel());
        cards.add(new Rage());
        cards.add(new Rampage());
        cards.add(new RecklessCharge());
        cards.add(new Rupture());
        cards.add(new SearingBlow());
        cards.add(new SecondWind());
        cards.add(new SeeingRed());
        cards.add(new Sentinel());
        cards.add(new SeverSoul());
        cards.add(new Shockwave());
        cards.add(new SpotWeakness());
        cards.add(new Uppercut());
        cards.add(new Whirlwind());
        cards.add(new Barricade());
        cards.add(new Berserk());
        cards.add(new Bludgeon());
        cards.add(new Brutality());
        cards.add(new DarkEmbrace());
        cards.add(new DemonForm());
        cards.add(new DoubleTap());
        cards.add(new Exhume());
        cards.add(new Feed());
        cards.add(new FiendFire());
        cards.add(new Immolate());
        cards.add(new Impervious());
        cards.add(new Juggernaut());
        cards.add(new LimitBreak());
        cards.add(new Offering());

        //colorless card
    }
    static {
        update();
    }
}