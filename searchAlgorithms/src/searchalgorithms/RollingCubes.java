/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import treetest.TreeState;
import ar.com.itba.sia.Rule;
import ar.com.itba.sia.Problem;
import java.util.List;
/**
 *
 * @author Acer
 */
public class RollingCubes implements Problem {
    
    public TreeState initialState;
    public TreeState finalState;

    @Override
    public Object getInitialState() {
        return this.initialState;
    }

    @Override
    public List getRules(Object e) {
        State s = (State) e;
        return s.rules;
    }

    @Override
    public boolean isResolved(Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
