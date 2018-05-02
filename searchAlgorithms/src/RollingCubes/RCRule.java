/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RollingCubes;


import ar.com.itba.sia.Rule;
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
    
}
