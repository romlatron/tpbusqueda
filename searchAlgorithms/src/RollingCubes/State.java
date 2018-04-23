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

    public List<Rule> getRules() { return rules; }

    public Cube[] getBoard() { return board; }
    
    public int getIndexEmpty() { return indexEmpty; }
    
    @Override
    public String toString()
    {
        String border = "----------------\n";
        String state = border;
        String lines[] = new String[12];
        
        for (int i = 0; i<9; i++)
        {
            switch (board[i].getCurrentColor())
            {
                case WHITE:
                    for (int j = (i%3)*4; j<((i%3)*4)+4; j++)
                    {   
                        if (lines[j] == null)
                            lines[j] = "|oooo|";
                        else
                            lines[j] += "oooo|";
                    }
                    break;

                case BLACK:
                    for (int j = (i%3)*4; j<((i%3)*4)+4; j++)
                    {
                        if (lines[j] == null)
                            lines[j] = "|xxxx|";
                        else
                            lines[j] += "xxxx|";
                    }
                    break;

                case WLEFT:
                    for (int j = (i%3)*4; j<((i%3)*4)+4; j++)
                    {
                        if (lines[j] == null)
                            lines[j] = "|ooxx|";
                        else
                            lines[j] += "ooxx|";
                    }
                    break;

                case WRIGHT:
                    for (int j = (i%3)*4; j<((i%3)*4)+4; j++)
                    {
                        if (lines[j] == null)
                            lines[j] = "|xxoo|";
                        else
                            lines[j] += "xxoo|";
                    }
                    break;

                case WUP:
                    if (lines[(i%3)*4 + 0] == null)
                    {
                        lines[(i%3)*4 + 0] = "|oooo|"; 
                        lines[(i%3)*4 + 1] = "|oooo|";
                        lines[(i%3)*4 + 2] = "|xxxx|";
                        lines[(i%3)*4 + 3] = "|xxxx|";
                    }
                    else
                    {
                        lines[(i%3)*4 + 0] += "oooo|"; 
                        lines[(i%3)*4 + 1] += "oooo|";
                        lines[(i%3)*4 + 2] += "xxxx|";
                        lines[(i%3)*4 + 3] += "xxxx|";
                    }
                    break;

                case WDOWN:
                    if (lines[(i%3)*4 + 0] == null)
                    {
                        lines[(i%3)*4 + 0] = "|xxxx|"; 
                        lines[(i%3)*4 + 1] = "|xxxx|";
                        lines[(i%3)*4 + 2] = "|oooo|";
                        lines[(i%3)*4 + 3] = "|oooo|";
                    }
                    else
                    {
                        lines[(i%3)*4 + 0] += "xxxx|"; 
                        lines[(i%3)*4 + 1] += "xxxx|";
                        lines[(i%3)*4 + 2] += "oooo|";
                        lines[(i%3)*4 + 3] += "oooo|";
                    }
                    break;

                default:
                    for (int j = (i%3)*4; j<((i%3)*4)+4; j++)
                    {
                        if (lines[j] == null)
                            lines[j] = "|    |";
                        else
                            lines[j] += "    |";
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
