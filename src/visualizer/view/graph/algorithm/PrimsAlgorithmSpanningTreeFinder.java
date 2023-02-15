package visualizer.view.graph.algorithm;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PrimsAlgorithmSpanningTreeFinder<T> implements MinimumSpanningTreeFinder<T> {

    @Override
    public Map<T, T> perform(T start, Function<T, List<T>> neighborFunction, BiFunction<T, T, Integer> weightFunction) {
        Map<T, T> result = new HashMap<>();

        PriorityQueue<NeighborNode> queue = new PriorityQueue<>(Comparator.comparingInt((NeighborNode v) -> v.weight));
        queue.add(new NeighborNode(start, null, 0));
        while(!queue.isEmpty()) {
            NeighborNode nodeInfo = queue.poll();
            T node = nodeInfo.node;
            if(result.containsKey(node)) continue;

            result.put(node, nodeInfo.prev);
            for(T neighbor: neighborFunction.apply(node)) {
                if(!result.containsKey(neighbor)) {
                    queue.add(new NeighborNode(neighbor, node, weightFunction.apply(neighbor, node)));
                }
            }
        }

        return result;
    }

    public class NeighborNode {
        final T node;
        final T prev;
        final int weight;

        public NeighborNode(T node, T prev, int weight) {
            this.node = node;
            this.prev = prev;
            this.weight = weight;
        }
    }
}
