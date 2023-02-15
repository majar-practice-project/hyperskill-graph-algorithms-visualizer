package visualizer.view.graph.util;

import visualizer.view.dialogue.AddVertexDialogue;
import visualizer.view.graph.GraphRepository;
import visualizer.view.graph.Vertex;

import javax.swing.*;
import java.util.function.Consumer;

public class VertexManager {
    private final AddVertexDialogue dialogue;
    private final Consumer<Vertex> graphVertexAdder;
    private final Consumer<Vertex> graphVertexRemover;
    private GraphRepository graphRepository;

    public VertexManager(AddVertexDialogue dialogue, Consumer<Vertex> graphVertexAdder, Consumer<Vertex> graphVertexRemover) {
        this.dialogue = dialogue;
        this.graphVertexAdder = graphVertexAdder;
        this.graphVertexRemover = graphVertexRemover;
    }

    public void setGraphRepository(GraphRepository repository) {
        graphRepository = repository;
    }

    public void removeVertex(Vertex v) {
        graphVertexRemover.accept(v);
    }

    public void handleNewPosition(int x, int y) {
        dialogue.show(s -> {
            if (s == null) return false;
            s = s.trim();
            if (!isValidateVertexID(s)) return true;

            Vertex v = new Vertex(s, x, y);
            graphVertexAdder.accept(v);
            graphRepository.addVertex(v);
            return false;
        });
    }

    /**
     * The input vertex ID should contain only 1 non-whitespace character
     * @param s the input vertex ID
     * @return whether the input satisfy the requirement
     */
    private boolean isValidateVertexID(String s){
        return s.length() == 1;
    }
}
