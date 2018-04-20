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
    public void depthFirst(Problem p, Object currentState, boolean isResolved)
    {
        isResolved = p.isResolved(currentState);
        if (isResolved)
            return;
        
        List<Rule<Object>>rules = p.getRules(currentState);
        for (Rule<Object> rule : rules)
            depthFirst(p, rule.applyToState(currentState), isResolved);
    }
    
    public void breadthFirst(Problem p)
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
    }
    
}
