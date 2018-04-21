/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import ar.com.itba.sia.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        List <Rule> goLeftRight = new ArrayList<>();
        goLeftRight.add(new TreeRuleLeft());
        goLeftRight.add(new TreeRuleRight());
        TreeState init = new TreeState (1, goLeftRight);
        TreeState fin = new TreeState (6, goLeftRight);
        TreeExample tree = new TreeExample (init, fin);
        
        SearchAlgorithms.iterativeDeepening (tree);

    }
    
}
