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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    public static Result depthFirst(Problem p)
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
                    newResult.parent = currentResult;
                    
                    stack.push(newResult);
                }
            }
        }
        if (p.isResolved(stack.peek().node)) {
            System.out.println(stack.peek());
            System.out.println("Profundidad: " + stack.peek().depth);
            
            System.out.println("Nodos expandidos : " + i);
            long estimatedTime = System.nanoTime() - startTime;

            int nFrontera = 0;
            nFrontera = stack.stream().map((_item) -> 1).reduce(nFrontera, Integer::sum);
            System.out.println("Nodos frontera : " + nFrontera);
            System.out.println("Nodos generados : " + (i + nFrontera));
            System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
            return stack.peek();
        }
        return null;
    }
    
    public static Result breadthFirst(Problem p)
    {
        Queue<Result> queue = new LinkedList<>();
        Set<Result> visitedNodes = new HashSet<>();
        queue.add(new Result(p.getInitialState()));
        visitedNodes.add(new Result(p.getInitialState()));
        
        Result currentResult = null;
        Object tmpObj = null;
        Object newState = null;
        Result newResult = null;
                
//        long startTime = System.nanoTime();
        long duration = 3600000;
        long maxTime = System.currentTimeMillis() + duration;

        while(!queue.isEmpty() && !p.isResolved(queue.element().node) && System.currentTimeMillis() < maxTime)
        {   
            currentResult = queue.poll();
            tmpObj = currentResult.node;
            
            List<Rule>rules = p.getRules(tmpObj);
            for (Rule rule : rules) 
            {                
                newState = rule.applyToState(tmpObj);
                newResult = new Result(newState, currentResult.depth+1);
                if (!visitedNodes.contains(newResult))
                {
                    queue.add(newResult);
                    visitedNodes.add(newResult);
                }
            }
        }
        
        if (p.isResolved(queue.element().node) || System.currentTimeMillis() >= maxTime)
        {
            System.out.println(queue.element());
            
//            long estimatedTime = System.nanoTime() - startTime;
//            System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
            System.out.println("Elapsed time: " + maxTime);
            System.out.println("Profundidad de la solucion : " + queue.element().depth);
            
            System.out.println("Nodos generados : " + visitedNodes.size());
            System.out.println("Nodos frontera : " + queue.size());
            System.out.println("Nodos expandidos : " + (visitedNodes.size() - queue.size()));
            
            return queue.element();
        }
        return null;
    }
    
    public static Result Astar(Problem p, Heuristic h)
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
                        return currentState;
                    }
                }
            }
        }
        return null;
    }
    
    public static Result greedySearch(Problem p, Heuristic h)
    {
        Result currentState  = new Result(p.getInitialState());
        Result nextState;
        Rule applyRule;
        double minScore;
        HashSet <Result> visitedNodes = new HashSet<>();        
        visitedNodes.add(currentState);
        int i =0;
        long startTime = System.nanoTime();

        while(!p.isResolved(currentState.node))  
        {     
            List<Rule> rules = p.getRules(currentState.node);
            applyRule = null;
            minScore = Double.POSITIVE_INFINITY ;

            for (Rule<Object> rule : rules)
            {                
                if (!visitedNodes.contains(new Result(rule.applyToState(currentState.node)))) {   
                    i++;
                    if (rule.getCost() + h.getValue(rule.applyToState(currentState.node)) < minScore){
                        minScore = rule.getCost()+ h.getValue(rule.applyToState(currentState.node));
                        applyRule = rule;
                    }
                }
            }
            if (applyRule != null) {
                nextState = new Result(applyRule.applyToState(currentState.node), currentState.depth+1);
                nextState.parent = new Result(currentState);
                visitedNodes.add(nextState);
                currentState = nextState;                
            }
            else {
                currentState = currentState.parent;
            }
        }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
        
        System.out.println("Profundidad de la solucion : " + currentState.depth);
        
        int nExpandidos = 0;
        nExpandidos = visitedNodes.stream().map((_item) -> 1).reduce(nExpandidos, Integer::sum);
        System.out.println("Nodos expandidos : " + nExpandidos);
        
        System.out.println("Nodos frontera : " + (i - nExpandidos));
        
        return currentState;
    }   
    
    
    private static int iterativeDeepeningStep (int i) {
        return (int) Math.floor(Math.exp(i/2.0) + 1);
    }
        
    public static Result iterativeDeepening (Problem p) 
    {
        long startTime = System.nanoTime();
        
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            Result goal = depthFirstLim(p,iterativeDeepeningStep(i));
            if (goal != null) {
                long estimatedTime = System.nanoTime() - startTime;
                System.out.println("Elapsed time: " + TimeUnit.NANOSECONDS.toMillis(estimatedTime));
                return goal;
            };
        }
        return null;
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
                    currentState.parent = parentState;

                    if (p.isResolved((currentState.node))) {
                        System.out.println(currentState.node);
                        System.out.println("Nodos Frontera: " + (frontier.size() + 1));
                        System.out.println("Nodos Explotados: " + exploded);
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