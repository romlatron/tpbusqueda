/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import ar.com.itba.sia.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import javafx.util.Pair;


/**
 *
 * @author Kevin
 */
public class SearchAlgorithms
{
    public static boolean depthFirst (Problem p, Object state, Object previousState) {
        System.out.println(state);
        if (p.isResolved(state)) {
            System.out.println("Bingo");
            return true;
        }
        List <Rule> rules = p.getRules(state);
        for (Rule rule : rules) {
            if (!rule.applyToState(state).equals(previousState)) {
                if (depthFirst(p, rule.applyToState(state), state))
                    return true;
            }
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
    
    public static void greedySearch(Problem p, Heuristic h)
    {
        Object currentState  = p.getInitialState();
        Object nextState;
        Rule applyRule;
        double minScore;
        HashMap <Object, Object> visitedNodes = new HashMap<>();
        visitedNodes.put(currentState, null);
        
        while(!p.isResolved(currentState))
        {
            System.out.println(currentState);
            
            List<Rule> rules = p.getRules(currentState);
            applyRule = null;
            minScore = Double.POSITIVE_INFINITY ;
            
            for (Rule<Object> rule : rules)
            {
                if (!visitedNodes.keySet().contains(rule.applyToState(currentState))) {
                    if (rule.getCost() + h.getValue(rule.applyToState(currentState)) < minScore)
                    {
                        minScore = rule.getCost();
                        applyRule = rule;
                    }
                }
            }
            if (applyRule != null) {
                System.out.println("RULE APPLIED");
                nextState = applyRule.applyToState(currentState);
                visitedNodes.put(nextState, currentState);
                currentState = nextState;
            }
            else {
                System.out.println("RULE not APPLIED");
                currentState = visitedNodes.get(currentState);
            }
        }
    }
    
    public static boolean Astar(Problem p, Heuristic h)
    {
        Map<Double, Pair<Object, Double>> openList = new TreeMap<>(); //contains f(state) and g(state)
        HashMap<Object, Double> closedList = new LinkedHashMap<>(); 
        boolean resolved = false;
        Object initialState = p.getInitialState();
        openList.put(h.getValue(initialState), new Pair<> (initialState, 0.0));
        
        while(!openList.isEmpty())
        {            
            Map.Entry<Double, Pair<Object, Double>> pair = openList.entrySet().iterator().next();
            Object currentState = pair.getValue().getKey();
            if (p.isResolved(currentState)) {
                resolved = true;
                break;
            }
            double currentScore = pair.getKey();
            closedList.remove(pair);
            
            List <Rule> rules = p.getRules(currentState);
            for (Rule rule : rules) {
                
                Object nextState = rule.applyToState(currentState);
                double nextCost = pair.getValue().getValue() + rule.getCost();
                double nextScore = h.getValue(nextState);
                
                for (Map.Entry<Double, Pair<Object, Double>> entry : openList.entrySet()) { //check that the node hasn't been visited yet or with bigger score
                    if (entry.getValue().getKey().equals(nextState)) {
                        if (entry.getKey() > currentScore) {
                            
                            for (Map.Entry<Object, Double> entryClosed : closedList.entrySet()) { //check the same with closed list
                                if (entryClosed.getKey().equals(currentState)) {
                                    if (entryClosed.getValue() > currentScore) {
                                        openList.remove(entry.getValue());
                                        openList.put(nextScore, new Pair<> (nextState, nextCost));
                                        closedList.remove(entryClosed.getKey());
                                        closedList.put(currentState, currentScore);
                                    }
                                    break;
                                }
                            }
                        }
                        
                        break;
                    }
                }
            }
        }
        return resolved;
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
