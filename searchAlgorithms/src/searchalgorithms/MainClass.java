/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import ar.com.itba.sia.*;
import RollingCubes.RollingCubes;
import RollingCubes.*;
import RollingCubes.ColorCubesHeuristic;
import RollingCubes.ImprovedHeuristic;
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
    public static void main(String[] args) 
    {
        
        RollingCubes rc = new RollingCubes();
//        SearchAlgorithms.depthFirst(rc);
        
        Heuristic h = ImprovedHeuristic.getInstance();

        if (args.length==0) { 
            //SearchAlgorithms.depthFirst(rc);
           SearchAlgorithms.Astar(rc, h);
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
