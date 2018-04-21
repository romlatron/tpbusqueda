/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RollingCubes;

import searchalgorithms.*;
import ar.com.itba.sia.Rule;
import java.util.List;
/**
 *
 * @author Acer
 */
public class State {
    
    public List <Rule> rules;
    public Cube[] board;
    

    public State(List<Rule> rules, Cube[] board) 
    {
        this.rules = rules;
        this.board = board;
    }

    public List<Rule> getRules() { return rules; }

    public Cube[] getBoard() { return board; }
}
