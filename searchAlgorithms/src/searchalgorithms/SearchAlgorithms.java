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
import java.util.Stack;
import java.util.TreeMap;
//import javafx.util.Pair;


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
    
    public static void greedySearch(Problem p)
    {
        Object currentState  = p.getInitialState();
        Object nextState;
        double minRuleCost;
        
        while(!p.isResolved(currentState))
        {
            System.out.println(currentState.toString());
            List<Rule<Object>> rules = p.getRules(currentState);
            minRuleCost = rules.get(0).getCost();
            nextState = rules.get(0).applyToState(currentState);
            
//            for (Rule<Object> rule : rules)
//            {
//                if (rule.getCost() < minRuleCost)
//                {
//                    minRuleCost = rule.getCost();
//                    nextState = rule.applyToState(currentState);
//                }
//            }
            int i = 1;
            while (i < rules.size() && nextState.equals(currentState))
            {
                if (rules.get(i).getCost() <= minRuleCost)
                {
                    minRuleCost = rules.get(i).getCost();
                    nextState = rules.get(i).applyToState(currentState);
                }
                i++;
            }
            currentState = nextState;
        }
    }
    
//    public static boolean Astar(Problem p, Heuristic h)
//    {
//        Map<Double, Pair<Object, Double>> openList = new TreeMap<>(); //contains f(state) and g(state)
//        HashMap<Object, Double> closedList = new LinkedHashMap<>(); 
//        boolean resolved = false;
//        Object initialState = p.getInitialState();
//        openList.put(h.getValue(initialState), new Pair<> (initialState, 0.0));
//        
//        while(!openList.isEmpty())
//        {            
//            Map.Entry<Double, Pair<Object, Double>> pair = openList.entrySet().iterator().next();
//            Object currentState = pair.getValue().getKey();
//            if (p.isResolved(currentState)) {
//                resolved = true;
//                break;
//            }
//            double currentScore = pair.getKey();
//            closedList.remove(pair);
//            
//            List <Rule> rules = p.getRules(currentState);
//            for (Rule rule : rules) {
//                
//                Object nextState = rule.applyToState(currentState);
//                double nextCost = pair.getValue().getValue() + rule.getCost();
//                double nextScore = h.getValue(nextState);
//                
//                for (Map.Entry<Double, Pair<Object, Double>> entry : openList.entrySet()) { //check that the node hasn't been visited yet or with bigger score
//                    if (entry.getValue().getKey().equals(nextState)) {
//                        if (entry.getKey() > currentScore) {
//                            
//                            for (Map.Entry<Object, Double> entryClosed : closedList.entrySet()) { //check the same with closed list
//                                if (entryClosed.getKey().equals(currentState)) {
//                                    if (entryClosed.getValue() > currentScore) {
//                                        openList.remove(entry.getValue());
//                                        openList.put(nextScore, new Pair<> (nextState, nextCost));
//                                        closedList.remove(entryClosed.getKey());
//                                        closedList.put(currentState, currentScore);
//                                    }
//                                    break;
//                                }
//                            }
//                        }
//                        
//                        break;
//                    }
//                }
//            }
//        }
//        return resolved;
//    }
        
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
        //System.out.println(depth);
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


