package visualizer.view.graph.algorithm;

import java.util.Deque;
import java.util.List;
import java.util.function.Function;

public interface GraphTraverser<T> {
    Deque<T> perform(T start, Function<T, List<T>> neighborFunction);

    String getName();
}
