/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RollingCubes;

import ar.com.itba.sia.Rule;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class ClickUp extends RCRule{

    @Override
    public Object applyToState(Object e) {
        State s = (State) e;
        Cube board[] = s.getBoard();
        int index = s.getIndexEmpty();
        Cube empty = board[index];
        
        switch (board[index-3].getCurrentColor()) { //index-3 : above cube
            case WHITE : 
                board[index].setCurrentColor(Cube.color.WDOWN);
                break;
                
            case BLACK : 
                board[index].setCurrentColor(Cube.color.WUP);
                break;
            
            case WUP : 
                board[index].setCurrentColor(Cube.color.WHITE);
                break;    
                
            case WDOWN : 
                board[index].setCurrentColor(Cube.color.BLACK);
                break;    
            
            default : 
                board[index].setCurrentColor(board[index-3].getCurrentColor());
        }
        board[index-3].setCurrentColor(Cube.color.EMPTY);
                        
        State newState = new State(board, index-3);
        
        return newState;
    }
    
}