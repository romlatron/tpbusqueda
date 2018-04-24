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
public class ColorCubesHeuristic implements Heuristic{
    private static ColorCubesHeuristic cch = null;
    
    private ColorCubesHeuristic () {};
    
    public static ColorCubesHeuristic getInstance()
    {
        if (cch == null)
            cch = new ColorCubesHeuristic();
        
        return cch;
    }
    
    @Override
    public double getValue(Object e) {
        State s = (State) e;
        double count = 0;        
        Cube [] board = s.getBoard();
        for (Cube c : board) 
            switch(c.getCurrentColor()) {
                case WUP :
                case WDOWN :    
                case WLEFT :
                case WRIGHT :
                    count += 1;
                    break;
                    
                case BLACK :
                    count += 2;
                    break;
                    
                default :
                    break;
            }
        return count;
    }
    
    
}
