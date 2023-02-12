package visualizer.view.graph.util;

import visualizer.view.dialogue.AddEdgeDialogue;
import visualizer.view.graph.Vertex;
import visualizer.view.graph.WeightedEdge;

import java.util.LinkedHashSet;
import java.util.Set;

public class EdgeAdder {
    private final AddEdgeDialogue dialogue;
    private final Set<Vertex> selectedVertices = new LinkedHashSet<>();

    public EdgeAdder(AddEdgeDialogue dialogue) {
        this.dialogue = dialogue;
    }

    public void handleNewSelectedVertex(Vertex newVertex) {
        selectedVertices.add(newVertex);

        if (selectedVertices.size() < 2) return;
        Vertex[] vertexPair = selectedVertices.toArray(Vertex[]::new);

        dialogue.show(s -> {
            if (s == null) return false;
            s = s.trim();
            if (!validateWeightInput(s)) return true;

            int weight = Integer.parseInt(s);
            Vertex v1 = vertexPair[0];
            Vertex v2 = vertexPair[1];

            selectedVertices.clear();

            WeightedEdge edge = new WeightedEdge(v1, v2, weight);
            edge.addToGraph(component -> {
                component.setVisible(true);
//                graph.add(component);
//                refreshGraph();
            });
            return false;

        });

    }

    private boolean validateWeightInput(String s) {
        return (s.matches("-?[1-9]\\d*|0"));
    }
}
