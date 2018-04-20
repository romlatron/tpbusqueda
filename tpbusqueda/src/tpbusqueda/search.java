/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpbusqueda;

import ar.com.itba.sia.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

/**
 *
 * @author Acer
 */
public class search {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List <Rule> goLeftRight = new ArrayList<>();
        goLeftRight.add(new ruleLeft());
        goLeftRight.add(new ruleRight());
        State init = new State (1, goLeftRight);
        State fin = new State (6, goLeftRight);
        Tree tree = new Tree (init, fin);
        
        depthFirstRec (tree, tree.getInitialState());
        
    }
        
    public static boolean depthFirstRec (Problem p, Object state) {
        System.out.println(state);
        if (p.isResolved(state)) {
            System.out.println("Bingo");
            return true;
        }
        List <Rule> rules = p.getRules(state);
        for (Rule rule : rules) {
            if (depthFirstRec(p, rule.applyToState(state)))
                return true;
        }
        
        return false;           
    }
    
    /*public static void greedySearch (node root, node goal, Map  heuristic) {
        node actual = root;
        while (actual != goal) {
            int max=0;
            for (node n : actual.children) {
                if (n.value > max)
                    max = n.value;
            }
                
        }    
            
    }*/
    
}
