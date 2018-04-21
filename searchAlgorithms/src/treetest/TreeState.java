/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import java.util.List;
import ar.com.itba.sia.*;
/**
 *
 * @author Acer
 */
public class TreeState {

    int num;
    List<Rule> rules;
    
    public TreeState() {}
    
    public TreeState (int num, List rules) {
        this.num = num;
        this.rules = rules;
    }
    
    @Override
    public String toString () {
        return String.format(this.num + "");
    }        
}
