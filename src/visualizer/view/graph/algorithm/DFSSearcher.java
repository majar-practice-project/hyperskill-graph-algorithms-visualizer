package visualizer.view.graph.algorithm;

import java.util.*;
import java.util.function.Function;

public class DFSSearcher<T> implements GraphTraverser<T>{
    @Override
    public Deque<T> perform(T start, Function<T, List<T>> neighborFunction) {
        Deque<T> stack = new ArrayDeque<>();
        Set<T> visited = new HashSet<>();
        Deque<T> result = new ArrayDeque<>();

        stack.push(start);

        while(!stack.isEmpty()) {
            T node = stack.pop();
            if(visited.contains(node)) continue;
            visited.add(node);
            result.add(node);

            List<T> neighbors = neighborFunction.apply(node);

            for(int i= neighbors.size()-1; i>=0; i--) {
                stack.push(neighbors.get(i));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "DFS";
    }
}