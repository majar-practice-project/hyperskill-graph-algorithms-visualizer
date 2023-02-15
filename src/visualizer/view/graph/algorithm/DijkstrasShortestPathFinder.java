package visualizer.view.graph.algorithm;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DijkstrasShortestPathFinder<T> implements ShortestPathFinder<T> {
    @Override
    public Map<T, Integer> perform(T start, Function<T, List<T>> neighborFunction, BiFunction<T, T, Integer> weightFunction) {
        Queue<Map.Entry<T, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        Map<T, Integer> result = new HashMap<>();

        queue.add(Map.entry(start, 0));

        while(!queue.isEmpty()) {
            Map.Entry<T, Integer> candidate = queue.poll();
            T node = candidate.getKey();
            int dist = candidate.getValue();

            if(result.containsKey(node)) continue;
            result.put(node, dist);

            for(T neighbor: neighborFunction.apply(node)) {
                queue.add(Map.entry(neighbor, dist+weightFunction.apply(node, neighbor)));
            }
        }
        return result;
    }

    @Override
    public String getName() {
        return "Dijkstra's Algorithm";
    }
}
