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
import java.util.Stack;



/**
 *
 * @author Kevin
 */
public class SearchAlgorithms
{
    public static void depthFirst(Problem p)
    {
        int i=0;
        Stack<Object> stack = new Stack<>();
        List<Object> visitedNodes = new ArrayList<>();
        stack.add(p.getInitialState());
        visitedNodes.add(p.getInitialState());
        
        while(!stack.isEmpty() && !p.isResolved(stack.peek()))
        {
            Object tmpObj = stack.pop();
            System.out.println(i);
            System.out.println(tmpObj);
            
            List<Rule>rules = p.getRules(tmpObj);
            for (Rule rule : rules)
            {
                Object currentState = rule.applyToState(tmpObj);
                
                if (!visitedNodes.contains(currentState))
                {
                    visitedNodes.add(currentState);
                    stack.push(currentState);
                    i++;
                }
            }
        }
        if (p.isResolved(stack.peek()))
            System.out.println(stack.peek());
    }
    
    public static void breadthFirst(Problem p)
    {
        Queue<Object> queue = new LinkedList<Object>();
        List<Object> visitedNodes = new ArrayList<>();
        queue.add(p.getInitialState());
        visitedNodes.add(p.getInitialState());
        
        while(!queue.isEmpty() && !p.isResolved(queue.element()))
        {   
            Object tmpObj = queue.poll();
            System.out.println(tmpObj);
            
            List<Rule>rules = p.getRules(tmpObj);
            for (Rule rule : rules) 
            {
                Object currentState = rule.applyToState(tmpObj);
                
                if (!visitedNodes.contains(currentState))
                {
                    visitedNodes.add(currentState);
                    queue.add(currentState);
                }
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
        int profundidad = 0;
        while (currentState != null) {
            profundidad++;
            System.out.println(currentState);
            currentState = visitedNodes.get(currentState);
        }
        System.out.println(i);
        System.out.println("Profundidad de la solucion : " + profundidad);
        
        int nExpandidos = 0;
        nExpandidos = visitedNodes.entrySet().stream().map((_item) -> 1).reduce(nExpandidos, Integer::sum);
        System.out.println("Nodos expandidos : " + nExpandidos);
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
                
        int nFrontera = 0;
        nFrontera = openList.entrySet().stream().map((_item) -> 1).reduce(nFrontera, Integer::sum);
        System.out.println("Nodos frontera : " + nFrontera);
        
        int nExpandidos = 0;
        nExpandidos = openList.entrySet().stream().map((_item) -> 1).reduce(nExpandidos, Integer::sum);
        System.out.println("Nodos expandidos : " + nExpandidos);
        
        System.out.println("Nodos generados en total : " + (nExpandidos + nFrontera));
       
        return resolved;
    }
        
    public static void iterativeDeepening (Problem p) 
    {
        Object actualState = p.getInitialState();
        System.out.println(actualState);
        int depth = 1;
        int i = 1;
        
        while (!depthFirstLim(p, actualState, depth))
            depth++;
        
        System.out.println("BINGO !!!");
    }
    
    //recursive algorithm used by iterative deepening
    private static boolean depthFirstLim (Problem p, Object state, int depth) 
    {   
        if (p.isResolved(state))
        {
            System.out.println(state);
            return true;
        }
        if (depth > 0)
        {
            List<Object> visitedNodes = new ArrayList<>();
            visitedNodes.add(state);
            List <Rule> rules = p.getRules(state);
            for (Rule rule : rules) 
            {
                Object nextState = rule.applyToState(state);
                if (!visitedNodes.contains(nextState))
                {
                    System.out.println(nextState);
                    if (depthFirstLim(p, nextState, depth-1))
                        return true;
                }
                if (depth == 1)
                    visitedNodes.add(nextState);
            }
        }
        return false;           
    }
}
