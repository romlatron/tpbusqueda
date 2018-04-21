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
public abstract class RCRule implements Rule{

    protected double cost = 1;
    
    public RCRule() { }
        
    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void setCost(double d) {
        this.cost = d;
    }

    @Override
    public abstract Object applyToState(Object e);
    
    protected List<Rule> newStateRules (int emptyIndex) {
        List <Rule> nextPossibleRules = new ArrayList();
        nextPossibleRules.add(new ClickDown());
        
        if (emptyIndex >= 3) {     
            nextPossibleRules.add(new ClickUp());
        }
        
        if (emptyIndex <= 5) {     
            nextPossibleRules.add(new ClickDown());
        }
                
        if (emptyIndex != 2 && emptyIndex != 5 && emptyIndex != 8) {
            nextPossibleRules.add(new ClickRight());
        }
        
        if (emptyIndex != 0 && emptyIndex != 3 && emptyIndex != 6) {
            nextPossibleRules.add(new ClickLeft());
        }
        
        return nextPossibleRules;
    }
    
    /*protected void swapCube(Cube[] board, int index1, int index2)
    {
        Cube tmp;
        tmp = board[index1];
        board[index1] = board[index2];
        board[index2] = tmp;
    }*/
}
