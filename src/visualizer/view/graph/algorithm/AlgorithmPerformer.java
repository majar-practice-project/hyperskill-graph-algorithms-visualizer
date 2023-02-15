package visualizer.view.graph.algorithm;

import visualizer.view.graph.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AlgorithmPerformer {
    private final JLabel bottomMessage = new JLabel("");

    private Consumer<Vertex> vertexConsumer;
    private GraphTraverser<Vertex> traversalAlgorithm;
    private ShortestPathFinder<Vertex> shortestPathAlgorithm;
    private MinimumSpanningTreeFinder<Vertex> minimumSpanningTreeAlgorithm;
    private Function<Vertex, List<Vertex>> neighborFunction;
    private BiFunction<Vertex, Vertex, Integer> weightFunction;

    {
        bottomMessage.setName("Display");
        bottomMessage.setForeground(Color.GREEN);
    }
    public void init(Function<Vertex, List<Vertex>> neighborFunction, GraphTraverser<Vertex> algorithm) {
        bottomMessage.setText("Please choose a starting vertex");
        this.neighborFunction = neighborFunction;
        this.traversalAlgorithm = algorithm;
        vertexConsumer = this::acceptTraversalSourceVertex;
    }

    public void init(Function<Vertex, List<Vertex>> neighborFunction, BiFunction<Vertex, Vertex, Integer> weightFunction, ShortestPathFinder<Vertex> algorithm) {
        bottomMessage.setText("Please choose a starting vertex");
        this.neighborFunction = neighborFunction;
        this.weightFunction = weightFunction;
        this.shortestPathAlgorithm = algorithm;
        vertexConsumer = this::acceptShortestPathSourceVertex;
    }

    public void init(Function<Vertex, List<Vertex>> neighborFunction, BiFunction<Vertex, Vertex, Integer> weightFunction, MinimumSpanningTreeFinder<Vertex> algorithm) {
        bottomMessage.setText("Please choose a starting vertex");
        this.neighborFunction = neighborFunction;
        this.weightFunction = weightFunction;
        this.minimumSpanningTreeAlgorithm = algorithm;
        vertexConsumer = this::acceptMSTSourceVertex;
    }

    public void acceptVertex(Vertex v) {
        vertexConsumer.accept(v);
    }

    public void acceptTraversalSourceVertex(Vertex v) {
        if(traversalAlgorithm==null) return;

        bottomMessage.setText("Please wait...");
        Timer delay = new Timer(1000, e -> {
            Deque<Vertex> order = traversalAlgorithm.perform(v, neighborFunction);
            String text = String.format("%s : %s", traversalAlgorithm.getName(),
                    order.stream()
                            .map(Vertex::getLabel)
                            .collect(Collectors.joining(" -> ")));

            bottomMessage.setText(text);
        });
        delay.setRepeats(false);
        delay.start();
    }

    public void acceptShortestPathSourceVertex(Vertex v) {
        if(shortestPathAlgorithm==null) return;
        bottomMessage.setText("Please wait...");

        Timer delay = new Timer(1000, e -> {
            Map<Vertex, Integer> distances = shortestPathAlgorithm.perform(v, neighborFunction, weightFunction);
            distances.remove(v);
            String text = distances.entrySet().stream()
                    .map(val -> String.format("%s=%d", val.getKey().getLabel(), val.getValue()))
                    .sorted()
                    .collect(Collectors.joining(", "));

            bottomMessage.setText(text);
        });
        delay.setRepeats(false);
        delay.start();
    }

    public void acceptMSTSourceVertex(Vertex v) {
        if(minimumSpanningTreeAlgorithm==null) return;
        bottomMessage.setText("Please wait...");

        Timer delay = new Timer(1000, e -> {
            Map<Vertex, Vertex> distances = minimumSpanningTreeAlgorithm.perform(v, neighborFunction, weightFunction);
            distances.remove(v);
            String text = distances.entrySet().stream()
                    .map(val -> String.format("%s=%s", val.getKey().getLabel(), val.getValue().getLabel()))
                    .sorted()
                    .collect(Collectors.joining(", "));

            bottomMessage.setText(text);
        });
        delay.setRepeats(false);
        delay.start();
    }

    public JLabel getBottomMessageLabel(){
        return bottomMessage;
    }

}