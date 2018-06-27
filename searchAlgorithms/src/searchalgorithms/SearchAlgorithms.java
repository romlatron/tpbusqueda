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
    
    public static void Astar(Problem p, Heuristic h)
    {
        int exploded = 0;
        PriorityQueue<Result> frontier = new PriorityQueue<>(Comparator.comparingDouble(n -> n.heuristicCost + n.cost));
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
        
    public static void iterativeDeepening (Problem p) 
    {
        Integer exploded = 0;
        Deque<Result> frontier = new LinkedList<>();
        long startTime = System.nanoTime();
        frontier.add(new Result(p.getInitialState()));
        
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            Result goal = depthFirstLim(p,i, frontier);
            if (goal != null) {
                long estimatedTime = System.nanoTime() - startTime;
                System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
                return;
            };
        }
    }
    
    //recursive algorithm used by iterative deepening
    private static Result depthFirstLim (Problem p, int depth, Deque<Result> frontier) 
    {   
        Map<Result, Integer> nodeDepths = new HashMap<>();

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