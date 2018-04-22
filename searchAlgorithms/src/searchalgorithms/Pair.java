/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

/**
 *
 * @author v1nkey
 */
public class Pair 
{
    Object state;
    double AstarCost;

    public Pair(Object state, double AstarCost) {
        this.state = state;
        this.AstarCost = AstarCost;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public double getAstarCost() {
        return AstarCost;
    }

    public void setAstarCost(double AstarCost) {
        this.AstarCost = AstarCost;
    }
    
    
}
