package visualizer.view.graph;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class WeightedEdge {
    private static final String EDGE_NAME = "Edge <%s -> %s>";
    private static final String WEIGHT_LABEL_NAME = "EdgeLabel <%s -> %s>";
    private final JEdge edge1;
    private final JEdge edge2;
    private final JLabel weightLabel;
    public WeightedEdge(Vertex v1, Vertex v2, int weight){
        edge1 = new JEdge(v1, v2);
        edge2 = new JEdge(v2, v1);
        weightLabel = createWeightLabel(v1, v2, weight);
    }

    private static JLabel createWeightLabel(Vertex src, Vertex dest, int weight){
        JLabel label = new JLabel(String.valueOf(weight));

        label.setLocation((src.getCenterX()+dest.getCenterX())/2, (src.getCenterY()+dest.getCenterY())/2);
        label.setSize(label.getPreferredSize());
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);

        label.setName(String.format(WEIGHT_LABEL_NAME, src.getLabel(), dest.getLabel()));
        return label;
    }

    public void addToGraph(Consumer<JComponent> addAction) {
        addAction.accept(weightLabel);
        addAction.accept(edge1);
        addAction.accept(edge2);
    }

    static class JEdge extends JComponent {
        private final boolean mainDiagonal;

        public JEdge(Vertex src, Vertex dest) {
            int srcX = src.getCenterX();
            int srcY = src.getCenterY();
            int destX = dest.getCenterX();
            int destY = dest.getCenterY();

            setBackground(new Color(0, true));
            setBounds(Math.min(srcX, destX), Math.min(srcY, destY), Math.abs(srcX-destX), Math.abs(srcY-destY));
            mainDiagonal = (srcX-destX)*(srcY-destY)>=0;
            setLayout(null);

            setName(String.format(EDGE_NAME, src.getLabel(), dest.getLabel()));
        }
        public JEdge(int srcX, int srcY, int destX, int destY) {
            setBackground(new Color(0, true));
            setBounds(Math.min(srcX, destX), Math.min(srcY, destY), Math.abs(srcX-destX), Math.abs(srcY-destY));
            mainDiagonal = (srcX-destX)*(srcY-destY)>=0;
            setLayout(null);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.WHITE);
            if(mainDiagonal) {
                g.drawLine(0, 0, getWidth(), getHeight());
            } else {
                g.drawLine(0, getHeight(), getWidth(), 0);
            }
        }
    }
}