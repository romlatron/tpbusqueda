/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import ar.com.itba.sia.*;
import RollingCubes.RollingCubes;
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
        Heuristic h = null;
        
        if (args.length==0) { 
            System.out.println("Missing arguments");
            return;
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
                    return;
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
                    return;
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