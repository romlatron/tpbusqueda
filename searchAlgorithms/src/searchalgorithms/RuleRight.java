/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpbusqueda;
import ar.com.itba.sia.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class RuleRight <E extends Object> implements Rule {

    double cost = 1;
    
    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void setCost(double d) {
        cost = d;
    }

    @Override
    public Object applyToState(Object e) {
        State s = (State) e;
        List <Rule> goLeftRight = new ArrayList<>();
        goLeftRight.add(new RuleLeft());
        goLeftRight.add(new RuleRight());
        switch (s.num) {
            case 1 : return new State (3, goLeftRight);
            case 2 : return new State (5, new ArrayList<>());
            case 3 : return new State (7, new ArrayList<>());
        }
        return null;
    }
    
    public RuleRight () {}
    
}
