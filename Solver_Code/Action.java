/*
This class takes in an action the user intends to perform.
The action is checked to be valid.
If valid, the new state is calculated and returned.
*/

import java.util.*;
import State;

class Score {
    public Map<String, String> Potion_Dict = new HashMap<String, String>();
    public Map<String, String> Card_Dict = new HashMap<String, String>();
    public Map<String, String> Enemy_Dict = new HashMap<String, String>();
    //ppulate the dictionaries
    //in future iterations these will be classes of their own

    public static void main(String[] args) {
        //what does the user want to do?
        String potion_name = null;
        String card_name = null;
        String target_name = null;
        current_state = State.getState();
        
        //1) use a potion
        current_state = use_a_potion(potion_name, target_name, current_state);
        //2) use a card
        current_state = use_a_card(card_name, target_name, current_state);
        //3) end turn
        current_state = end_turn(current_state);
        //return current_state

    }

    public State use_a_potion(String potion, String target, State current_state){
        if (!Potion_Dict.containsKey(potion)) {
            //throw exception
        }
        else if (target.equalsIgnoreCase("self") || target.equalsIgnoreCase("enemy")) {
            //throw exception
        }
        else {
            //1) remove potion from state
            current_state.remove_potion(potion);
            //2) change player attributes in state
            //3) change enemy attributes in state
        }
    }

    public State use_a_card(String card, String target, State current_state) {
        //1) remove card from state
        //2) remove the energy the card costs from the state
        //3) change player attributes in state
        //4) change enemy attributes in state
    }

    public State end_turn(State current_state) {
        return current_state;
    }
}