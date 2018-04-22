/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RollingCubes;

import ar.com.itba.sia.Rule;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Acer
 */
public class State {
    
    private List <Rule> rules;
    private Cube[] board;
    private int indexEmpty;

    public State(Cube[] board, int index) 
    {        
        this.board = board;
        this.indexEmpty = index;
        
        List <Rule> nextPossibleRules = new ArrayList();
        
        if (indexEmpty >= 3) {     
            nextPossibleRules.add(new ClickUp());
        }
        
        if (indexEmpty <= 5) {     
            nextPossibleRules.add(new ClickDown());
        }
                
        if (indexEmpty != 2 && indexEmpty != 5 && indexEmpty != 8) {
            nextPossibleRules.add(new ClickRight());
        }
        
        if (indexEmpty != 0 && indexEmpty != 3 && indexEmpty != 6) {
            nextPossibleRules.add(new ClickLeft());
        }
        
        this.rules = nextPossibleRules;
    }

    public List<Rule> getRules() { return rules; }

    public Cube[] getBoard() { return board; }
    
    public int getIndexEmpty() { return indexEmpty; }
}
