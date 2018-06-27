/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

import RollingCubes.State;
import ar.com.itba.sia.*;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;



/**
 *
 * @author Kevin
 */
public class SearchAlgorithms
{
    
    private static class Result {
        public int depth = 0;
        public double heuristicCost = -1;
        public double cost = 0;
        public Result parent = null;
        public Object node;
        
        public Result (Result result) {
            this.depth = result.depth;
            this.node = result.node;
        }
        
        public Result (Object node) {
            this.depth = depth;
            this.node = node;
        }
        
        public Result (Object node, double heuristicCost) {
            this.node = node;
            this.heuristicCost = heuristicCost;
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
        int i=0;
        long startTime = System.nanoTime();
        Stack<Result> stack = new Stack<>();
        Set<Result> visitedNodes = new HashSet<>();
        stack.add(new Result(p.getInitialState()));
        visitedNodes.add(new Result(p.getInitialState()));
        
        while(!stack.isEmpty() && !p.isResolved(stack.peek().node))
        {
            i++;
            Result currentResult = stack.pop();
            Object tmpObj = currentResult.node;
            List<Rule>rules = p.getRules(tmpObj);
            for (Rule rule : rules)
            {
                Object newState = rule.applyToState(tmpObj);
                Result newResult = new Result(newState, currentResult.depth+1);
                if (!visitedNodes.contains(newResult))
                {
                    visitedNodes.add(newResult);
                    
                    stack.push(newResult);
                }
            }
        }
        if (p.isResolved(stack.peek().node)) {
            System.out.println(stack.peek());
            System.out.println("Profundidad de la solucion : " + stack.peek().depth);
            
            System.out.println("Nodos expandidos : " + i);
            long estimatedTime = System.nanoTime() - startTime;

            int nFrontera = 0;
            nFrontera = stack.stream().map((_item) -> 1).reduce(nFrontera, Integer::sum);
            System.out.println("Nodos frontera : " + nFrontera);
            System.out.println("Nodos generados : " + (i + nFrontera));
            System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
        }
    }
    
    public static void breadthFirst(Problem p)
    {
        int exploded = 0;
        Queue<Result> frontier = new LinkedList<>();
        Set<Object> visitedNodes = new HashSet<>();
        long startTime = System.nanoTime();
        frontier.add(new Result(p.getInitialState()));

        while(!frontier.isEmpty())
        {   
            Result parentState = frontier.poll();
            visitedNodes.add(parentState);
            exploded++;
            
            List<Rule> rules = p.getRules(parentState.node);
            for (Rule rule : rules) {
                Result currentState = new Result(rule.applyToState(parentState.node), parentState.depth + 1);
                
                if (!visitedNodes.contains(currentState)) {
                    if(currentState.node instanceof RollingCubes.State) {
                        visitedNodes.addAll(((State) currentState.node).applySymmetry().stream().map(s -> new Result(s, currentState.depth)).collect(Collectors.toList()));
                    }
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
        int exploded = 0;
        PriorityQueue<Result> frontier = new PriorityQueue<>(Comparator.comparingDouble(n -> n.heuristicCost));
        Set<Object> visitedNodes = new HashSet<>();
        long startTime = System.nanoTime();
        frontier.add(new Result(p.getInitialState(), h.getValue(p.getInitialState())));

        while(!frontier.isEmpty())
        {   
            Result parentState = frontier.remove();
            visitedNodes.add(parentState);
            exploded++;
            
            List<Rule> rules = p.getRules(parentState.node);
            for (Rule rule : rules) {
                Result currentState = new Result(rule.applyToState(parentState.node), parentState.depth + 1);
                currentState.heuristicCost = h.getValue(currentState.node);
                currentState.cost = parentState.cost + rule.getCost();
                currentState.parent = parentState;
                
                if (!visitedNodes.contains(currentState)) {
                    if(currentState.node instanceof RollingCubes.State) {
                        visitedNodes.addAll(((State) currentState.node).applySymmetry().stream().map(s -> new Result(s, currentState.depth)).collect(Collectors.toList()));
                    }
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
    
    public static boolean Astar(Problem p, Heuristic h)
    {
        HashMap<Object, Double> openList = new HashMap<>(); //waiting list containing state and score
        HashMap<Object, Double> closedList = new HashMap<>(); //list of visited nodes
        boolean resolved = false;
        Object initialState = p.getInitialState();
        Object currentState = null;
        Object nextState, existingClosed;    
        double currentCost = 0, currentScore = 0, nextCost, nextScore;
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
        System.out.println(currentState);
        System.out.println("Profundidad : " + currentCost);
        System.out.println("Nodos frontera : " + nFrontera);
        System.out.println("Nodos expandidos : " + nExpandidos);
        System.out.println("Nodos generados en total : " + (nExpandidos + nFrontera));
        return resolved;
    }
        
    public static void iterativeDeepening (Problem p) 
    {
        
        long startTime = System.nanoTime();
        
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            Result goal = depthFirstLim(p,i);
            if (goal != null) {
                long estimatedTime = System.nanoTime() - startTime;
                System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
                return;
            };
        }
    }
    
    //recursive algorithm used by iterative deepening
    private static Result depthFirstLim (Problem p, int depth) 
    {   
        Map<Result, Integer> nodeDepths = new HashMap<>();
        Deque<Result> frontier = new LinkedList<>();
        frontier.add(new Result(p.getInitialState()));
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