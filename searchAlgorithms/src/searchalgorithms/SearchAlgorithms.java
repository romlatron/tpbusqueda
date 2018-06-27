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
        int exploded = 0;
        Deque<Result> frontier = new LinkedList<>();
        Set<Object> visitedNodes = new HashSet<>();
        long startTime = System.nanoTime();
        frontier.add(new Result(p.getInitialState()));

        while(!frontier.isEmpty())
        {   
            Result parentState = frontier.pop();
            visitedNodes.add(parentState);
            exploded++;
            
            List<Rule> rules = p.getRules(parentState.node);
            for (Rule rule : rules) {
                Result currentState = new Result(rule.applyToState(parentState.node), parentState.depth + 1);
                
                if (!visitedNodes.contains(currentState)) {
                    if(currentState.node instanceof RollingCubes.State) {
                        visitedNodes.addAll(((State) currentState.node).applySymmetry(visitedNodes).stream().map(s -> new Result(s, currentState.depth)).collect(Collectors.toList()));
                    }
                    frontier.push(currentState);
                    if (p.isResolved((currentState.node))) {
                        long estimatedTime = System.nanoTime() - startTime;
                        System.out.println(currentState.node);
                        System.out.println("Nodos frontera: " + frontier.size());
                        System.out.println("Nodos Explotados: " + exploded);
                        System.out.println("Nodos Generados: " + (frontier.size() + exploded));
                        System.out.println("Profundidad: " + currentState.depth);
                        System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
                        // TODO: Check frontier and exploded
                        return;
                    }
                }
            }
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
                        visitedNodes.addAll(((State) currentState.node).applySymmetry(visitedNodes).stream().map(s -> new Result(s, currentState.depth)).collect(Collectors.toList()));
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
                        visitedNodes.addAll(((State) currentState.node).applySymmetry(visitedNodes).stream().map(s -> new Result(s, currentState.depth)).collect(Collectors.toList()));
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
        Map<Object, Double> openList = new LinkedHashMap<>(); //waiting list containing state and score
        HashMap<Object, Double> closedList = new LinkedHashMap<>(); //list of visited nodes
        boolean resolved = false;
        Object initialState = p.getInitialState();
        Object currentState = null;
        Object nextState, existingClosed;    
        double currentCost, currentScore, nextCost, nextScore;
        boolean visited, waitingList; //to check if the new state is already in one list with a smaller score
        openList.put(initialState, h.getValue(initialState));
        int nExpandidos = 0;
        int i = 0;
        
        while(!openList.isEmpty())
        {   
            i++;
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

            nExpandidos++;
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
                        existingClosed = entry.getKey(); //in case it has been visited with a higher score, we will have to replace it
                        visited = entry.getValue() <= nextScore;
                        break;
                    }
                }
                
                if (!visited) { // check that the node isn't already in the waiting list with lower score
                
                    Iterator<Map.Entry<Object, Double>> ite = openList.entrySet().iterator();
                    while (ite.hasNext()) {
                        Map.Entry<Object, Double> entry = ite.next();
                        if (entry.getKey().equals(nextState) 
                                || entry.getKey().equals(((State)(nextState)).applyHorizontalSymmetry((State)nextState))
                                ||entry.getKey().equals(((State)(nextState)).applyRotationalSymmetry((State)nextState))
                                ||entry.getKey().equals(((State)(nextState)).applyVerticalSymmetry((State)nextState))) {
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
            closedList.put(((State)(currentState)).applyHorizontalSymmetry((State)currentState), currentScore);
            closedList.put(((State)(currentState)).applyVerticalSymmetry((State)currentState), currentScore);
            closedList.put(((State)(currentState)).applyRotationalSymmetry((State)currentState), currentScore);
        }
                
        int nFrontera = 0;
        nFrontera = openList.entrySet().stream().map((_item) -> 1).reduce(nFrontera, Integer::sum);
        System.out.println("Nodos frontera : " + nFrontera);
        
        
//        int nExpandidos = 0;
//        nExpandidos = openList.entrySet().stream().map((_item) -> 1).reduce(nExpandidos, Integer::sum);

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