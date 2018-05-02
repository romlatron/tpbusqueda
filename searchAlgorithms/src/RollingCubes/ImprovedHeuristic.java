/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RollingCubes;

import ar.com.itba.sia.Heuristic;
/**
 *
 * @author Acer
 */
public class ImprovedHeuristic implements Heuristic{
    private static ImprovedHeuristic cch = null;
    
    private ImprovedHeuristic () {};
    
    public static ImprovedHeuristic getInstance()
    {
        if (cch == null)
            cch = new ImprovedHeuristic();
        
        return cch;
    }
    
    @Override
    public double getValue(Object e) {
        State s = (State) e;
        double count = 0;  
        double minCost = 0;
        Cube [] board = s.getBoard();
        for (int i = 0; i<9; i++) 
            switch(board[i].getCurrentColor()) {
                case WUP :
                    if (i==6 || i==8) {
                        if (minCost < 9)
                            minCost = 9;
                        count += 3;
                    }
                    else if (i == 7) {
                        if (minCost < 12)
                            minCost = 12;
                        count += 4;
                    }
                    else count += 1 ;
                    break; 
                case WDOWN :
                    if (i==0 || i==2) {
                        if (minCost < 9)
                            minCost = 9;
                        count += 3;
                    }
                    else if (i == 1) {
                        if (minCost < 12)
                            minCost = 12;
                        count += 4;
                    }
                    else count += 1 ;
                    break;  
                case WLEFT :
                    if (i==2 || i==8) {
                        if (minCost < 9)
                            minCost = 9;
                        count += 3;
                    }
                    else if (i == 3) {
                        if (minCost < 12)
                            minCost = 12;
                        count += 4;
                    }
                    else count += 1 ;
                    break; 
                case WRIGHT :
                    if (i==0 || i==6) {
                        if (minCost < 9)
                            minCost = 9;
                        count += 3;
                    }
                    else if (i == 5) {
                        if (minCost < 12)
                            minCost = 12;
                        count += 4;
                    }
                    else count += 1 ;
                    break; 
                    
                case BLACK :
                    if (i==4) { 
                        if (minCost < 15) {
                            minCost = 15;
                            count += 5;
                        }
                    }
                    else {
                        if (minCost < 6)
                            minCost = 6; 
                        count += 2;
                    }
                    break;
                
                default :
                    break;
            }
        if (count > minCost)
            return count;
        else return minCost;
    }
    
    
}
