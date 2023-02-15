package visualizer.view.graph;

import java.util.*;

public class GraphRepository {
    Map<Vertex, Map<Vertex, WeightedEdge>> neighborsMatrix = new HashMap<>();
    Set<WeightedEdge> edges = new HashSet<>();

    public void reset() {
        neighborsMatrix.clear();
        edges.clear();
    }
    public void addVertex(Vertex v) {
        neighborsMatrix.put(v, new HashMap<>());
    }

    public void addEdge(Vertex v1, Vertex v2, WeightedEdge edge) {
        neighborsMatrix.get(v1).put(v2, edge);
        neighborsMatrix.get(v2).put(v1, edge);
        edges.add(edge);
    }

    public WeightedEdge[] removeVertex(Vertex v) {
        Map<Vertex, WeightedEdge> neighbors = neighborsMatrix.get(v);
        WeightedEdge[] removedEdges = neighbors.values().stream()
                .filter(edges::contains).toList()
                .toArray(WeightedEdge[]::new);

        for (Vertex neighbor : neighbors.keySet()) {
            neighborsMatrix.get(neighbor).remove(v);
            removeEdge(neighbors.get(neighbor));
        }

        neighborsMatrix.remove(v);
        return removedEdges;
    }

    public void removeEdge(WeightedEdge e) {
        edges.remove(e);
    }

    public Set<WeightedEdge> getEdges() {
        return new HashSet<>(edges);
    }

    public List<Vertex> getNeighbors(Vertex v) {
        Map<Vertex, WeightedEdge> neighbors = neighborsMatrix.get(v);
        return neighbors.keySet().stream()
                .filter(key -> edges.contains(neighbors.get(key)))
                .sorted(Comparator.comparingInt(key -> neighbors.get(key).getWeight()))
                .toList();
    }

    public int getWeight(Vertex v1, Vertex v2) {
        return neighborsMatrix.get(v1).get(v2).getWeight();
    }
}
