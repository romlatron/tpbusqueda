/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import treetest.TreeState;
import treetest.TreeRuleLeft;
import treetest.TreeExample;
import treetest.TreeRuleRight;
import ar.com.itba.sia.*;
import java.util.ArrayList;
import java.util.List;

import RollingCubes.RollingCubes;
import RollingCubes.State;
import RollingCubes.ColorCubesHeuristic;
import RollingCubes.ImprovedHeuristic;


/**
 *
 * @author Kevin
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        RollingCubes rc = new RollingCubes();
        State root = (State)rc.getInitialState();
        Heuristic h=ImprovedHeuristic.getInstance();
        /*if (args.length==0) { 
            h = ImprovedHeuristic.getInstance(); 
            
        }
        
        else {
            switch(args[2])
                
        } */
        //System.out.println(SearchAlgorithms.Astar(rc, h));

       
      //  System.out.println(next.equals(root));
        
       // System.out.println(rules.get(1).applyToState(next).equals(root));
        
        //SearchAlgorithms.depthFirst(rc, root, null);
        //SearchAlgorithms.greedySearch(rc, h);
        System.out.println(SearchAlgorithms.Astar(rc, h));

        
    }
    
}
