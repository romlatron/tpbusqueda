/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import ar.com.itba.sia.*;
import java.util.ArrayList;
import java.util.HashMap;
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
            
            List<Rule>rules = p.getRules(tmpObj);
            for (Rule rule : rules) {
                queue.add(rule.applyToState(tmpObj));
            }
        }
        
        if (p.isResolved(queue.element()))
            System.out.println(queue.element());
    }
    
    public static void greedySearch(Problem p)
    {
        Object currentState  = p.getInitialState();
        Object nextState;
        double minRuleCost;
        
        while(!p.isResolved(currentState))
        {
            List<Rule<Object>> rules = p.getRules(currentState);
            minRuleCost = rules.get(0).getCost();
            nextState = rules.get(0).applyToState(currentState);
            
            for (Rule<Object> rule : rules)
            {
                if (rule.getCost() < minRuleCost)
                {
                    minRuleCost = rule.getCost();
                    nextState = rule.applyToState(currentState);
                }
            }
            currentState = nextState;
        }
    }
    
    public static List<Object> Astar(Problem p, Heuristic h)
    {
        List<Object> bestPath = new ArrayList<Object>();
        HashMap<Object, Double> visitedStates = new HashMap<>();
        boolean isListModified = true;
        
        Object initialState = p.getInitialState();
        double rootAstarScore = h.getValue(initialState);
        
        
        while(isListModified)
        {
            
        }
        
        return bestPath;
    }
        
    public static void iterativeDeepening (Problem p) {
        Object actualState = p.getInitialState();
        int maxDepth = 1;
        while (!depthFirstLim(p, actualState, maxDepth) ) {
            maxDepth ++;
        }
            
    }
    
    //recursive algorithm used by iterative deepening
    public static boolean depthFirstLim (Problem p, Object state, int limite) {
        if (limite == 0)
            return false;
        System.out.println(state);
        if (p.isResolved(state)) {
            System.out.println("Bingo");
            return true;
        }
        List <Rule> rules = p.getRules(state);
        for (Rule rule : rules) {
            if (depthFirstLim(p, rule.applyToState(state), limite - 1))
                return true;
        }
        
        return false;           
    }
}
