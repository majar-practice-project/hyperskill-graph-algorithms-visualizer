package visualizer.view.graph.util;

import java.awt.*;

/**
 * <p>a rotated rectangular bound box around a line specified by two points and a thickness,
 * similar to a border box around the line</p>
 */
public class BoundBox {
    private final double[] p1;
    private final double[] p2;
    private final double[] v1;
    private final double[] v2;
    public BoundBox(int[] point1, int[] point2, double thickness) {
        double[][] bounds = getBoundCorners(point1, point2, thickness);
        p1 = bounds[0];
        p2 = bounds[1];

        double[][] vectors = getVectors(bounds);
        v1 = vectors[0];
        v2 = vectors[1];
    }

    public boolean isInBound(Point point) {
        double[] v3 = new double[]{point.getX()-p1[0], point.getY()-p1[1]};
        double[] v4 = new double[]{point.getX()-p2[0], point.getY()-p2[1]};
        double dot1 = dotProduct(v3, v1);
        double dot2 = dotProduct(v4, v2);

        return 0<=dot1 && dot1<=dotProduct(v1,v1)
                && 0<=dot2 && dot2<=dotProduct(v2,v2);
    }

    public static double[][] getBoundCorners(int[] point1, int[] point2, double thickness) {
        double height = point2[1]-point1[1];
        double width = point2[0]-point1[0];
        double[] v = new double[] {-height, width};
        double multiplier = Math.sqrt(thickness*thickness / (v[0]*v[0]+v[1]*v[1]));
        v[0] = multiplier*v[0];
        v[1] = multiplier*v[1];

        return new double[][]{
                {point1[0] + v[0], point1[1] + v[1]},
                {point1[0] - v[0], point1[1] - v[1]},
                {point2[0] - v[0], point2[1] - v[1]}
        };
    }

    private static double[][] getVectors(double[][] bounds) {
        double[][] vectors = new double[bounds.length-1][2];
        for(int i=1; i<bounds.length; i++) {
            vectors[i-1][0] = bounds[i][0] - bounds[i-1][0];
            vectors[i-1][1] = bounds[i][1] - bounds[i-1][1];
        }
        return vectors;
    }

    private static double dotProduct(double[] p1, double[] p2) {
        return p1[0]*p2[0] + p1[1]*p2[1];
    }
}
