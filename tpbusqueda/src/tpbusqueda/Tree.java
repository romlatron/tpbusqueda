/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpbusqueda;
import ar.com.itba.sia.*;
import java.util.List;

/**
 *
 * @author Acer
 * @param <E>
 */
public class Tree <E extends Object> implements Problem {

    public State initialState;
    public State finalState;
    
    @Override
    public State getInitialState(){
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
        return s.num==finalState.num;
    }
    
    public Tree (State initialState, State finalState) {
        this.initialState = initialState;
        this.finalState = finalState;
    }
    
}
