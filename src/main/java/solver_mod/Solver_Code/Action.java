package solver_mod.Solver_Code;
/*
This class takes in an action the user intends to perform.
The action is checked to be valid.
If valid, the new state is calculated and returned.
*/

import java.util.*;

public class Action {
    public Map<String, String> Potion_Dict = new HashMap<String, String>();
    public Map<String, String> Card_Dict = new HashMap<String, String>();
    public Map<String, String> Enemy_Dict = new HashMap<String, String>();
    //ppulate the dictionaries
    //in future iterations these will be classes of their own


    public State use_a_potion(String potion, String target, State current_state){
        state_to_use = current_state.deep_copy();
        if (!Potion_Dict.containsKey(potion)) {
            //throw exception
        }
        else if (target.equalsIgnoreCase("self") || target.equalsIgnoreCase("enemy")) {
            //throw exception
        }
        //1) remove potion from state
        state_to_use.remove_potion(potion);
        //2) change player attributes in state
        //3) change enemy attributes in state

        return state_to_use;
    }

    public State use_a_card(String card, String target, State current_state) {
        state_to_use = current_state.deep_copy();
        if (!Card_Dict.containsKey(card)) {
            //throw exception
        }
        //1) remove card from state
        state_to_use.remove_card(card);
        //2) remove the energy the card costs from the state
        state_to_use.set_energy(-card.energy);
        //3) change player attributes in state
        //4) change enemy attributes in state

        return current_state;
    }

    public State end_turn(State current_state) {
        state_to_use = current_state.deep_copy();
        return state_to_use;
    }
}