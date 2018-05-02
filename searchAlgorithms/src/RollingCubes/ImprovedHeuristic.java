/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RollingCubes;

import ar.com.itba.sia.Heuristic;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
        int emptyIndex = s.getIndexEmpty();
        int column = emptyIndex % 3;
        Cube [] board = s.getBoard();
        
        // First we get the indexes for the places around the empty cube.
        // Notice that we have to delete indexes outside the bound [0, 9]
        // Also, note that if the empty cube is in a column on a side, we
        // should only check to one siding cube. Thats why we check for the
        // column when creating the list.
        List<Integer> aroundEmptyUnfilteredIdxs = Arrays.asList(emptyIndex - 3, emptyIndex + 3, column > 0 ? emptyIndex - 1 : -1, column < 2 ? emptyIndex + 1 : -1);
        List<Integer> aroundEmptyFilteredIdxs = aroundEmptyUnfilteredIdxs.stream().filter(item -> item > 0 && item < 10).collect(Collectors.toList());

        // TODO: WHY IS IT RETURNING 0.

        for (int i = 0; i<9; i++) {
            Cube cube = board[i];
            Boolean cubeIsBlack = cube.getCurrentColor() == Cube.color.BLACK;
            Boolean cubeIsWhite = cube.getCurrentColor() == Cube.color.WHITE;
            Boolean cubeIsEmpty = cube.getCurrentColor() == Cube.color.EMPTY;

            if (cubeIsBlack) count += i == 4 ? 6 : 4;
            if (cubeIsWhite && i == 4) count += 6;
            if (cubeIsBlack || cubeIsWhite || cubeIsEmpty) continue;
            
            if (aroundEmptyFilteredIdxs.contains(i)) {
                if (i + 3 == emptyIndex && cube.getCurrentColor() != Cube.color.WUP ) count += 5; // Above cube
                if (i - 3 == emptyIndex && cube.getCurrentColor() != Cube.color.WDOWN ) count += 5; // Bottom cube
                if (i - 1 == emptyIndex && cube.getCurrentColor() != Cube.color.WRIGHT ) count += 5; // Left cube
                if (i + 1 == emptyIndex && cube.getCurrentColor() != Cube.color.WLEFT ) count += 5; // Right cube
            } else if (i != 4) count += 5; // Other striped cubes on the border
        }
        
        if (count == 0) System.out.println(s);
        
        return count;
    }
    
    
}
