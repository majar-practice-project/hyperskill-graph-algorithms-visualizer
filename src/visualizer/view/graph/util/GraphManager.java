package visualizer.view.graph.util;

import visualizer.view.graph.GraphRepository;
import visualizer.view.graph.Vertex;
import visualizer.view.graph.WeightedEdge;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class GraphManager {
    private final VertexManager vertexManager;
    private final EdgeManager edgeManager;
    private final GraphRepository graphRepository = new GraphRepository();

    public GraphManager(VertexManager vertexManager, EdgeManager edgeManager) {
        this.vertexManager = vertexManager;
        this.edgeManager = edgeManager;

        vertexManager.setGraphRepository(graphRepository);
        edgeManager.setGraphRepository(graphRepository);
    }

    /**
     * determine whether edge exists around a specific point
     *
     * @param p the point specified in the window
     * @return true if edge exists or false otherwise
     */
    public static boolean edgeExists(Point p, Set<WeightedEdge> edges) {
        return edges.stream()
                .anyMatch(edge -> edge.isInLine(p));
    }

    public void handleNewPosition(Point p) {
        // test case expects that we cannot add a vertex that overlaps the edge, so just return if an edge is fond near the point
        if (edgeExists(p, graphRepository.getEdges())) return;
        vertexManager.handleNewPosition(p.x, p.y);
    }

    public void handleNewSelectedVertex(Vertex newVertex) {
        edgeManager.handleNewSelectedVertex(newVertex);
    }

    public void handleDeleteVertex(Vertex v) {
        WeightedEdge[] edges = graphRepository.removeVertex(v);
        Arrays.stream(edges).forEach(edgeManager::removeEdge);
        vertexManager.removeVertex(v);
    }

    public void reset(){
        graphRepository.reset();
    }

    public void handleDeleteEdges(Point p) {
        edgeManager.handleDeleteEdges(p);
    }

    public void clearVertexSelectionCache() {
        edgeManager.clearCache();
    }

    public List<Vertex> getNeighbors(Vertex v) {
        return graphRepository.getNeighbors(v);
    }

    public int getWeight(Vertex v1, Vertex v2) {
        return graphRepository.getWeight(v1, v2);
    }
}
