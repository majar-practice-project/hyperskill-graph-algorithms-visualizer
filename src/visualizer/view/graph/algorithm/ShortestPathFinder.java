package visualizer.view.graph.algorithm;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ShortestPathFinder<T> {
    Map<T, Integer> perform(T start, Function<T, List<T>> neighborFunction, BiFunction<T, T, Integer> weightFunction);

    String getName();
}
