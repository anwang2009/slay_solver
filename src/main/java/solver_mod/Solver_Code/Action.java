package solver_mod.Solver_Code;
/*
This class takes in an action the user intends to perform.
The action is checked to be valid.
If valid, the new state is calculated and returned.
*/

public class Action {


    public State use_a_potion(String potion, String target, State current_state){
        State state_to_use = current_state.deep_copy();
        state_to_use.actions.add(potion);
        if (!Potion_Encyclopedia.dict.containsKey(potion)) {
            throw new IllegalArgumentException("Potion not in list");
        }
        if (target.equalsIgnoreCase("self") || target.equalsIgnoreCase("enemy")) {
            throw new IllegalArgumentException("Invalid target");
        }
        switch (potion) {
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
                for(String enemy : state_to_use.get_enemies()) {
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
                int current_block = state_to_use.get_enemy_block(target);
                int damage_remaining = 20 - current_block;
                state_to_use.set_enemy_block(target, - 20);
                if (damage_remaining > 0) {
                    state_to_use.set_enemy_health(target, -damage_remaining);
                }
                if (state_to_use.get_enemy_health(target) == 0) {
                    state_to_use.remove_enemy(target);
                }
                break;
            case "FearPotion":
                state_to_use.set_vulnerable_enemy_has(target, 3);
                break;
            case "Fruit Juice":
                state_to_use.set_max_health(5);
                break;
            case "GamblersBrew":
                break;
            case "LiquidBronze":
                for (String enemy : state_to_use.get_enemies()) {
                    if (state_to_use.get_enemy_damage(enemy) > 0) {
                        current_block = state_to_use.get_enemy_block(enemy);
                        damage_remaining = 3 - current_block;
                        state_to_use.set_enemy_block(enemy, - 3);
                        if (damage_remaining > 0) {
                            state_to_use.set_enemy_health(enemy, -damage_remaining);
                        }
                        if (state_to_use.get_enemy_health(target) == 0) {
                            state_to_use.remove_enemy(target);
                        }
                    }
                }
                break;
            case "Poison Potion":
                current_block = state_to_use.get_enemy_block(target);
                damage_remaining = 6 - current_block;
                state_to_use.set_enemy_block(target, - 6);
                if (damage_remaining > 0) {
                    state_to_use.set_enemy_health(target, -damage_remaining);
                }
                if (state_to_use.get_enemy_health(target) == 0) {
                    state_to_use.remove_enemy(target);
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
                for (String enemy : state_to_use.get_enemies()) {
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
                state_to_use.set_weak_enemy_has(target, 3);
                break;
            default: break;
        }
        state_to_use.remove_potion(potion);
        return state_to_use;
    }

    public State use_a_card(String card, String target, State current_state) {
        State state_to_use = current_state.deep_copy();
        state_to_use.actions.add(card);
        if (!Card_Encyclopedia.dict.containsKey(card)) {
            throw new IllegalArgumentException("Card not in list");
        }
        if (target.equalsIgnoreCase("self") || target.equalsIgnoreCase("enemy")) {
            throw new IllegalArgumentException("Invalid target");
        }
        //1) remove card from state
        state_to_use.remove_card(card);
        //2) remove the energy the card costs from the state
        //state_to_use.set_energy(-card.energy);
        //3) change player attributes in state
        //4) change enemy attributes in state

        return current_state;
    }

    public State end_turn(State current_state) {
        return current_state.deep_copy();
    }
}