/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RollingCubes;

import ar.com.itba.sia.Rule;
import ar.com.itba.sia.Problem;
import java.util.List;
/**
 *
 * @author Acer
 */
public class RollingCubes implements Problem {
    
    private State initialState;

    @Override
    public Object getInitialState() {
        return this.initialState;
    }

    @Override
    public List <Rule> getRules(Object e) {
        State s = (State) e;
        return s.getRules();
    }

    @Override
    public boolean isResolved(Object e) {
        State s = (State) e;
        for (Cube cube : s.getBoard()) {
            if (cube.getCurrentColor() != Cube.color.WHITE && cube.getCurrentColor() != Cube.color.EMPTY)
                return false;
        }
        return true;
    }
    
    public RollingCubes () {        
        Cube board[] = new Cube[9];
        for (int i=0; i<9; i++)
            if (i != 4) board[i] = new Cube(Cube.color.BLACK);
        board[4] = new Cube(Cube.color.EMPTY);
        
        State init = new State(board, 4);
        this.initialState = init;
    }
}
