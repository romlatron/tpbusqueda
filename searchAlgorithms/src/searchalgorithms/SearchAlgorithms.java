/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 *
 * @author Kevin
 */
public class SearchAlgorithms
{
    public static boolean depthFirst (Problem p, Object state) {
        System.out.println(state);
        if (p.isResolved(state)) {
            System.out.println("Bingo");
            return true;
        }
        List <Rule> rules = p.getRules(state);
        for (Rule rule : rules) {
            if (depthFirst(p, rule.applyToState(state)))
                return true;
        }
        
        return false;           
    }
    
    public static void breadthFirst(Problem p)
    {
        Queue<Object> queue = new LinkedList<Object>();
        queue.add(p.getInitialState());
        
        while(!queue.isEmpty() && !p.isResolved(queue.element()))
        {   
            Object tmpObj = queue.poll();
            System.out.println(tmpObj);
            
            List<Rule<Object>>rules = p.getRules(tmpObj);
            for (Rule<Object> rule : rules)
                queue.add(rule.applyToState(tmpObj));   
        }
        
        if (p.isResolved(queue.element()))
            System.out.println(queue.element());
    }
    
}
