/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;
import ar.com.itba.sia.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class RuleLeft <E extends Object> implements Rule {

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
            case 1 : return new State (2, goLeftRight);
            case 2 : return new State (4, new ArrayList<>());
            case 3 : return new State (6, new ArrayList<>());
        }
        return null;
    }
    
    public RuleLeft () {}
    
}
