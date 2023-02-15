package visualizer;

import visualizer.view.GraphMenuBar;
import visualizer.view.dialogue.AddEdgeDialogue;
import visualizer.view.dialogue.AddVertexDialogue;
import visualizer.view.graph.Vertex;
import visualizer.view.graph.algorithm.*;
import visualizer.view.graph.util.EdgeManager;
import visualizer.view.graph.util.GraphManager;
import visualizer.view.graph.util.VertexManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private final JPanel graph;
    private final JLabel modeLabel;
    private GraphMode graphMode = GraphMode.ADD_VERTEX;

    private final GraphManager graphManager = new GraphManager(
            new VertexManager(new AddVertexDialogue(), c -> addComponentToGraph(processVertex(c)), this::removeComponentFromGraph),
            new EdgeManager(new AddEdgeDialogue(), this::addComponentToGraph, this::removeComponentFromGraph));

    private final AlgorithmPerformer algorithmPerformer = new AlgorithmPerformer();

    public MainFrame() {
        super("Graph-Algorithms Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        initMenuBar();

        graph = createGraph();
        modeLabel = createModelLabel();
        arrangeComponents();

        setVisible(true);
    }

    private void arrangeComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHEAST;
        add(modeLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.SOUTH;
        add(algorithmPerformer.getBottomMessageLabel(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        add(graph, c);
    }

    public Vertex processVertex(Vertex v) {
        v.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (graphMode) {
                    case ADD_EDGE -> graphManager.handleNewSelectedVertex(v);
                    case REMOVE_VERTEX -> graphManager.handleDeleteVertex(v);
                    case NONE -> algorithmPerformer.acceptVertex(v);
                    default -> {
                    }
                }
            }
        });
        return v;
    }

    private JPanel createGraph() {
        JPanel graph = new JPanel();
        graph.setName("Graph");

        graph.setSize(getSize());
        graph.setBackground(Color.BLACK);
        graph.setLayout(null);

        graph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch(graphMode) {
                    case ADD_VERTEX -> graphManager.handleNewPosition(e.getPoint());
                    case REMOVE_EDGE -> graphManager.handleDeleteEdges(e.getPoint());
                    default -> {}
                }
            }
        });

        return graph;
    }

    private JLabel createModelLabel() {
        JLabel label = new JLabel(graphMode.getText(), SwingConstants.RIGHT);
        label.setName("Mode");
        label.setForeground(Color.GREEN);
        return label;
    }

    private void initMenuBar() {
        GraphMenuBar graphMenuBar = new GraphMenuBar();
        setJMenuBar(graphMenuBar.getMenuBar());

        graphMenuBar.getFileNewItem().addActionListener(e -> {
            graph.removeAll();
            graphManager.reset();
            refreshGraph();
            setGraphMode(GraphMode.ADD_VERTEX);
        });
        graphMenuBar.getFileExitItem().addActionListener(e -> System.exit(0));

        graphMenuBar.getModeNoneItem().addActionListener(e -> setGraphMode(GraphMode.NONE));
        graphMenuBar.getModeAddVertexItem().addActionListener(e -> setGraphMode(GraphMode.ADD_VERTEX));
        graphMenuBar.getModeRemoveVertexItem().addActionListener(e -> setGraphMode(GraphMode.REMOVE_VERTEX));
        graphMenuBar.getModeRemoveEdgeItem().addActionListener(e -> setGraphMode(GraphMode.REMOVE_EDGE));
        graphMenuBar.getModeAddEdgeItem().addActionListener(e -> {
            setGraphMode(GraphMode.ADD_EDGE);
            graphManager.clearVertexSelectionCache();    //todo
        });

        graphMenuBar.getAlgorithmBreadthFirstSearchItem().addActionListener(e -> {
            setGraphMode(GraphMode.NONE);
            algorithmPerformer.init(graphManager::getNeighbors, new BFSSearcher<>());
        });
        graphMenuBar.getAlgorithmDepthFirstSearchItem().addActionListener(e -> {
            setGraphMode(GraphMode.NONE);
            algorithmPerformer.init(graphManager::getNeighbors, new DFSSearcher<>());
        });
        graphMenuBar.getAlgorithmDijkstrasAlgorithmItem().addActionListener(e -> {
            setGraphMode(GraphMode.NONE);
            algorithmPerformer.init(graphManager::getNeighbors,
                    graphManager::getWeight, new DijkstrasShortestPathFinder<>());
        });
        graphMenuBar.getAlgorithmPrimItem().addActionListener(e -> {
            setGraphMode(GraphMode.NONE);
            algorithmPerformer.init(graphManager::getNeighbors,
                    graphManager::getWeight, new PrimsAlgorithmSpanningTreeFinder<>());
        });
    }

    private void setGraphMode(GraphMode mode) {
        assert mode != null;
        graphMode = mode;
        modeLabel.setText(graphMode.getText());
    }

    private void refreshGraph() {
        graph.revalidate();
        repaint();
    }

    private void addComponentToGraph(JComponent component) {
        component.setVisible(true);
        graph.add(component);
        refreshGraph();
    }

    private void removeComponentFromGraph(JComponent component) {
        graph.remove(component);
        refreshGraph();
    }
}