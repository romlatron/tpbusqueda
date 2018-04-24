/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RollingCubes;

/**
 *
 * @author Acer
 */
public class ClickLeft extends RCRule{

    @Override
    public Object applyToState(Object e) {
        State s = (State) e;
        Cube board[] = new Cube[9];
        for (int i=0; i<9; i++)
            board[i] = new Cube (s.getBoard()[i].getCurrentColor());
        int index = s.getIndexEmpty();
        
        switch (board[index-1].getCurrentColor()) { 
            case WHITE : 
                board[index].setCurrentColor(Cube.color.WRIGHT);
                break;
                
            case BLACK : 
                board[index].setCurrentColor(Cube.color.WLEFT);
                break;
            
            case WRIGHT : 
                board[index].setCurrentColor(Cube.color.BLACK);
                break;    
                
            case WLEFT : 
                board[index].setCurrentColor(Cube.color.WHITE);
                break;    
            
            default : 
                board[index].setCurrentColor(board[index-1].getCurrentColor());
        }
        board[index-1].setCurrentColor(Cube.color.EMPTY);
                        
        State newState = new State(board, index-1);
        
        return newState;
    }
    
    public ClickLeft() { }
    
}
