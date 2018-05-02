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
        
        // Count black cubes
        for (Cube c : board) if (c.getCurrentColor() == Cube.color.BLACK) count++;
        // Check if middle is empty
        if (board[4].getCurrentColor() != Cube.color.EMPTY) count++;
        
        return count;
    }
    
    
}
