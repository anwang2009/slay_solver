package solver_mod.Solver_Code;
/*
This code assumes the following classes exist:
Player
Enemy
Potion_Encyclopedia
Card_Encyclopedia
Enemy_Encyclopedia
*/

//todo: add get_debuff_enemy_applies and set_debuff_enemy_applies

import java.lang.reflect.Field;
import java.util.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class State {

    public List<Object> actions = new ArrayList<>();
    public List<AbstractMonster> action_targets = new ArrayList<>();

    public int Self_Health = 0;
    public int Max_Health = AbstractDungeon.player.maxHealth;
    public List<AbstractPotion> Potions = new ArrayList<>();
    public List<AbstractCard> Cards = new ArrayList<>();
    public int Current_Block = 0;
    public int Energy = 0;
    public int Strength = 0;
    public int Dexterity = 0;
    public int Weak = 0;
    public int Vulnerable = 0;
    public int Frail = 0;
    public int Bonus = 0;

    public int Number_of_Enemies = 0;
    public List<AbstractMonster> Enemies = new ArrayList<AbstractMonster>();
    public List<Integer> Enemy_Health_List = new ArrayList<Integer>();
    public List<Integer> Damage_Enemy_Inflicts = new ArrayList<Integer>();
    public List<Integer> Block_Enemy_Will_Add = new ArrayList<Integer>();
    public List<Integer> Debuff_Enemy_Applies = new ArrayList<Integer>();
    public List<Integer> Weak_Enemy_Has = new ArrayList<Integer>();
    public List<Integer> Vulnerable_Enemy_Has = new ArrayList<Integer>();
    //todo: store enemy strength, create get and set methods for enemy strength,
    //add enemy strength to score

    public State deep_copy() {
        State new_state = new State();
        //new_state.initialise();
        new_state.actions = new ArrayList<>(this.actions);
        new_state.action_targets = new ArrayList<>(this.action_targets);

        new_state.set_self_health(Self_Health);
        new_state.set_max_health(Max_Health);
        new_state.set_block(Current_Block);
        new_state.set_energy(Energy);
        new_state.set_strength(Strength);
        new_state.set_dexterity(Dexterity);
        new_state.set_vulnerable(Vulnerable);
        new_state.set_frail(Frail);
        new_state.set_weak(Weak);
        new_state.set_bonus(Bonus);
        new_state.Number_of_Enemies = Number_of_Enemies;

        for (AbstractPotion potion : Potions) {
            new_state.add_potion(potion);
        }
        for (AbstractCard card : Cards) {
            new_state.add_card(card);
        }

        for (AbstractMonster enemy : Enemies) {
            new_state.Enemies.add(enemy);
        }
        new_state.Enemy_Health_List.addAll(Enemy_Health_List);
        new_state.Weak_Enemy_Has.addAll(Weak_Enemy_Has);
        new_state.Vulnerable_Enemy_Has.addAll(Vulnerable_Enemy_Has);
        new_state.Block_Enemy_Will_Add.addAll(Block_Enemy_Will_Add);
        new_state.Damage_Enemy_Inflicts.addAll(Damage_Enemy_Inflicts);
        new_state.Debuff_Enemy_Applies.addAll(Debuff_Enemy_Applies);

        return new_state;
    }

    public void initialise() {
        //user attributes
        Self_Health = AbstractDungeon.player.currentHealth;
        Current_Block = AbstractDungeon.player.currentBlock;
        Energy = AbstractDungeon.player.energy.energy;
        Potions.addAll(AbstractDungeon.player.potions);
        Cards.addAll(AbstractDungeon.player.hand.group);
        Strength = 0;
        Dexterity = 0;
        Weak = 0;
        Vulnerable = 0;
        Bonus = 0;
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
    
    public void add_enemy(AbstractMonster enemy) {
        if (!Enemy_Encyclopedia.contains(enemy)) {
            //throw new IllegalArgumentException();
        }
        if (Enemies == null) {
            Enemies = new ArrayList<>();
        }
        if (enemy == null) {
            return;
        }
        Enemies.add(enemy);

        int intentDmg = 0;
        boolean isMultiDmg = false;
        int intentMultiAmt = 0;
        try {
            ++Number_of_Enemies;
            Enemy_Health_List.add(enemy.currentHealth);
            Field intentDmgField = AbstractMonster.class.getDeclaredField("intentDmg");
            intentDmgField.setAccessible(true);
            intentDmg = (Integer) intentDmgField.get(enemy);

            Field isMultiDmgField = AbstractMonster.class.getDeclaredField("isMultiDmg");
            isMultiDmgField.setAccessible(true);
            isMultiDmg = (Boolean) isMultiDmgField.get(enemy);

            Field intentMultiAmtFied = AbstractMonster.class.getDeclaredField("intentMultiAmt");
            intentMultiAmtFied.setAccessible(true);
            intentMultiAmt = (Integer) intentMultiAmtFied.get(enemy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isMultiDmg) {
            intentDmg *= intentMultiAmt;
        }

        Damage_Enemy_Inflicts.add(intentDmg);
        Block_Enemy_Will_Add.add(enemy.currentBlock);

        if (enemy.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
            enemy.intent == AbstractMonster.Intent.DEFEND_DEBUFF ||
            enemy.intent == AbstractMonster.Intent.DEBUFF ||
            enemy.intent == AbstractMonster.Intent.STRONG_DEBUFF) {
            Debuff_Enemy_Applies.add(1);
        } else {
            Debuff_Enemy_Applies.add(0);
        }
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
        int poison = 0;
        for (AbstractPower power : enemy.powers) {
            if (power instanceof PoisonPower) {
                poison = power.amount;
                break;
            }
        }
        validate_public_fields();
    }


    public void validate_public_fields() {
        return;
    }

    public int get_bonus() {
        return Bonus;
    }

    public void set_bonus(int bonus) {
        Bonus += bonus;
        if (Bonus < 0) {
            Bonus = 0;
        }
    }

    public int get_max_health() {
        return Max_Health;
    }

    public void set_max_health(int health_to_add) {
        Max_Health += health_to_add;
        if (Max_Health < 0) {
            Max_Health = 0;
        }
        validate_public_fields();
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

    public void remove_potion(AbstractPotion potion_to_remove) {
        if (!Potion_Encyclopedia.contains(potion_to_remove)) {
            throw new IllegalArgumentException(potion_to_remove.name);
        }
        Potions.remove(potion_to_remove);
        validate_public_fields();
    }

    public void add_potion(AbstractPotion potion_to_add) {
        if (!Potion_Encyclopedia.contains(potion_to_add)) {
            throw new IllegalArgumentException(potion_to_add.name);
        }
        Potions.add(potion_to_add);
        validate_public_fields();
    }

    public List<AbstractPotion> get_potions() {
        return Potions;
    }

    public void remove_card(AbstractCard card_to_remove) {
        if (!Card_Encyclopedia.contains(card_to_remove)) {
            throw new IllegalArgumentException();
        }
        Cards.remove(card_to_remove);
        validate_public_fields();
    }

    public void add_card(AbstractCard card_to_add) {
        if (!Card_Encyclopedia.contains(card_to_add)) {
            //throw new IllegalArgumentException();
        }
        Cards.add(card_to_add);
        validate_public_fields();
    }

    public List<AbstractCard> get_cards() {
        List<AbstractCard> to_return = Cards;
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


    public void remove_enemy(AbstractMonster enemy_to_remove) {
        if (!Enemies.contains(enemy_to_remove)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy_to_remove);
        Enemies.remove(enemy_to_remove);
        Number_of_Enemies -= 1;
        if (Number_of_Enemies < 0) {
            throw new IllegalArgumentException();
        }
        Enemy_Health_List.remove(index);
        Damage_Enemy_Inflicts.remove(index);
        Block_Enemy_Will_Add.remove(index);
        Debuff_Enemy_Applies.remove(index);
        Vulnerable_Enemy_Has.remove(index);
        Weak_Enemy_Has.remove(index);
        validate_public_fields();
    }


    public List<AbstractMonster> get_enemies() {
        return Enemies;
    }

    public void set_enemy_health(AbstractMonster enemy, int health_to_add) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        int health_to_set = Enemy_Health_List.get(index) + health_to_add;
        if (health_to_set < 0) {
            health_to_set = 0;
        }
        Enemy_Health_List.set(index, health_to_set);
        validate_public_fields();
    }

    public int get_enemy_health(AbstractMonster enemy) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        return Enemy_Health_List.get(index);
    }

    public void set_enemy_damage(AbstractMonster enemy, int damage) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        if (damage < 0) {
            damage = 0;
        }
        Damage_Enemy_Inflicts.set(index, damage);
        validate_public_fields();
    }

    public int get_enemy_damage(AbstractMonster enemy) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        return Damage_Enemy_Inflicts.get(index);
    }

    public void set_enemy_block(AbstractMonster enemy, int block) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        int block_to_set = Block_Enemy_Will_Add.get(index) + block;
        if (block_to_set < 0) {
            block_to_set = 0;
        }
        Block_Enemy_Will_Add.set(index, block_to_set);
        validate_public_fields();
    }

    public int get_enemy_block(AbstractMonster enemy) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        return Block_Enemy_Will_Add.get(index);
    }

    public void set_vulnerable_enemy_has(AbstractMonster enemy, int vulnerabe) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        int vulnerable_to_set = Vulnerable_Enemy_Has.get(index) + vulnerabe;
        if (vulnerable_to_set < 0) {
            vulnerable_to_set = 0;
        }
        Vulnerable_Enemy_Has.set(index, vulnerable_to_set);
        validate_public_fields();
    }

    public int get_vulnerable_enemy_has(AbstractMonster enemy) {
        if (!Enemies.contains(enemy)) {
            //throw exception
        }
        int index = Enemies.indexOf(enemy);
        return Vulnerable_Enemy_Has.get(index);
    }

    public void set_weak_enemy_has(AbstractMonster enemy, int weak) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        int weak_to_set = Weak_Enemy_Has.get(index) + weak;
        if (weak_to_set < 0) {
            weak_to_set = 0;
        }
        Weak_Enemy_Has.set(index, weak_to_set);
        validate_public_fields();
    }

    public int get_weak_enemy_has(AbstractMonster enemy) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        return Weak_Enemy_Has.get(index);
    }

    public void set_debuff_enemy_applies(AbstractMonster enemy, int debuff) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        int current_debuff =  Debuff_Enemy_Applies.get(index);
        current_debuff += debuff;
        if (current_debuff < 0) {
            current_debuff = 0;
        }
        Debuff_Enemy_Applies.set(index, current_debuff);

    }

    public int get_debuff_enemy_applies(AbstractMonster enemy) {
        if (!Enemies.contains(enemy)) {
            throw new IllegalArgumentException();
        }
        int index = Enemies.indexOf(enemy);
        return Debuff_Enemy_Applies.get(index);
    }

    public double score() {
        double score = self_health_score() + number_of_potions_score() + number_of_cards_score();
        score += current_block_score() + current_strength_score();
        score += current_dexterity_score() + current_vulnerable_score() + current_weak_score();
        score += number_of_enemies_score();
        score += bonus_score();

        // TODO: Fill in enemy rank score method
        score += -Cards.size()*5;
        for (AbstractMonster enemy : Enemies) {
            score += enemy_block_score(enemy) + enemy_damage_score(enemy);
            score += enemy_debuff_applied_score(enemy);
            score += enemy_health_score(enemy);
            score += enemy_vulnerable_score(enemy);
            score += enemy_weak_score(enemy);
        }
        return score;
    }

    private double self_health_score() {
        if (Self_Health == 0) {
            return -(double)Integer.MAX_VALUE;
        }
        else {
            return Self_Health*4;
        }
    }

    private double max_health_score() {
        return Max_Health*3;
    }

    private double number_of_potions_score() {
        return (Potions.size()*2);
    }

    private double number_of_cards_score() {
        return (Cards.size()*1);
    }

    private double current_block_score() {
        int sum_enemy_damage = 0;
        for (AbstractMonster m : Enemies) {
            sum_enemy_damage += get_enemy_damage(m);
        }
        if (Self_Health < 20 || sum_enemy_damage > 20) {
            return Current_Block*8;
        }
        if (sum_enemy_damage == 0) {
            return - Current_Block*5;
        }
        return Current_Block*2.5;
    }

    private double current_strength_score() {
        int total_enemy_block = 0;
        for (AbstractMonster m : Enemies) {
            int index = Enemies.indexOf(m);
            total_enemy_block += Block_Enemy_Will_Add.get(index);
        }
        if (total_enemy_block > 10) {
            return Strength * 5;
        }
        return Strength*3;
    }

    private double current_dexterity_score() {
        int total_enemy_damage = 0;
        for (AbstractMonster m : Enemies) {
            int index = Enemies.indexOf(m);
            total_enemy_damage += Damage_Enemy_Inflicts.get(index);
        }
        if (total_enemy_damage > 15) {
            return Strength * 5;
        }
        return Dexterity*3;
    }
    
    private double current_vulnerable_score() {
        int total_enemy_damage = 0;
        for (AbstractMonster m : Enemies) {
            int index = Enemies.indexOf(m);
            total_enemy_damage += Damage_Enemy_Inflicts.get(index);
        }
        if (total_enemy_damage > 15) {
            return Vulnerable * 5;
        }
        return -Vulnerable*3;
    }

    private double current_weak_score() {
        return -Weak*5;
    }

    private double number_of_enemies_score() {
        return -Enemies.size()*20;
    }


    private double enemy_health_score(AbstractMonster enemy) {
        int index = Enemies.indexOf(enemy);
        int health = Enemy_Health_List.get(index);
        if (health < 10) {
            if (Damage_Enemy_Inflicts(index) > 3) {
                return - health*30000;
            }
            return -health*10000;
        }
        return -health*10;
    }

    private double enemy_damage_score(AbstractMonster enemy) {
        int index = Enemies.indexOf(enemy);
        int damage = Damage_Enemy_Inflicts.get(index);
        if (damage > 20) {
            return -damage*10;
        }
        if (damage <= Current_Block) {
            return -damage*2;
        }
        return -damage*5;
    }

    private double enemy_block_score(AbstractMonster enemy) {
        int index = Enemies.indexOf(enemy);
        int block = Block_Enemy_Will_Add.get(index);
        return -block*3;
    }

    private double enemy_debuff_applied_score(AbstractMonster enemy) {
        int index = Enemies.indexOf(enemy);
        int debuff = Debuff_Enemy_Applies.get(index);
        return -debuff*5;
    }

    private double enemy_weak_score(AbstractMonster enemy) {
        int index = Enemies.indexOf(enemy);
        int weak = Weak_Enemy_Has.get(index);
        return weak*5;
    }

    private double enemy_vulnerable_score(AbstractMonster enemy) {
        int index = Enemies.indexOf(enemy);
        int vulnerable = Vulnerable_Enemy_Has.get(index);
        return vulnerable*5;
    }


    private double bonus_score() {
        return Bonus*3;
    }
}