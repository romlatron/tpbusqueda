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
        /*List <Rule> goLeftRight = new ArrayList<>();
        goLeftRight.add(new TreeRuleLeft());
        goLeftRight.add(new TreeRuleRight());
        TreeState init = new TreeState (1, goLeftRight);
        TreeState fin = new TreeState (6, goLeftRight);
        TreeExample tree = new TreeExample (init, fin);
        
        SearchAlgorithms.iterativeDeepening (tree);*/

        RollingCubes rc = new RollingCubes();
        State root = (State)rc.getInitialState();
        Heuristic h = ImprovedHeuristic.getInstance();
        List<Rule> rules = rc.getRules(root);
        Object next = rules.get(0).applyToState(root);
      //  System.out.println(next.equals(root));
        
       // System.out.println(rules.get(1).applyToState(next).equals(root));
        
        //SearchAlgorithms.depthFirst(rc, root, null);
        //SearchAlgorithms.greedySearch(rc, h);
        System.out.println(SearchAlgorithms.Astar(rc, h));

        
    }
    
}
