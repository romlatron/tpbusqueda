/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;
import ar.com.itba.sia.*;
import java.util.List;

/**
 *
 * @author Acer
 * @param <E>
 */
public class TreeExample <E extends Object> implements Problem {

    public TreeState initialState;
    public TreeState finalState;
    
    @Override
    public TreeState getInitialState(){
        return this.initialState;
    }

    @Override
    public List getRules(Object e) {
        TreeState s = (TreeState) e;
        return s.rules;
    }

    @Override
    public boolean isResolved(Object e) {
        TreeState s = (TreeState) e;
        return s.num==finalState.num;
    }
    
    public TreeExample (TreeState initialState, TreeState finalState) {
        this.initialState = initialState;
        this.finalState = finalState;
    }
    
}
