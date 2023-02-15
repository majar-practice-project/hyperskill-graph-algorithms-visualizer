package visualizer.view.graph.algorithm;

import java.util.*;
import java.util.function.Function;

public class BFSSearcher<T> implements GraphTraverser<T> {
    @Override
    public Deque<T> perform(T start, Function<T, List<T>> neighborFunction) {
        Deque<T> queue = new ArrayDeque<>();
        Set<T> visited = new HashSet<>();
        Deque<T> result = new ArrayDeque<>();

        queue.add(start);

        while(!queue.isEmpty()) {
            T node = queue.pop();
            if(visited.contains(node)) continue;
            visited.add(node);
            result.add(node);

            queue.addAll(neighborFunction.apply(node));
        }
        return result;
    }

    @Override
    public String getName() {
        return "BFS";
    }
}
