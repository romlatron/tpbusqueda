/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import RollingCubes.State;
import ar.com.itba.sia.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.TimeUnit;



/**
 *
 * @author Kevin
 */
public class SearchAlgorithms
{
    
    private static class Result {
        public int depth = 0;
        public Object node;
        
        public Result (Result result) {
            this.depth = result.depth;
            this.node = result.node;
        }
        
        public Result (Object node) {
            this.depth = depth;
            this.node = node;
        }
        
        public Result (Object node, int depth) {
            this.depth = depth;
            this.node = node;
        }
        
        public boolean equals(Object o) {
            Result r = (Result) o;
            return r.node.equals(node);
        }
        
        public String toString() {
            return node.toString();
        }
        
        public int hashCode() {
            return node.hashCode();
        }
    }
        
    public static void depthFirst(Problem p)
    {
        int exploded = 0;
        Deque<Object> frontier = new LinkedList<>();
        HashSet<Object> visitedNodes = new HashSet<>();
        long startTime = System.nanoTime();
        frontier.add(p.getInitialState());

        while(!frontier.isEmpty())
        {   
            Object currentState = frontier.pop();
            exploded++;
            if (p.isResolved((currentState))) {
                        long estimatedTime = System.nanoTime() - startTime;
                        System.out.println(currentState);
                        System.out.println("Nodos frontera: " + frontier.size());
                        System.out.println("Nodos Explotados: " + exploded);
                        System.out.println("Nodos Generados: " + (frontier.size() + exploded));
                        //System.out.println("Profundidad: " + currentState.depth);
                        System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
                        // TODO: Check frontier and exploded
                        return;
            }
            
            List<Rule> rules = p.getRules(currentState);
            for (Rule rule : rules) {
                
                if (!visitedNodes.contains(rule.applyToState(currentState))) {
                    frontier.push(rule.applyToState(currentState));                    
                }
            }
            visitedNodes.add(currentState);
        }
    }
    
    public static void breadthFirst(Problem p)
    {
        int exploded = 0;
        Queue<Result> frontier = new LinkedList<>();
        Map<Result, Integer> nodeDepths = new HashMap<>();
        long startTime = System.nanoTime();
        frontier.add(new Result(p.getInitialState()));

        while(!frontier.isEmpty())
        {   
            Result parentState = frontier.poll();
            nodeDepths.put(parentState, parentState.depth);
            exploded++;
            
            List<Rule> rules = p.getRules(parentState.node);
            for (Rule rule : rules) {
                Result currentState = new Result(rule.applyToState(parentState.node), parentState.depth + 1);
                Integer oldDepth = nodeDepths.get(currentState);
                
                if (oldDepth == null || oldDepth > currentState.depth) {
                    frontier.add(currentState);
                    if (p.isResolved((currentState.node))) {
                        long estimatedTime = System.nanoTime() - startTime;
                        System.out.println(currentState.node);
                        System.out.println("Nodos frontera: " + frontier.size());
                        System.out.println("Nodos Explotados: " + exploded);
                        System.out.println("Nodos Generados: " + (frontier.size() + exploded));
                        System.out.println("Profundidad: " + currentState.depth);
                        System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
                        // TODO: Check generated nodes
                        return;
                    }
                }
            }
        }
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
        long startTime = System.nanoTime();

        while(!p.isResolved(currentState))  
        {
            
            List<Rule> rules = p.getRules(currentState);
            applyRule = null;
            minScore = Double.POSITIVE_INFINITY ;

            for (Rule<Object> rule : rules)
            {                
                if (!visitedNodes.keySet().contains(rule.applyToState(currentState))) {   
                    i++;
                    if (rule.getCost() + h.getValue(rule.applyToState(currentState)) < minScore){
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
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
        
        int profundidad = 0;
        while (currentState != null) {
            profundidad++;
            //System.out.println(currentState);
            currentState = visitedNodes.get(currentState);
        }
        System.out.println("Profundidad de la solucion : " + profundidad);
        
        int nExpandidos = 0;
        nExpandidos = visitedNodes.entrySet().stream().map((_item) -> 1).reduce(nExpandidos, Integer::sum);
        System.out.println("Nodos expandidos : " + nExpandidos);
        
        System.out.println("Nodos frontera : " + (i - nExpandidos));
    }
    
    public static boolean Astar(Problem p, Heuristic h)
    {
        HashMap<Object, Double> openList = new HashMap<>(); //waiting list containing state and score
        HashMap<Object, Double> closedList = new HashMap<>(); //list of visited nodes
        boolean resolved = false;
        Object initialState = p.getInitialState();
        Object currentState = null;
        Object nextState, existingClosed;    
        double currentCost, currentScore = 0, nextCost, nextScore;
        boolean visited, waitingList; //to check if the new state is already in one list with a smaller score
        openList.put(initialState, h.getValue(initialState));
        int nExpandidos = 0;
        
        while(!openList.isEmpty())
        {   
            Entry<Object, Double> min = null;
            for (Entry<Object, Double> entry : openList.entrySet()) { //we select the node with smallest score in the waiting list
                if (min == null || min.getValue() > entry.getValue()) {
                    min = entry;
                }
            }
            currentState = min.getKey();

            if (p.isResolved(currentState)) {
                resolved = true;
                break;
            }
            currentScore = min.getValue();
            currentCost = min.getValue() - h.getValue(currentState); 
            openList.remove(min.getKey());
            //System.out.println(nExpandidos+ " "+currentScore);

            nExpandidos++;
            List <Rule> rules = p.getRules(currentState);
            
            for (Rule rule : rules) {
            
                visited = false;
                waitingList = false;
                existingClosed = null;
                nextState = rule.applyToState(currentState);
                nextCost = currentCost + rule.getCost();
                nextScore =  nextCost + h.getValue(nextState);
                
                if (closedList.get(nextState) != null)
                    visited = closedList.get(nextState) <= nextScore;
                if (!visited) { // check that the node isn't already in the waiting list with lower score
                    if (openList.get(nextState) != null){
                        waitingList = openList.get(nextState) <= nextScore;
                        if (!waitingList) openList.remove(nextState);
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
        System.out.println("Profundidad : " + currentScore);
        
        System.out.println("Nodos frontera : " + nFrontera);
        
        System.out.println("Nodos expandidos : " + nExpandidos);
        
        System.out.println("Nodos generados en total : " + (nExpandidos + nFrontera));
        
        return resolved;
    }
        
    public static void iterativeDeepening (Problem p) 
    {
        Integer exploded = 0;
        Deque<Result> frontier = new LinkedList<>();
        Map<Result, Integer> nodeDepths = new HashMap<>();
        long startTime = System.nanoTime();
        frontier.add(new Result(p.getInitialState()));
        
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Result goal = depthFirstLim(p,12, frontier, nodeDepths);
            if (goal != null) {
                long estimatedTime = System.nanoTime() - startTime;
                System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
                return;
            };
        }
    }
    
    //recursive algorithm used by iterative deepening
    private static Result depthFirstLim (Problem p, int depth, Deque<Result> frontier, Map<Result, Integer> nodeDepths) 
    {   
        int exploded = 0;
        while(!frontier.isEmpty())
        {   
            Result parentState = frontier.pop();
            nodeDepths.put(parentState, parentState.depth);
            exploded++;
            
            List<Rule> rules = p.getRules(parentState.node);
            for (Rule rule : rules) {
                Result currentState = new Result(rule.applyToState(parentState.node), parentState.depth + 1);
                Integer oldDepth = nodeDepths.get(currentState);
                
                if (oldDepth == null || (depth >= 0 && oldDepth > currentState.depth)) {
                    if (p.isResolved((currentState.node))) {
                        System.out.println(currentState.node);
                        System.out.println("Nodos Frontera: " + (frontier.size() + 1));
                        System.out.println("Nodos Explotados: " + exploded);
                        System.out.println("Nodos Generados: " + (frontier.size() + exploded));
                        System.out.println("Profundidad: " + currentState.depth);
                        // TODO: Check generated (It only counts last tree)
                        return currentState;
                    }
                    if (depth < 0 || currentState.depth < depth) {
                        frontier.push(currentState);
                    }
                }
            }
        }
        return null;
    }
}