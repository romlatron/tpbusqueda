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
public class ClickDown extends RCRule{

    @Override
    public Object applyToState(Object e) {
        State s = (State) e;
        Cube board[] = new Cube[9];
        for (int i=0; i<9; i++)
            board[i] = new Cube (s.getBoard()[i].getCurrentColor());
        int index = s.getIndexEmpty();
        
        switch (board[index+3].getCurrentColor()) { 
            case WHITE : 
                board[index].setCurrentColor(Cube.color.WUP);
                break;
                
            case BLACK : 
                board[index].setCurrentColor(Cube.color.WDOWN);
                break;
            
            case WUP : 
                board[index].setCurrentColor(Cube.color.BLACK);
                break;    
                
            case WDOWN : 
                board[index].setCurrentColor(Cube.color.WHITE);
                break;    
            
            default : 
                board[index].setCurrentColor(board[index+3].getCurrentColor());
        }
        board[index+3].setCurrentColor(Cube.color.EMPTY);
                        
        State newState = new State(board, index+3);
        
        return newState;
    }
    
    public ClickDown() { }
    
}
