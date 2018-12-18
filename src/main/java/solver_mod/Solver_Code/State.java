package solver_mod.Solver_Code;
/*
This code assumes the following classes exist:
Player
Enemy
Potion_Encyclopedia
Card_Encyclopedia
Enemy_Encyclopedia
*/

import java.util.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class State {

    public static int Self_Health = 0;
    public static List<String> Potions = new ArrayList();
    public static List<String> Cards = new ArrayList();
    public static int Current_Block = 0;
    public static int Energy = 0;
    public static int Strength = 0;
    public static int Dexterity = 0;
    public static int Weak = 0;
    public static int Vulnerable = 0;
    public static int Frail = 0;

    public static int Number_of_Enemies = 0;
    public static List<String> Enemies = new ArrayList();
    public static List<Integer> Enemy_Health_List = new ArrayList();
    public static List<Integer> Damage_Enemy_Inflicts = new ArrayList();
    public static List<Integer> Block_Enemy_Will_Add = new ArrayList();
    public static List<Integer> Strengths_Enemy_Adds = new ArrayList();
    public static List<Integer> Dexterity_Enemy_Adds = new ArrayList();
    public static List<Integer> Weak_Enemy_Applies = new ArrayList();
    public static List<Integer> Vulnerable_Enemy_Applies = new ArrayList();
    public static List<Integer> Frail_Enemy_Applies = new ArrayList();
    public static List<Integer> Weak_Enemy_Has = new ArrayList();
    public static List<Integer> Vulnerable_Enemy_Has = new ArrayList();

    public State deep_copy(State current_state) {
        State new_state = new State();

        new_state.set_self_health(Self_Health);

        for (String potion : Potions) {
            new_state.add_potion(potion);
        }

        for (String card : Cards) {
            new_state.add_card(card);
        }

        new_state.set_block(Current_Block);
        new_state.set_energy(Energy);
        new_state.set_strength(Strength);
        new_state.set_dexterity(Dexterity);
        new_state.set_vulnerable(Vulnerable);
        new_state.set_weak(Weak);
        new_state.set_frail(Frail);

        for (String enemy : Enemies) {
            new_state.add_enemy(enemy);
        }

        return new_state;

    }

    public void initialise() {
        //user attributes
        Self_Health = AbstractDungeon.player.currentHealth;
        Current_Block = AbstractDungeon.player.currentBlock;
        Energy = AbstractDungeon.player.energy.energy;
        for (AbstractPotion p : AbstractDungeon.player.potions) {
            Potions.add(p.name);
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            Cards.add(c.name);
        }
        Strength = 0;
        Dexterity = 0;
        Weak = 0;
        Vulnerable = 0;
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power.ID.equals("Strength")) {
                Strength = power.amount;
            } 
            else if (power.ID.equals("Dexterity")) {
                Dexterity = power.amount;
            } 
            else if (power.ID.equals("Weakened")) {
                Weak = power.amount;
            } 
            else if (power.ID.equals("Vulnerable")) {
                Vulnerable = power.amount;
            }
            else if (power.ID.equals("Frail")) {
                Frail = power.amount;
            }
        }

        //enemy attributes
        List<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        Number_of_Enemies = monsters.size();
        
        for (AbstractMonster m : monsters) {
            add_enemy(m);
        }
        // initialize encyclopedias
    }
    
    private void add_enemy(AbstractMonster enemy) {
        Enemies.add(enemy.name);
        Enemy_Health_List.add(enemy.currentHealth);
        Damage_Enemy_Inflicts.add();
        Block_Enemy_Will_Add.add();
        Strengths_Enemy_Adds.add();
        Dexterity_Enemy_Adds.add();
        Weak_Enemy_Applies.add();
        Vulnerable_Enemy_Applies.add();
        Frail_Enemy_Applies.add();
        int weak = 0, vulnerable = 0;
        for (AbstractPower power : enemy.powers) {
            if (power.ID.equals("Weakened")) {
                weak = power.amount;
            } else if (power.ID.equals("Vulnerable")) {
                vulnerable = power.amount;
            }
        }
        Weak_Enemy_Has.add(weak);
        Vulnerable_Enemy_Has.add(vulnerable);
    }


    public void validate_public_fields() {
         if (Self_Health < 0) {
             //throw exception
         }
        if (Current_Block < 0) {
            //throw exception
        }
        if (Energy < 0) {
            //throw exception
        }
        if (Strength < 0) {
            //throw exception
        }
        if (Dexterity < 0) {
            //throw exception
        }
        if (Weak < 0) {
            //throw exception
        }
        if (Vulnerable < 0) {
            //throw exception
        }
        if (Frail < 0) {
            //throw exception
        }
        if (Potions.size() < 0) {
            //throw exception
        }
        for (String potion : Potions) {
            if (!Potion_Encyclopedia.dict.containsKey(potion)) {
                //throw exception
            }
        }
        if (Cards.size() < 0) {
            //throw exception
        }
        for (String card : Cards) {
            if (!Card_Encyclopedia.dict.containsKey(card)) {
                //throw exception
            }
        }

        if (Number_of_Enemies <= 0) {
            //throw exception
        }
        if (Enemies.size() != Number_of_Enemies) {
            //throw exception
        }
        for (String enemy : Enemies) {
            if (!Enemy_Encyclopedia.dict.containsKey(enemy)) {
                //throw exception
            }
        }
        if (Enemy_Health_List.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int health : Enemy_Health_List) {
            if (health < 0) {
                //throw exception
            }
        }
        if (Damage_Enemy_Inflicts.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int damage : Damage_Enemy_Inflicts) {
            if (damage < 0) {
                //throw exception
            }
        }
        if (Block_Enemy_Will_Add.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int block : Block_Enemy_Will_Add) {
            if (block < 0) {
                //throw exception
            }
        }
        if (Strengths_Enemy_Adds.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int strength : Strengths_Enemy_Adds) {
            if (strength < 0) {
                //throw exception
            }
        }
        if (Dexterity_Enemy_Adds.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int dexterity : Dexterity_Enemy_Adds) {
            if (dexterity < 0) {
                //throw exception
            }
        }
        if (Weak_Enemy_Applies.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int weak : Weak_Enemy_Applies) {
            if (weak < 0) {
                //throw exception
            }
        }
        if (Vulnerable_Enemy_Applies.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int vulnerable: Vulnerable_Enemy_Applies) {
            if (vulnerable < 0) { 
                //throw exception
            }
        }
        if (Frail_Enemy_Applies.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int frail : Frail_Enemy_Applies) {
            if (frail < 0) {
                //throw exception
            }
        }
        if (Weak_Enemy_Has.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int weak : Weak_Enemy_Has) {
            if (weak < 0) {
                //throw exception
            }
        }
        if (Vulnerable_Enemy_Has.size() != Number_of_Enemies) {
            //throw exception
        }
        for (int vulnerabe : Vulnerable_Enemy_Has) {
            if (vulnerabe < 0) {
                //throw exception
            }
        }
    }

    public void set_self_health(int health_to_add) {
        Self_Health += health_to_add;
        if (Self_Health < 0) {
            Self_Health = 0;
        }
        validate_public_fields();
    }

    public int get_self_health() {
        int to_return = Self_Health;
        return to_return;
    }

    public void remove_potion(String potion_to_remove) {
        if (!Potions.contains(potion_to_remove)) {
            //throw Exception
        }
        Potions.remove(potion_to_remove);
        validate_public_fields();
    }

    public void add_potion(String potion_to_add) {
        if (!Potion_Encyclopedia.dict.containsKey(potion_to_add)) {
            //throw exception
        }
        Potions.add(potion_to_add);
        validate_public_fields();
    }

    public List<String> get_potions() {
        List<String> to_return = Potions;
        return to_return;
    }

    public void remove_card(String card_to_remove) {
        if (!Cards.contains(card_to_remove)) {
            //throw Exception
        }
        Cards.remove(card_to_remove);
        validate_public_fields();
    }

    public void add_card(String card_to_add) {
        if (!Card_Encyclopedia.dict.containsKey(card_to_add)) {
            //throw exception
        }
        Cards.add(card_to_add);
        validate_public_fields();
    }

    public List<String> get_cards() {
        List<String> to_return = Cards;
        return to_return;
    }

    public void set_block(int block_to_add) {
        Current_Block += block_to_add;

        if (Current_Block < 0) {
            Current_Block = 0;
        }
        validate_public_fields();
    }

    public int get_block() {
        int to_return = Current_Block;
        return to_return;
    }

    public void set_energy(int energy_to_add) {
        Energy += energy_to_add;
        if (Energy < 0) {
            Energy = 0;
        }
        validate_public_fields();
    }

    public int get_energy() {
        int to_return = Energy;
        return to_return;
    }

    public void set_strength(int strength_to_add) {
        Strength += strength_to_add;
        if (Strength < 0) {
            Strength = 0;
        }
    }

    public int get_strength() {
        int to_return = Strength;
        return to_return;
    }

    public void set_dexterity(int dexterity_to_add) {
        Dexterity += dexterity_to_add;
        if (Dexterity < 0) {
            Dexterity = 0;
        }
        validate_public_fields();
    }

    public int get_dexterity() {
        int to_return =  Dexterity;
        return to_return;
    }

    public void set_weak(int weak_to_add) {
        Weak += weak_to_add;
        if (Weak < 0) {
            Weak = 0;
        }
        validate_public_fields();
    }

    public int get_weak() {
        int to_return =  Weak;
        return to_return;
    }

    public void set_vulnerable(int vulnerable_to_add) {
        Vulnerable += vulnerable_to_add;
        if (Vulnerable < 0) {
            Vulnerable = 0;
        }
        validate_public_fields();
    }

    public int get_vulnerable() {
        int to_return =  Vulnerable;
        return to_return;
    }

    public void set_frail(int frail_to_add) {
        Frail += frail_to_add;
        if (Frail < 0) {
            Frail = 0;
        }
        validate_public_fields();
    }

    public int get_frail() {
        int to_return = Frail;
        return to_return;
    }


    public void remove_enemy(String enemy_to_remove) {
        if (!Enemies.contains(enemy_to_remove)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy_to_remove);
        Enemies.remove(enemy_to_remove);
        Number_of_Enemies -= 1;
        if (Number_of_Enemies < 0) {
            //throw exception
        }
        Enemy_Health_List.remove(index);
        Damage_Enemy_Inflicts.remove(index);
        Block_Enemy_Will_Add.remove(index);
        Strengths_Enemy_Adds.remove(index);
        Dexterity_Enemy_Adds.remove(index);
        Weak_Enemy_Applies.remove(index);
        Vulnerable_Enemy_Applies.remove(index);
        Frail_Enemy_Applies.remove(index);
        validate_public_fields();
    }

    public void add_enemy(String enemy_to_add) {
        if (!Enemy_Encyclopedia.dict.containsKey(enemy_to_add)) {
            //throw exception
        }
        ++Number_of_Enemies;
        add_enemy(Enemy_Encyclopedia.dict.get(enemy_to_add));
        validate_public_fields();
    }

    public List<String> get_enemies() {
        return Enemies;
    }

    public void set_enemy_health(String enemy, int health_to_add) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        Enemy_Health_List.set(index, Enemy_Health_List.get(index) + health_to_add);
        validate_public_fields();
    }

    public int get_enemy_health(String enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Enemy_Health_List.get(index);
    }

    public void set_enemy_damage(String enemy, int damage) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        Damage_Enemy_Inflicts.set(index, damage);
        validate_public_fields();
    }

    public int get_enemy_damage(String enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Damage_Enemy_Inflicts.get(index);
    }

    public void set_enemy_block(String enemy, int block) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        Block_Enemy_Will_Add.set(index, Block_Enemy_Will_Add.get(index) + block);
        validate_public_fields();
    }

    public int get_enemy_block(String enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Block_Enemy_Will_Add.get(index);
    }

    public void set_enemy_strength(String enemy, int strength) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        Strengths_Enemy_Adds.set(index, Strengths_Enemy_Adds.get(index) + strength);
        validate_public_fields();
    }

    public int get_enemy_strength(String enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Strengths_Enemy_Adds.get(index);
    }

    public void set_enemy_dexterity(String enemy, int dexterity) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        Dexterity_Enemy_Adds.set(index, Dexterity_Enemy_Adds.get(index) + dexterity);
        validate_public_fields();
    }

    public int get_enemy_dexterity(String enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Dexterity_Enemy_Adds.get(index);
    }

    public void set_weak_enemy_applies(String enemy, int weak) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        Weak_Enemy_Applies.set(index, weak);
        validate_public_fields();
    }

    public int get_weak_enemy_applies(String enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Weak_Enemy_Applies.get(index);
    }

    public void set_vulnerable_enemy_applies(String enemy, int vulnerabe) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        Vulnerable_Enemy_Applies.set(index, vulnerabe);
        validate_public_fields();
    }

    public int get_vulnerable_enemy_applies(String enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Vulnerable_Enemy_Applies.get(index);
    }

    public void set_vulnerable_enemy_has(String enemy, int vulnerabe) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        Vulnerable_Enemy_Has.set(index, Vulnerable_Enemy_Has.get(index) + vulnerabe);
        validate_public_fields();
    }

    public int get_vulnerable_enemy_has(String enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Vulnerable_Enemy_Has.get(index);
    }

    public void set_weak_enemy_has(String enemy, int weak) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        Weak_Enemy_Has.set(index, Weak_Enemy_Has.get(index) + weak);
        validate_public_fields();
    }

    public int get_weak_enemy_has(String enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Weak_Enemy_Has.get(index);
    }

    public double score() {
        double score = self_health_score() + number_of_potions_Score();
        for (String potion : Potions) {
            score += effect_of_potion_Score(potion);
        }
        score += number_of_cards_score();
        for (String card : Cards) {
            score += effect_of_card_score(card);
        }
        score += current_block_score() + current_energy_score() + current_strength_score();
        score += current_dexterity_score() + current_vulnerable_score() + current_weak_score();
        score += number_of_enemies_score();
        for (String enemy : Enemies) {
            score += enemy_rank_score(enemy) + enemy_block_score(enemy) + enemy_damage_score(enemy);
            score += enemy_dexterity_Score(enemy) + enemy_frail_applied_score(enemy);
            score += enemy_health_score(enemy) + enemy_strength_score(enemy);
            score += enemy_vulnerable_applied_score(enemy) + enemy_vulnerable_score(enemy);
            score += enemy_weak_applied_score(enemy) + enemy_weak_score(enemy);
        }
        return score;
    }

    private double self_health_score() {
        if (Self_Health == 0) {
            return -(double)Integer.MAX_VALUE;
        }
        else {
            return Self_Health*5;
        }
    }

    private double number_of_potions_Score() {
        return (Potions.size()*2);
    }

    private double effect_of_potion_Score(String potion) {
        return 0.0;
    }

    private double number_of_cards_score() {
        return (Cards.size()*2);
    }

    private double effect_of_card_score(String card) {
        return 0.0;
    }

    private double current_block_score() {
        return Current_Block*3;
    }

    private double current_energy_score() {
        return Energy*2;
    }

    private double current_strength_score() {
        return Strength*2;
    }

    private double current_dexterity_score() {
        return Dexterity*2;
    }
    
    private double current_vulnerable_score() {
        return -Vulnerable*2;
    }

    private double current_weak_score() {
        return -Weak*2;
    }

    private double number_of_enemies_score() {
        return -Enemies.size()*2;
    }

    private double enemy_rank_score(String enemy) {
        return 0.0;
    }

    private double enemy_health_score(String enemy) {
        int index = Enemy_Health_List.indexOf(enemy);
        int health = Enemy_Health_List.get(index);
        return -health*2;
    }

    private double enemy_damage_score(String enemy) {
        int index = Damage_Enemy_Inflicts.indexOf(enemy);
        int damage = Damage_Enemy_Inflicts.get(index);
        return -damage*2;
    }

    private double enemy_block_score(String enemy) {
        int index = Block_Enemy_Will_Add.indexOf(enemy);
        int block = Block_Enemy_Will_Add.get(index);
        return -block*2;
    }

    private double enemy_strength_score(String enemy) {
        int index = Strengths_Enemy_Adds.indexOf(enemy);
        int strength = Strengths_Enemy_Adds.get(index);
        return -strength*2;
    }

    private double enemy_dexterity_Score(String enemy) {
        int index = Dexterity_Enemy_Adds.indexOf(enemy);
        int dexterity = Dexterity_Enemy_Adds.get(index);
        return -dexterity*2;
    }

    private double enemy_weak_applied_score(String enemy) {
        int index = Weak_Enemy_Applies.indexOf(enemy);
        int weak = Weak_Enemy_Applies.get(index);
        return -weak*2;
    }

    private double enemy_vulnerable_applied_score(String enemy) {
        int index = Vulnerable_Enemy_Applies.indexOf(enemy);
        int vulnerable = Vulnerable_Enemy_Applies.get(index);
        return -vulnerable*2;
    }

    private double enemy_frail_applied_score(String enemy) {
        int index = Frail_Enemy_Applies.indexOf(enemy);
        int frail = Frail_Enemy_Applies.get(index);
        return -frail*2;
    }

    private double enemy_weak_score(String enemy) {
        int index = Weak_Enemy_Has.indexOf(enemy);
        int weak = Weak_Enemy_Has.get(index);
        return weak*2;
    }

    private double enemy_vulnerable_score(String enemy) {
        int index = Vulnerable_Enemy_Has.indexOf(enemy);
        int vulnerable = Vulnerable_Enemy_Has.get(index);
        return vulnerable*2;
    }
}