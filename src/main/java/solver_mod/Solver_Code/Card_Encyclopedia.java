package solver_mod.Solver_Code;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.green.Backflip;
import com.megacrit.cardcrawl.cards.green.MasterfulStab;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.status.*;
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
    public static List<AbstractCard> cards = new ArrayList();
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
        cards.add(new Reaper());

        //colorless card
        cards.add(new Apparition());
        cards.add(new Bite());
        cards.add(new JAX());
        cards.add(new RitualDagger());
        cards.add(new Shiv());
        cards.add(new BandageUp());
        cards.add(new Blind());
        cards.add(new DarkShackles());
        cards.add(new DeepBreath());
        cards.add(new Discovery());
        cards.add(new DramaticEntrance());
        cards.add(new Enlightenment());
        cards.add(new Finesse());
        cards.add(new FlashOfSteel());
        cards.add(new Forethought());
        cards.add(new GoodInstincts());
        cards.add(new Impatience());
        cards.add(new JackOfAllTrades());
        cards.add(new Madness());
        cards.add(new MindBlast());
        cards.add(new Panacea());
        cards.add(new PanicButton());
        cards.add(new Purity());
        cards.add(new SwiftStrike());
        cards.add(new Trip());
        cards.add(new Apotheosis());
        cards.add(new Chrysalis());
        cards.add(new HandOfGreed());
        cards.add(new Magnetism());
        cards.add(new MasterOfStrategy());
        cards.add(new Mayhem());
        cards.add(new Metamorphosis());
        cards.add(new Panache());
        cards.add(new SadisticNature());
        cards.add(new SecretTechnique());
        cards.add(new SecretWeapon());
        cards.add(new TheBomb());
        cards.add(new ThinkingAhead());
        cards.add(new Transmutation());
        cards.add(new Violence());
        cards.add(new Violence());

        //curses
        cards.add(new AscendersBane());
        cards.add(new Clumsy());
        cards.add(new Decay());
        cards.add(new Doubt());
        cards.add(new Injury());
        cards.add(new Necronomicurse());
        cards.add(new Normality());
        cards.add(new Pain());
        cards.add(new Parasite());
        cards.add(new Pride());
        cards.add(new Regret());
        cards.add(new Shame());
        cards.add(new Writhe());

        //statuses
        cards.add(new Burn());
        cards.add(new Dazed());
        cards.add(new Wound());
        cards.add(new Slimed());
        cards.add(new VoidCard());

    }
    static {
        update();
    }
    
    public static boolean contains(AbstractCard card) {
        for (AbstractCard c : cards) {
            if (card.name.replace('+',' ').trim().equals(c.name)) {
                return true;
            }
        }
        return false;
    }
}