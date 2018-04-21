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
    
    public State initialState;

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
        State s = (State) e;
        for (Cube cube : s.board) {
            if (cube.currentColor != Cube.color.WHITE && cube.currentColor != Cube.color.EMPTY)
                return false;
        }
        return true;
    }
    
    public RollingCubes () {
        State init = new State();
        for (int i=0; i<9; i++)
            if (i != 4) init.board[i].setColor(Cube.color.BLACK);
        init.board[4].setColor(Cube.color.EMPTY);
        this.initialState = init;
    }
}
