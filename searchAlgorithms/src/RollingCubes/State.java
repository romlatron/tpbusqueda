/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RollingCubes;

import ar.com.itba.sia.Rule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Acer
 */
public class State {
    
    private List <Rule> rules;
    private Cube[] board;
    private int indexEmpty;

    public State(Cube[] board, int index) 
    {        
        this.board = board;
        this.indexEmpty = index;
        
        List <Rule> nextPossibleRules = new ArrayList();
        
        if (indexEmpty >= 3) {     
            nextPossibleRules.add(new ClickUp());
        }
        
        if (indexEmpty <= 5) {     
            nextPossibleRules.add(new ClickDown());            
        }
                
        if (indexEmpty != 2 && indexEmpty != 5 && indexEmpty != 8) {
            nextPossibleRules.add(new ClickRight());            
        }
        
        if (indexEmpty != 0 && indexEmpty != 3 && indexEmpty != 6) {
            nextPossibleRules.add(new ClickLeft());
        }
        
        this.rules = nextPossibleRules;
    }
    
    public State(State s)
    {
        this.board = new Cube[9];
        for (int i=0; i<s.board.length; i++)
            this.board[i] = new Cube(s.board[i].getCurrentColor());
        
        this.indexEmpty = s.indexEmpty;
    }
    
    public State(Cube[] newBoard) { this.board = newBoard; }
    
    private void permutePosition(State state, int pos1, int pos2)
    {
        Cube tmp = state.board[pos1];
        state.board[pos1] = state.board[pos2];
        state.board[pos2] = tmp;
    }
    
    public State applyVerticalSymmetry(State currentState)
    {
        State symmetricState = new State(currentState);
        permutePosition(symmetricState, 0, 2);
        permutePosition(symmetricState, 3, 5);
        permutePosition(symmetricState, 6, 8);
        
        for (Cube c : symmetricState.getBoard())
        {
            switch(c.getCurrentColor())
            {
                case WLEFT:
                    c.setCurrentColor(Cube.color.WRIGHT);
                    break;
                    
                case WRIGHT:
                    c.setCurrentColor(Cube.color.WLEFT);
                    break;
                    
                default:
                    break;
            }
        }
        return symmetricState;
    }
    
    public State applyHorizontalSymmetry(State currentState)
    {
        State symmetricState = new State(currentState);
        permutePosition(symmetricState, 0, 6);
        permutePosition(symmetricState, 1, 7);
        permutePosition(symmetricState, 2, 8);
        
        for (Cube c : symmetricState.getBoard())
        {
            switch(c.getCurrentColor())
            {
                case WDOWN:
                    c.setCurrentColor(Cube.color.WUP);
                    break;
                    
                case WUP:
                    c.setCurrentColor(Cube.color.WDOWN);
                    break;
                    
                default:
                    break;
            }
        }
        return symmetricState;
    }
 
    private State apply180Rotation(State currentState)
    {
        return applyHorizontalSymmetry(applyVerticalSymmetry(currentState));
    }
    
    public List<State> applySymmetry() {
        return this.applySymmetry(this);
    }
    
    public List<State> applySymmetry(State s)
    {  
        List symmetricNode = new ArrayList<>();

        symmetricNode.add(s);
        symmetricNode.add(applyHorizontalSymmetry(s));
        symmetricNode.add(applyVerticalSymmetry(s));
        symmetricNode.add(apply180Rotation(s));
        
        return symmetricNode;
    }

    public List<Rule> getRules() { return rules; }

    public Cube[] getBoard() { return board; }
    
    public int getIndexEmpty() { return indexEmpty; }
    
    public boolean strictEquals(Object object) {
        if (object == null) 
            return false;
        State s = (State) object;
        for (int i = 0; i<9; i++) {
            if (this.board[i].getCurrentColor() != s.getBoard()[i].getCurrentColor())
                return false;
        }
        return true;
    }

    
    @Override
    public boolean equals(Object object) {
        if (object == null) 
            return false;
        
        State s = (State) object;
        if (s.strictEquals(this)) return true;
        for(State symmetricState: applySymmetry(s)) {
            if (symmetricState.strictEquals(this)) return true;
        }
        return false;
    }
    
//    @Override
//    public boolean equals(Object object) {
//        if (object == null) 
//            return false;
//        
//        State s = (State) object;
//        List<Object> symmetricStates = new ArrayList<>();
//        s.applySymmetry(symmetricStates);
//        
//        for (int i = 0; i<symmetricStates.size(); i++) 
//            if (this.sameBoard(symmetricStates.get(i)))
//                return true;
//                
//        return false;
//    }
    
    public State applyRotationalSymmetry(State currentState)
    {
        return applyHorizontalSymmetry(applyVerticalSymmetry(currentState));
    }

    public int hashBoard() {
        int hash = 0;
        for (int i=0; i<9; i++)
            switch(this.board[i].getCurrentColor())
            {
                case WDOWN:
                    hash += Math.pow(7,i)*1;
                    break;                    
                case WUP:
                    hash += Math.pow(7,i)*2;
                    break;
                    
                case WLEFT:
                    hash += Math.pow(7,i)*3;
                    break;
                    
                case WRIGHT:
                    hash += Math.pow(7,i)*4;
                    break;
                case WHITE:
                    hash += Math.pow(7,i)*5;
                    break;
                case BLACK:
                    hash += Math.pow(7,i)*6;
                    break;
                case EMPTY:
                    hash += Math.pow(7,i)*0;
                    break;    
                default:
                    break;
            }
        return hash;
    }
    
    @Override
    public int hashCode() {
        int hash = this.hashBoard()
                + applyHorizontalSymmetry(this).hashBoard()
                + applyVerticalSymmetry(this).hashBoard()
                + applyRotationalSymmetry(this).hashBoard();
        return hash;
    }
    
    @Override
    public String toString()
    {
        String border = "----------------------------\n";
        String state = border;
        String lines[] = new String[12];
        
        for (int i = 0; i<9; i++)
        {
            switch (board[i].getCurrentColor())
            {
                case WHITE:
                    for (int j = (i/3)*4; j<((i/3)*4)+4; j++)
                    {   
                        if (lines[j] == null)
                            lines[j] = "|oooooooo|";
                        else
                            lines[j] += "oooooooo|";
                    }
                    break;

                case BLACK:
                    for (int j = (i/3)*4; j<((i/3)*4)+4; j++)
                    {
                        if (lines[j] == null)
                            lines[j] = "|xxxxxxxx|";
                        else
                            lines[j] += "xxxxxxxx|";
                    }
                    break;

                case WLEFT:
                    for (int j = (i/3)*4; j<((i/3)*4)+4; j++)
                    {
                        if (lines[j] == null)
                            lines[j] = "|ooooxxxx|";
                        else
                            lines[j] += "ooooxxxx|";
                    }
                    break;

                case WRIGHT:
                    for (int j = (i/3)*4; j<((i/3)*4)+4; j++)
                    {
                        if (lines[j] == null)
                            lines[j] = "|xxxxoooo|";
                        else
                            lines[j] += "xxxxoooo|";
                    }
                    break;

                case WUP:
                    if (lines[(i/3)*4 + 0] == null)
                    {
                        lines[(i/3)*4 + 0] = "|oooooooo|"; 
                        lines[(i/3)*4 + 1] = "|oooooooo|";
                        lines[(i/3)*4 + 2] = "|xxxxxxxx|";
                        lines[(i/3)*4 + 3] = "|xxxxxxxx|";
                    }
                    else
                    {
                        lines[(i/3)*4 + 0] += "oooooooo|"; 
                        lines[(i/3)*4 + 1] += "oooooooo|";
                        lines[(i/3)*4 + 2] += "xxxxxxxx|";
                        lines[(i/3)*4 + 3] += "xxxxxxxx|";
                    }
                    break;

                case WDOWN:
                    if (lines[(i/3)*4 + 0] == null)
                    {
                        lines[(i/3)*4 + 0] = "|xxxxxxxx|"; 
                        lines[(i/3)*4 + 1] = "|xxxxxxxx|";
                        lines[(i/3)*4 + 2] = "|oooooooo|";
                        lines[(i/3)*4 + 3] = "|oooooooo|";
                    }
                    else
                    {
                        lines[(i/3)*4 + 0] += "xxxxxxxx|"; 
                        lines[(i/3)*4 + 1] += "xxxxxxxx|";
                        lines[(i/3)*4 + 2] += "oooooooo|";
                        lines[(i/3)*4 + 3] += "oooooooo|";
                    }
                    break;

                default:
                    for (int j = (i/3)*4; j<((i/3)*4)+4; j++)
                    {
                        if (lines[j] == null)
                            lines[j] = "|        |";
                        else 
                            lines[j] += "        |";
                    }
                    break;
            }
        }
        
        for (int k = 0; k<12; k++)
        {
            if (k == 4 || k == 8)
                state += border;
            
            state = state + lines[k] + "\n";
        }
        state += border;
        
        return state;
    }
}
