package visualizer;

import visualizer.view.dialogue.AddEdgeDialogue;
import visualizer.view.dialogue.AddVertexDialogue;
import visualizer.view.graph.Vertex;
import visualizer.view.graph.WeightedEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MainFrame extends JFrame {
    private final JPanel graph = new JPanel();
    private final AddVertexDialogue vertexDialogue = new AddVertexDialogue();
    private final AddEdgeDialogue edgeDialogue = new AddEdgeDialogue();
    private final JLabel modeLabel;
    private final HashSet<Vertex> selectedVertices = new LinkedHashSet<>();   //todo
    private GraphMode graphMode = GraphMode.ADD_VERTEX;

    public MainFrame() {
        super("Graph-Algorithms Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        initMenuBar();

        setLayout(new GridBagLayout());

        graph.setName("Graph");

        graph.setSize(getSize());
        graph.setBackground(Color.BLACK);
        graph.setLayout(null);

        graph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (graphMode != GraphMode.ADD_VERTEX) return;
                vertexDialogue.show(s -> {
                    if (s == null) return false;
                    s = s.trim();
                    if (s.length() != 1) return true;

                    graph.add(createVertex(s.substring(0, 1), e.getX(), e.getY()));
                    refreshGraph();
                    return false;
                });
            }
        });

        modeLabel = new JLabel(graphMode.getText(), SwingConstants.RIGHT);
        modeLabel.setName("Mode");
        modeLabel.setForeground(Color.GREEN);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHEAST;

        add(modeLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        add(graph, c);

        setVisible(true);
    }

    public Vertex createVertex(String label, int x, int y) {
        Vertex v = new Vertex(label, x, y);
        v.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (graphMode != GraphMode.ADD_EDGE) return;
                selectedVertices.add(v);
                if (selectedVertices.size() != 2) return;

                edgeDialogue.show(s -> {
                    if (s == null) return false;

                    s = s.trim();
                    if (!s.matches("-?[1-9]\\d*|0")) return true;

                    int weight = Integer.parseInt(s);
                    Iterator<Vertex> iterator = selectedVertices.iterator();
                    Vertex v1 = iterator.next();
                    Vertex v2 = iterator.next();

                    selectedVertices.clear();

                    WeightedEdge edge = new WeightedEdge(v1, v2, weight);
                    edge.addToGraph(component -> {
                        component.setVisible(true);
                        graph.add(component);
                        refreshGraph();
                    });
                    return false;
                });
            }
        });
        return v;
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = new JMenu("Mode");
        menuBar.add(modeMenu);

        JMenuItem addVertexItem = new JMenuItem("Add a Vertex");
        JMenuItem addEdgeItem = new JMenuItem("Add an Edge");
        JMenuItem noneItem = new JMenuItem("None");

        modeMenu.add(addVertexItem);
        modeMenu.add(addEdgeItem);
        modeMenu.add(noneItem);

        noneItem.addActionListener(e -> {
            graphMode = GraphMode.NONE;
            updateModeLabel();
        });
        addVertexItem.addActionListener(e -> {
            graphMode = GraphMode.ADD_VERTEX;
            updateModeLabel();
        });
        addEdgeItem.addActionListener(e -> {
            graphMode = GraphMode.ADD_EDGE;
            updateModeLabel();
            selectedVertices.clear();    //todo
        });

        setJMenuBar(menuBar);

        menuBar.setName("MenuBar");
        modeMenu.setName("Mode");
        addVertexItem.setName("Add a Vertex");
        addEdgeItem.setName("Add an Edge");
        noneItem.setName("None");
    }

    private void updateModeLabel() {
        modeLabel.setText(graphMode.getText());
    }

    private void refreshGraph() {
        graph.revalidate();
        graph.repaint();
        modeLabel.repaint();
    }
}