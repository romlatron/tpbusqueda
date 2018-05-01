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
import java.util.Map.Entry;
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
        boolean visited;
        HashMap <Object, Object> visitedNodes = new HashMap<>();
        visitedNodes.put(currentState, null);
        
        int i =0;
        while(!p.isResolved(currentState))  
        {
            i++;
            System.out.print(h.getValue(currentState));
            System.out.println(i);
            
            List<Rule> rules = p.getRules(currentState);
            applyRule = null;
            minScore = Double.POSITIVE_INFINITY ;

            for (Rule<Object> rule : rules)
            {                
                visited = false;
                for (Object o : visitedNodes.keySet())
                    if (o.equals(rule.applyToState(currentState))) visited = true;
                if (!visited) {
                    if (rule.getCost() + h.getValue(rule.applyToState(currentState)) < minScore)
                    {
                        minScore = rule.getCost()+ h.getValue(rule.applyToState(currentState));
                        applyRule = rule;
                    }
                }
            }
            if (applyRule != null) {
                nextState = applyRule.applyToState(currentState);
                visitedNodes.put(nextState, currentState);
                currentState = nextState;
            }
            else {
                currentState = visitedNodes.get(currentState);
            }
        }
        while (currentState != null) {
            System.out.println(currentState);
            currentState = visitedNodes.get(currentState);
        }
        System.out.println(i);
    }
    
    public static boolean Astar(Problem p, Heuristic h)
    {
        Map<Object, Double> openList = new LinkedHashMap<>(); //waiting list containing state and score
        HashMap<Object, Double> closedList = new LinkedHashMap<>(); //list of visited nodes
        boolean resolved = false;
        Object initialState = p.getInitialState();
        Object currentState, nextState, existingClosed;    
        double currentCost, currentScore, nextCost, nextScore;
        boolean visited, waitingList; //to check if the new state is already in one list with a smaller score
        openList.put(initialState, h.getValue(initialState));
        int i = 0;
        
        while(!openList.isEmpty())
        {   
            i++;
            Entry<Object, Double> min = null;
            for (Entry<Object, Double> entry : openList.entrySet()) {
                if (min == null || min.getValue() > entry.getValue()) {
                    min = entry;
                }
            }
            currentState = min.getKey();
            System.out.println(currentState);
            if (p.isResolved(currentState)) {
                resolved = true;
                break;
            }
            currentScore = min.getValue();
            currentCost = min.getValue() - h.getValue(currentState); 
            openList.remove(min.getKey());
            System.out.println(currentScore + " " + h.getValue(currentState) + " " + i);
            List <Rule> rules = p.getRules(currentState);
            
            for (Rule rule : rules) {
            
                visited = false;
                waitingList = false;
                existingClosed = null;
                nextState = rule.applyToState(currentState);
                nextCost = currentCost + rule.getCost();
                nextScore =  nextCost + h.getValue(nextState);
                
                for (Map.Entry<Object, Double> entry : closedList.entrySet()) { //check that the node hasn't been visited yet or with bigger score
                    if (entry.getKey().equals(nextState)) {
                        existingClosed = entry.getKey();
                        visited = entry.getValue() <= nextScore;
                        break;
                    }
                }
                
                if (!visited) {
                
                    Iterator<Map.Entry<Object, Double>> ite = openList.entrySet().iterator();
                    while (ite.hasNext()) {
                        Map.Entry<Object, Double> entry = ite.next();
                        if (entry.getKey().equals(nextState)) {
                            waitingList = entry.getValue()<= nextScore;
                            if (!waitingList) ite.remove();
                            break;
                        }
                    }

                    if (!waitingList) {
                        openList.put(nextState, nextScore);
                        if (existingClosed != null) closedList.remove(existingClosed);
                    }
                }
            }
            closedList.put(currentState, currentScore);
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
