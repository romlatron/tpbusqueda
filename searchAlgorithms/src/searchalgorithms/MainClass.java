/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import ar.com.itba.sia.*;
import RollingCubes.RollingCubes;
import RollingCubes.State;
import RollingCubes.ColorCubesHeuristic;
import RollingCubes.ImprovedHeuristic;
import java.util.ArrayList;
import java.util.List;
import sokoban.SokobanProblem;
import sokoban.SokobanDistanceHeuristic;
import sokoban.SokobanPlacedBoxesHeuristic;

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
        SokobanProblem sp = new SokobanProblem("input4.txt");
        RollingCubes rc = new RollingCubes();
        //SearchAlgorithms.iterativeDeepening(rc);
//        State root = (State)rc.getInitialState();
//        Rule rule = root.getRules().get(0);
//        State firstState = (State) rule.applyToState(root);
//        State symmetricState =
        
//        List<Object> symmetrics = new ArrayList<>();
//        firstState.applySymmetry(symmetrics);
//        System.out.println("1st state \n"+ firstState);
//        System.out.println("90 clock \n" + firstState.apply90ClockwiseRotation(firstState));
//        System.out.println("1st state \n"+ firstState);
        
//        SearchAlgorithms.depthFirst(rc);
        
        Heuristic h = ImprovedHeuristic.getInstance();
        if (args.length==0) { 
           System.out.println(SearchAlgorithms.Astar(sp, new SokobanDistanceHeuristic()));
           // SearchAlgorithms.greedySearch(rc, h);
        } else if (args.length == 1 || args.length == 2 && args[1].equals("--trace")) {
            boolean trace = args.length == 2 && args[1].equals("--trace");
            Result result = null;
            switch(args[0]) {
                case "DFS" :
                    result = SearchAlgorithms.depthFirst(rc);
                    break;
                case "BFS" :
                    result = SearchAlgorithms.breadthFirst(rc);
                    break;
                case "ID" :
                    result = SearchAlgorithms.iterativeDeepening(rc);
                    break;
                default :
                    System.out.println("Invalid method");
                    break;
            }
            if (trace && result != null) {
                Result it = result;
                while (it != null) {
                    System.out.println(it.node);
                    it = it.parent;
                }
            } 
        } else {
            boolean trace = args.length == 3 && args[2].equals("--trace");
            Result result = null;
            switch(args[1]) {
                case "ImproveHeuristic" :
                    h = ImprovedHeuristic.getInstance();
                    break;
                case "ColorCubesHeuristic" :
                    h = ColorCubesHeuristic.getInstance();
                    break;
                default :
                    System.out.println("Invalid heuristic");
                    return;
            }
            switch(args[0]) {
                case "AStar" :
                    result = SearchAlgorithms.Astar(rc, h);
                    break;
                case "Greedy" :
                    result = SearchAlgorithms.greedySearch(rc, h);
                    break;
                default :
                    System.out.println("Invalid method");
                    break;
            }
            
            if (trace && result != null) {
                Result it = result;
                while (it != null) {
                    System.out.println(it.node);
                    it = it.parent;
                }
            } 
                
        }
    }    
}
