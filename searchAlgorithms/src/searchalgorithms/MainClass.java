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
        Heuristic h = ImprovedHeuristic.getInstance();
        if (args.length==0) { 
           System.out.println(SearchAlgorithms.Astar(rc, h));
            //SearchAlgorithms.greedySearch(rc, h);
        } else if (args.length == 1) {
            switch(args[0]) {
                case "DFS" :
                    SearchAlgorithms.depthFirst(rc);
                    break;
                case "BFS" :
                    SearchAlgorithms.breadthFirst(rc);
                    break;
                case "ID" :
                    SearchAlgorithms.iterativeDeepening(rc);
                    break;
                default :
                    System.out.println("Invalid method");
                    break;
            }
        } else {
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
                    System.out.println(SearchAlgorithms.Astar(rc, h));
                    break;
                case "Greedy" :
                    SearchAlgorithms.greedySearch(rc, h);
                    break;
                default :
                    System.out.println("Invalid method");
                    break;
            }
                
        } 
        
    }

    
}
