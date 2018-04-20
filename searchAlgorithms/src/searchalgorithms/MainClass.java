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
 * @author Kevin
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        List <Rule> goLeftRight = new ArrayList<>();
        goLeftRight.add(new RuleLeft());
        goLeftRight.add(new RuleRight());
        State init = new State (1, goLeftRight);
        State fin = new State (6, goLeftRight);
        Tree tree = new Tree (init, fin);
        
        //SearchAlgorithms.depthFirstRec(tree, tree.getInitialState());
        //SearchAlgorithms.breadthFirst(tree);
    }
    
}
