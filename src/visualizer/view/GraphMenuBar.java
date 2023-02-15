package visualizer.view;

import javax.swing.*;

public class GraphMenuBar {
    private static final MenuItemFactory factory = new MenuItemFactory();
    private final JMenuBar menuBar;
    private final JMenuItem fileNewItem;
    private final JMenuItem fileExitItem;
    private final JMenuItem modeAddVertexItem;
    private final JMenuItem modeRemoveVertexItem;
    private final JMenuItem modeAddEdgeItem;
    private final JMenuItem modeRemoveEdgeItem;
    private final JMenuItem modeNoneItem;
    private final JMenuItem algorithmDepthFirstSearchItem;
    private final JMenuItem algorithmBreadthFirstSearchItem;
    private final JMenuItem algorithmDijkstrasAlgorithmItem;
    private final JMenuItem algorithmPrimItem;

    public GraphMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setName("Menu");
        JMenu fileMenu = factory.createJMenu("File", menuBar);
        JMenu modeMenu = factory.createJMenu("Mode", menuBar);
        JMenu algorithmMenu = factory.createJMenu("Algorithms", menuBar);

        fileNewItem = factory.createJMenuItem("New", fileMenu);
        fileExitItem = factory.createJMenuItem("Exit", fileMenu);

        modeAddVertexItem = factory.createJMenuItem("Add a Vertex", modeMenu);
        modeAddEdgeItem = factory.createJMenuItem("Add an Edge", modeMenu);
        modeRemoveVertexItem = factory.createJMenuItem("Remove a Vertex", modeMenu);
        modeRemoveEdgeItem = factory.createJMenuItem("Remove an Edge", modeMenu);
        modeNoneItem = factory.createJMenuItem("None", modeMenu);

        algorithmDepthFirstSearchItem = factory.createJMenuItem("Depth-First Search", algorithmMenu);
        algorithmBreadthFirstSearchItem = factory.createJMenuItem("Breadth-First Search", algorithmMenu);
        algorithmDijkstrasAlgorithmItem = factory.createJMenuItem("Dijkstra's Algorithm", algorithmMenu);
        algorithmPrimItem = factory.createJMenuItem("Prim's Algorithm", algorithmMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JMenuItem getFileNewItem() {
        return fileNewItem;
    }

    public JMenuItem getFileExitItem() {
        return fileExitItem;
    }

    public JMenuItem getModeAddVertexItem() {
        return modeAddVertexItem;
    }

    public JMenuItem getModeRemoveVertexItem() {
        return modeRemoveVertexItem;
    }

    public JMenuItem getModeAddEdgeItem() {
        return modeAddEdgeItem;
    }

    public JMenuItem getModeRemoveEdgeItem() {
        return modeRemoveEdgeItem;
    }

    public JMenuItem getModeNoneItem() {
        return modeNoneItem;
    }

    public JMenuItem getAlgorithmDepthFirstSearchItem() {
        return algorithmDepthFirstSearchItem;
    }

    public JMenuItem getAlgorithmBreadthFirstSearchItem() {
        return algorithmBreadthFirstSearchItem;
    }

    public JMenuItem getAlgorithmDijkstrasAlgorithmItem() {
        return algorithmDijkstrasAlgorithmItem;
    }
    public JMenuItem getAlgorithmPrimItem() {
        return algorithmPrimItem;
    }

    private static class MenuItemFactory {
        JMenuItem createJMenuItem(String text, JComponent parent) {
            JMenuItem newItem = new JMenuItem(text);
            newItem.setName(newItem.getText());
            parent.add(newItem);
            return newItem;
        }

        JMenu createJMenu(String text, JComponent parent) {
            JMenu newItem = new JMenu(text);
            newItem.setName(newItem.getText());
            parent.add(newItem);
            return newItem;
        }
    }
}