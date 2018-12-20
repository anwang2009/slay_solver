package solver_mod.Solver_Code;
/*
This class takes in an action the user intends to perform.
The action is checked to be valid.
If valid, the new state is calculated and returned.
*/

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.*;

public class Action {


    public State use_a_potion(AbstractPotion ap, AbstractMonster am, State current_state){
        State state_to_use = current_state.deep_copy();
        state_to_use.actions.add(ap);
        state_to_use.action_targets.add(am);
        if (!Potion_Encyclopedia.potions.contains(ap)) {
            throw new IllegalArgumentException("Potion not in list");
        }
        switch (ap.name) {
            case "Ancient Potion" :
                //set 1st encountered debuff by enemy to 0;
                break;
            case "Attack Potion":
                //choose 1 of 3 cards, add to cards list, set energy for card as 0;
                break;
            case "Block Potion":
                state_to_use.set_block(12);
                break;
            case "Blood Potion":
                state_to_use.set_self_health((int)state_to_use.get_max_health());
                break;
            case "Dexterity Potion":
                state_to_use.set_dexterity(2);
                break;
            case "Energy Potion":
                state_to_use.set_energy(2);
                break;
            case "EntropicBrew":
                //add 3 potions to potion list
                //state_to_use.add_potion();
                //state_to_use.add_potion();
                //state_to_use.add_potion();
                break;
            case "EssenceOfSteel":
                state_to_use.set_block(4);
                break;
            case "Explosive Potion":
                for(AbstractMonster enemy : state_to_use.get_enemies()) {
                    int current_block = state_to_use.get_enemy_block(enemy);
                    int damage_remaining = 10 - current_block;
                    state_to_use.set_enemy_block(enemy, -10);
                    if (damage_remaining > 0) {
                        state_to_use.set_enemy_health(enemy, -damage_remaining);
                    }
                    if (state_to_use.get_enemy_health(enemy) == 0) {
                        state_to_use.remove_enemy(enemy);
                    }
                }
                break;
            case "Fairy Potion":
                state_to_use.set_self_health((int)state_to_use.get_max_health());
                break;
            case "Fire Potion":
                int current_block = state_to_use.get_enemy_block(am);
                int damage_remaining = 20 - current_block;
                state_to_use.set_enemy_block(am, - 20);
                if (damage_remaining > 0) {
                    state_to_use.set_enemy_health(am, -damage_remaining);
                }
                if (state_to_use.get_enemy_health(am) == 0) {
                    state_to_use.remove_enemy(am);
                }
                break;
            case "FearPotion":
                state_to_use.set_vulnerable_enemy_has(am, 3);
                break;
            case "Fruit Juice":
                state_to_use.set_max_health(5);
                break;
            case "GamblersBrew":
                break;
            case "LiquidBronze":
                for (AbstractMonster enemy : state_to_use.get_enemies()) {
                    if (state_to_use.get_enemy_damage(enemy) > 0) {
                        current_block = state_to_use.get_enemy_block(enemy);
                        damage_remaining = 3 - current_block;
                        state_to_use.set_enemy_block(enemy, - 3);
                        if (damage_remaining > 0) {
                            state_to_use.set_enemy_health(enemy, -damage_remaining);
                        }
                        if (state_to_use.get_enemy_health(am) == 0) {
                            state_to_use.remove_enemy(am);
                        }
                    }
                }
                break;
            case "Poison Potion":
                current_block = state_to_use.get_enemy_block(am);
                damage_remaining = 6 - current_block;
                state_to_use.set_enemy_block(am, - 6);
                if (damage_remaining > 0) {
                    state_to_use.set_enemy_health(am, -damage_remaining);
                }
                if (state_to_use.get_enemy_health(am) == 0) {
                    state_to_use.remove_enemy(am);
                }
                break;
            case "PowerPotion":
                //add one of 3 random cards; set their energy to 0
                break;
            case "Regen Potion":
                state_to_use.set_self_health(5);
                break;
            case "SkillPotion":
                //add one of 3 random cards; set their energy to 0
                break;
            case "SmokeBomb":
                for (AbstractMonster enemy : state_to_use.get_enemies()) {
                    state_to_use.remove_enemy(enemy);
                }
                break;
            case "SneckoOil":
                //implement confusion
                //add 3 cards
                break;
            case "SpeedPotion":
                state_to_use.set_dexterity(5);
                break;
            case "SteroidPotion":
                state_to_use.set_strength(5);
                break;
            case "Strength Potion":
                state_to_use.set_strength(2);
                break;
            case "Swift Potion":
                //add three cards
                break;
            case "Weak Potion":
                state_to_use.set_weak_enemy_has(am, 3);
                break;
            default: break;
        }
        state_to_use.remove_potion(ap);
        return state_to_use;
    }

    public State use_a_card(AbstractCard ac, AbstractMonster am, State current_state) {
        State state_to_use = current_state.deep_copy();
        state_to_use.actions.add(ac);
        state_to_use.action_targets.add(am);
        if (!Card_Encyclopedia.cards.contains(ac)) {
            throw new IllegalArgumentException("Card not in Encyclopedia");
        }
        //1) remove card from state
        state_to_use.remove_card(ac);
        //2) remove the energy the card costs from the state
        state_to_use.Energy -= ac.cost;
        switch (ac.name) {
            case "Bash" :
                does_damage_to_enemy(state_to_use, am, 8);
                applies_debuff_to_enemy(state_to_use, am, 2, "Vulnerable");
                state_to_use.set_energy(-ac.cost);
                break;
            case "Defend_R" :
                state_to_use.set_block(5);
                state_to_use.set_energy(-ac.cost);
                break;
            case "Strike_R" :
                does_damage_to_enemy(state_to_use, am, 6);
                state_to_use.set_energy(-ac.cost);
                break;
            case "Anger" :
                does_damage_to_enemy(state_to_use, am, 6);
                state_to_use.set_energy(-ac.cost);
                break;
            case "Armaments" :
                state_to_use.set_block(5);
                state_to_use.set_energy(-ac.cost);
                break;
            case "Body Slam" :
                int block = state_to_use.get_block();
                does_damage_to_enemy(state_to_use, am, block);
                state_to_use.set_energy(-ac.cost);
                break;
            case "Clash" :
                does_damage_to_enemy(state_to_use, am, 14);
                state_to_use.set_energy(-ac.cost);
                break;
            case "Cleave" :
                for (AbstractMonster enemy : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, am, 8);
                }
                state_to_use.set_energy(-ac.cost);
                break;
            case "Clothesline" :
                does_damage_to_enemy(state_to_use, am, 12);
                applies_debuff_to_enemy(state_to_use, am, 2, "Weak");
                state_to_use.set_energy(-ac.cost); break;
            case "Flex" :
                state_to_use.set_strength(2);
                state_to_use.set_energy(-ac.cost); break;
            case "Havoc" :
                state_to_use.set_energy(-ac.cost); break;
            case "Headbutt" :
                does_damage_to_enemy(state_to_use, am, 14);
                state_to_use.set_energy(-ac.cost); break;
            case "Heavy Blade" :
                int damage_done = 14 + 3*state_to_use.get_strength();
                does_damage_to_enemy(state_to_use, am, damage_done);
                state_to_use.set_energy(-ac.cost); break;
            case "Iron Wave" :
                does_damage_to_enemy(state_to_use, am, 5);
                state_to_use.set_block(5);
                state_to_use.set_energy(-ac.cost); break;
            case "Perfected Strike" :
                damage_done = 6;
                for (AbstractCard c : state_to_use.get_cards()) {
                    if (c.name.contains("Strike")) {
                        damage_done += 2;
                    }
                    does_damage_to_enemy(state_to_use, am, damage_done);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Pommel Strike" :
                does_damage_to_enemy(state_to_use, am, 9);
                state_to_use.set_energy(-ac.cost); break;
            case "Shrug It Off" :
                state_to_use.set_block(8);
                state_to_use.set_energy(-ac.cost); break;
            case "Sword Boomerang" :
                for (AbstractMonster enemy : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, am, 3);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Thunderclap" :
                for (AbstractMonster enemy : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, am, 4);
                    state_to_use.set_vulnerable_enemy_has(am, 1);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "True Grit" :
                state_to_use.set_block(7);
                state_to_use.set_energy(-ac.cost); break;
            case "Twin Strike" :
                does_damage_to_enemy(state_to_use, am, 10);
                state_to_use.set_energy(-ac.cost); break;
            case "Warcry" :
                state_to_use.set_energy(-ac.cost); break;
            case "Wind Strike" :
                does_damage_to_enemy(state_to_use, am, 12);
                state_to_use.set_energy(-ac.cost); break;
            case "Battle Trance" :
                state_to_use.set_energy(-ac.cost); break;
            case "Blood for Blood" :
                does_damage_to_enemy(state_to_use, am, 18);
                state_to_use.set_energy(-ac.cost); break;
            case "Bloodletting" : {
                state_to_use.set_self_health(-3);
                state_to_use.set_energy(1);
            }
                state_to_use.set_energy(-ac.cost); break;
            case "Burning Pact" :
                state_to_use.set_energy(-ac.cost); break;
            case "Carnage" :
                does_damage_to_enemy(state_to_use, am, 20);
                state_to_use.set_energy(-ac.cost); break;
            case "Combust" :
                state_to_use.set_self_health(-1);
                for (AbstractMonster enemy : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, am, 5);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Corruption" :
                state_to_use.set_energy(-ac.cost); break;
            case "Disarm" :
                //state_to_use.set_strength(2);
                state_to_use.set_energy(-ac.cost); break;
            case "Dropkick" :
                does_damage_to_enemy(state_to_use,am, 5);
                if (state_to_use.get_weak_enemy_has(am) > 0) {
                    state_to_use.set_energy(1);
                }
                state_to_use.set_energy(1);
                state_to_use.set_energy(-ac.cost); break;
            case "Dual Wield" :
                state_to_use.set_energy(-ac.cost); break;
            case "Entrench" :
                block = state_to_use.get_block();
                state_to_use.set_block(block);
                state_to_use.set_energy(-ac.cost); break;
            case "Evolve" :
                state_to_use.set_energy(-ac.cost); break;
            case "Feel No Pain" :
                state_to_use.set_block(3);
                state_to_use.set_energy(-ac.cost); break;
            case "Fire Breathing" :
                int damage = state_to_use.get_cards().size();
                for (AbstractMonster enemy : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, am, damage);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Flame Barrier" :
                state_to_use.set_block(12);
                for (AbstractMonster enemy : state_to_use.get_enemies()) {
                    if (state_to_use.get_enemy_damage(am) > 0)
                        does_damage_to_enemy(state_to_use, am, 4);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Ghostly Armor" :
                state_to_use.set_block(10);
                state_to_use.set_energy(-ac.cost); break;
            case "Hemokinesis" :
                state_to_use.set_self_health(-3);
                does_damage_to_enemy(state_to_use, am, 14);
                state_to_use.set_energy(-ac.cost); break;
            case "Infernal Blade" :
                state_to_use.set_energy(-ac.cost); break;
            case "Inflame" :
                state_to_use.set_strength(2);
                state_to_use.set_energy(-ac.cost); break;
            case "Intimidate" :
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    state_to_use.set_weak_enemy_has(am, 1);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Metallicize" :
                state_to_use.set_block(3);
                state_to_use.set_energy(-ac.cost); break;
            case "Power Through" :
                state_to_use.set_block(15);
                state_to_use.set_energy(-ac.cost); break;
            case "Pummel" :
                does_damage_to_enemy(state_to_use, am, 8);
                state_to_use.set_energy(-ac.cost); break;
            case "Rage" :
                state_to_use.set_block(3);
                state_to_use.set_energy(-ac.cost); break;
            case "Rampage" :
                does_damage_to_enemy(state_to_use, am, 8);
                state_to_use.set_energy(-ac.cost); break;
            case "Reckless Charge" :
                does_damage_to_enemy(state_to_use, am, 7);
                state_to_use.set_energy(-ac.cost); break;
            case "Rupture" :
                state_to_use.set_strength(1);
                state_to_use.set_energy(-ac.cost); break;
            case "Searing Blow" :
                does_damage_to_enemy(state_to_use, am, 12);
                state_to_use.set_energy(-ac.cost); break;
            case "Second Wind" :
                block = state_to_use.get_block();
                int multiplier = state_to_use.get_cards().size() - 1;
                state_to_use.set_block(block*multiplier);
                state_to_use.set_energy(-ac.cost); break;
            case "Seeing Red" :
                state_to_use.set_energy(2);
                state_to_use.set_energy(-ac.cost); break;
            case "Sentinel" :
                state_to_use.set_block(5);
                state_to_use.set_energy(-ac.cost); break;
            case "Sever Soul" :
                does_damage_to_enemy(state_to_use, am, 16);
                state_to_use.set_energy(-ac.cost); break;
            case "Shockwave" :
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    applies_debuff_to_enemy(state_to_use, am, 3, "Weak");
                    applies_debuff_to_enemy(state_to_use, am, 3, "Vulnerable");
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Spot Weakness" :
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    if (state_to_use.get_enemy_damage(m) > 0) {
                        state_to_use.set_strength(3);
                    }
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Uppercut" :
                does_damage_to_enemy(state_to_use, am, 13);
                applies_debuff_to_enemy(state_to_use, am, 1, "Weak");
                applies_debuff_to_enemy(state_to_use, am, 1, "Vulnerable");
                state_to_use.set_energy(-ac.cost); break;
            case "Whirlwind" :
                int energy = state_to_use.get_energy();
                damage = 5*energy;
                does_damage_to_enemy(state_to_use, am, damage);
                state_to_use.set_energy(-energy); break;
            case "Barricade" :
                block = state_to_use.get_block();
                state_to_use.set_block(block);
                state_to_use.set_energy(-ac.cost); break;
            case "Berserk" :
                state_to_use.set_energy(1);
                state_to_use.set_vulnerable(3);
                state_to_use.set_energy(-ac.cost); break;
            case "Bludgeon" :
                does_damage_to_enemy(state_to_use, am, 32);
                state_to_use.set_energy(-ac.cost); break;
            case "Brutality" :
                state_to_use.set_self_health(-1);
                state_to_use.set_energy(-ac.cost); break;
            case "Dark Embrace" :
                state_to_use.set_energy(-ac.cost); break;
            case "Demon Form" :
                state_to_use.set_strength(2);
                state_to_use.set_energy(-ac.cost); break;
            case "Double Tap" :
                state_to_use.set_energy(-ac.cost); break;
            case "Exhume" :
                state_to_use.set_energy(-ac.cost); break;
            case "Feed" :
                does_damage_to_enemy(state_to_use, am, 10);
                state_to_use.set_max_health(3);
                state_to_use.set_self_health(3);
                state_to_use.set_energy(-ac.cost); break;
            case "Fiend Fire" :
                int cards = state_to_use.get_cards().size();
                damage = 7*cards;
                does_damage_to_enemy(state_to_use,am, damage);
                state_to_use.set_energy(-ac.cost); break;
            case "Immolate" :
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, m , 21);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Impervious" :
                state_to_use.set_block(30);
                state_to_use.set_energy(-ac.cost); break;
            case "Juggernaut" :
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, am, 5);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Limit Break" :
                state_to_use.set_strength(state_to_use.get_strength());
                state_to_use.set_energy(-ac.cost); break;
            case "Offering" :
                state_to_use.set_self_health(-6);
                state_to_use.set_energy(2);
                state_to_use.set_energy(-ac.cost); break;
            case "Reaper" :
                int heal = 0;
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, am, 4);
                    heal += 4;
                }
                state_to_use.set_self_health(heal);
                state_to_use.set_energy(-ac.cost); break;
            case "Apparition" :
                state_to_use.set_block(10000);
                state_to_use.set_energy(-ac.cost); break;
            case "Bite" :
                does_damage_to_enemy(state_to_use, am, 7);
                state_to_use.set_self_health(2);
                state_to_use.set_energy(-ac.cost); break;
            case "J.A.X" :
                state_to_use.set_self_health(-3);
                state_to_use.set_strength(2);
                state_to_use.set_energy(-ac.cost); break;
            case "Ritual Dagger" :
                does_damage_to_enemy(state_to_use, am, 15);
                state_to_use.set_energy(-ac.cost); break;
            case "Shiv" :
                does_damage_to_enemy(state_to_use, am, 4);
                state_to_use.set_energy(-ac.cost); break;
            case "Bandage Up" :
                state_to_use.set_self_health(4);
                state_to_use.set_energy(-ac.cost); break;
            case "Blind" :
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    applies_debuff_to_enemy(state_to_use, m, 2, "Weak");
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Dark Shackles" :
                //enemy loses 9 strength
                //applies_debuff_to_enemy(state_to_use, am, 9, "");
                state_to_use.set_energy(-ac.cost); break;
            case "Deep Breath" :
                state_to_use.set_energy(-ac.cost); break;
            case "Discovery" :
                state_to_use.set_energy(-ac.cost); break;
            case "Dramatic Entrance" :
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, m, 6);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Enlightenment" :
                state_to_use.set_energy(1);
                state_to_use.set_energy(-ac.cost); break;
            case "Finesse" :
                state_to_use.set_block(2);
                state_to_use.set_energy(-ac.cost); break;
            case "Flash of Steel" :
                does_damage_to_enemy(state_to_use, am, 3);
                state_to_use.set_energy(-ac.cost); break;
            case "Forethought" :
                state_to_use.set_energy(-ac.cost); break;
            case "Good Instincts" :
                state_to_use.set_block(5);
                state_to_use.set_energy(-ac.cost); break;
            case "Impatience" :
                state_to_use.set_energy(-ac.cost); break;
            case "Jack Of All Trades" :
                state_to_use.set_energy(-ac.cost); break;
            case "Madness" :
                state_to_use.set_energy(-ac.cost); break;
            case "Mind Blast" :
                block = state_to_use.get_cards().size();
                state_to_use.set_block(block);
                state_to_use.set_energy(-ac.cost); break;
            case "Panacea" :
                int count = 0;
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    if (state_to_use.get_debuff_enemy_applies() > 0) {
                        state_to_use.set_debuff_enemy_applies(m,
                                -state_to_use.get_debuff_enemy_applies());
                    }
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Panic Button" :
                state_to_use.set_block(30);
                state_to_use.set_energy(-ac.cost); break;
            case "Purity" :
                state_to_use.set_energy(-ac.cost); break;
            case "Swift Strike" :
                does_damage_to_enemy(state_to_use, am, 6);
                state_to_use.set_energy(-ac.cost); break;
            case "Trip" :
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    state_to_use.set_vulnerable_enemy_has(am, 2);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Apotheosis" :
                state_to_use.set_self_health(15);
                state_to_use.set_energy(-ac.cost); break;
            case "Chrysalis" :
                state_to_use.set_energy(-ac.cost); break;
            case "Hand of Greed" :
                does_damage_to_enemy(state_to_use, am, 20);
                state_to_use.set_energy(-ac.cost); break;
            case "Magnetism" :
                state_to_use.set_energy(-ac.cost); break;
            case "Master of Strategy" :
                state_to_use.set_energy(-ac.cost); break;
            case "Mayhem" :
                state_to_use.set_energy(-ac.cost); break;
            case "Metamorphosis" :
                state_to_use.set_energy(-ac.cost); break;
            case "Panache" :
                for (AbstractMonster m : state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, m, 10);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Sadistic Nature" :
                for (AbstractMonster m: state_to_use.get_enemies()) {
                    if(state_to_use.get_vulnerable_enemy_has(m) > 0 ||
                    state_to_use.get_weak_enemy_has(m) > 0) {
                        does_damage_to_enemy(state_to_use, m, 3);
                    }
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Secret Technique" :
                state_to_use.set_energy(-ac.cost); break;
            case "Secret Weapon" :
                state_to_use.set_energy(-ac.cost); break;
            case "The Bomb" :
                for (AbstractMonster m: state_to_use.get_enemies()) {
                    does_damage_to_enemy(state_to_use, m, 30);
                }
                state_to_use.set_energy(-ac.cost); break;
            case "Thinking Ahead" :
                state_to_use.set_energy(-ac.cost); break;
            case "Transmutation" :
                state_to_use.set_energy(-state_to_use.get_energy()); break;
            case "Violence" :
                state_to_use.set_energy(-ac.cost); break;
            case "AscendersBane" :
                state_to_use.set_energy(-ac.cost); break;
            case "Clumsy" :
                state_to_use.set_energy(-ac.cost); break;
            case "Curse" :
                state_to_use.set_self_health(-2);
                state_to_use.set_energy(-ac.cost); break;
            case "Doubt" :
                state_to_use.set_weak(1);
                state_to_use.set_energy(-ac.cost); break;
            case "Injury" :
                state_to_use.set_energy(-ac.cost); break;
            case "Necromicurse" :
                state_to_use.set_energy(-ac.cost); break;
            case "Normality" :
                state_to_use.set_energy(-ac.cost); break;
            case "Pain" :
                state_to_use.set_self_health(-1);
                state_to_use.set_energy(-ac.cost); break;
            case "Parasite" :
                int max_hp = state_to_use.get_max_health();
                if (max_hp == state_to_use.get_self_health()) {
                    state_to_use.set_self_health(-3);
                }
                state_to_use.set_max_health(-3);
                state_to_use.set_energy(-ac.cost); break;
            case "Pride" :
                state_to_use.set_energy(-ac.cost); break;
            case "Regret" :
                damage = state_to_use.get_cards().size();
                state_to_use.set_self_health(-damage);
                state_to_use.set_energy(-ac.cost); break;
            case "Shame" :
                state_to_use.set_frail(1);
                state_to_use.set_energy(-ac.cost); break;
            case "Writhe" :
                state_to_use.set_energy(-ac.cost); break;
            default : break;
        }



        return state_to_use;
    }

    private void does_damage_to_enemy(State state_to_use, AbstractMonster am,
                             int damage) {
        int block = state_to_use.get_enemy_block(am);
        int damage_left = block - damage;
        state_to_use.set_enemy_block(am, -damage);
        if (damage_left < 0) {
            state_to_use.set_enemy_health(am, -damage_left);
        }
        if (state_to_use.get_enemy_health(am) <= 0) {
            state_to_use.remove_enemy(am);
        }
    }

    private void applies_debuff_to_enemy(State state_to_use, AbstractMonster am,
                                          int debuff, String type) {
        if (type.equals("Vulnerable")) {
            state_to_use.set_vulnerable_enemy_has(am ,debuff);
        }
        if (type.equals("Weak")) {
            state_to_use.set_weak_enemy_has(am, debuff);
        }
    }

    public State end_turn(State current_state) {
        return current_state.deep_copy();
    }
}