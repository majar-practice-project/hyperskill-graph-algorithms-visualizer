package visualizer.view.graph.util;

import visualizer.view.dialogue.AddEdgeDialogue;
import visualizer.view.graph.GraphRepository;
import visualizer.view.graph.Vertex;
import visualizer.view.graph.WeightedEdge;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

public class EdgeManager {
    private final AddEdgeDialogue dialogue;
    private final Consumer<JComponent> graphComponentAdder;
    private final Consumer<JComponent> graphComponentRemover;
    private final Set<Vertex> selectedVertices = new LinkedHashSet<>();
    private GraphRepository graphRepository;

    public EdgeManager(AddEdgeDialogue dialogue, Consumer<JComponent> graphComponentAdder, Consumer<JComponent> graphComponentRemover) {
        this.dialogue = dialogue;
        this.graphComponentAdder = graphComponentAdder;
        this.graphComponentRemover = graphComponentRemover;
    }

    public void setGraphRepository(GraphRepository repository) {
        graphRepository = repository;
    }

    public void handleNewSelectedVertex(Vertex newVertex) {
        selectedVertices.add(newVertex);

        if (selectedVertices.size() < 2) return;
        Vertex[] vertexPair = selectedVertices.toArray(Vertex[]::new);
        selectedVertices.clear();

        dialogue.show(s -> {
            if (s == null) return false;
            s = s.trim();
            if (!validateWeightInput(s)) return true;

            int weight = Integer.parseInt(s);
            Vertex v1 = vertexPair[0];
            Vertex v2 = vertexPair[1];

            WeightedEdge edge = new WeightedEdge(v1, v2, weight);
            graphRepository.addEdge(v1, v2, edge);
            edge.addToGraph(graphComponentAdder);
            return false;

        });

    }

    public void handleDeleteEdges(Point p) {
        for (WeightedEdge edge : graphRepository.getEdges()) {
            if (edge.isInLine(p)) {
                edge.removeFromGraph(graphComponentRemover);
                graphRepository.removeEdge(edge);
            }
        }
    }

    public void removeEdge(WeightedEdge edge) {
        edge.removeFromGraph(graphComponentRemover);
    }

    public void clearCache() {
        selectedVertices.clear();
    }

    private boolean validateWeightInput(String s) {
        return (s.matches("-?[1-9]\\d*|0"));
    }
}
