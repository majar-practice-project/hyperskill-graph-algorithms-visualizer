package visualizer.view.graph;

import javax.swing.*;
import java.awt.*;

public class Vertex extends JPanel {
    private static final String vertexName = "Vertex %s";
    private static final String labelName = "VertexLabel %s";
    private static final int DEFAULT_VERTEX_SIZE = 50;
    private final int size;
    private final String label;

    public Vertex(String label, int x, int y) {
        this(label, x, y, DEFAULT_VERTEX_SIZE);
    }

    public Vertex(String label, int x, int y, int size) {
        this.label = label;
        this.size = size;
        setBounds(x - size / 2, y - size / 2, size, size);
        setBackground(new Color(0, true));
        setLayout(new GridBagLayout());
        JLabel labelElement = new JLabel(label);
        add(labelElement);

        //naming for testing purpose
        super.setName(String.format(vertexName, label));
        labelElement.setName(String.format(labelName, label));

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(0, 0, size, size);
        super.paint(g);
    }

    public String getLabel(){
        return label;
    }

    public int getCenterX() {
        return getX() + size / 2;
    }

    public int getCenterY() {
        return getY() + size / 2;
    }
}
