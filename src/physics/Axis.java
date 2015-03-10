package physics;

import entities.Polygon2D;

import java.awt.geom.Point2D;

/**
 * Represents an axis onto which a polygon is projected.
 *
 * @author Tyler Packard
 */
public class Axis {
    Vector axis;

    public Axis(Point2D.Double v1, Point2D.Double v2) {
        axis = new Vector(v1, v2).normal();
        axis.normalize();
    }

    public double[] projection(Polygon2D polygon) {
        double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;
        for (Point2D.Double vertex : polygon.getVertices()) {
            Vector point = Vector.add(new Vector(vertex), new Vector(polygon.getPos()));
            double projection = Vector.dot(point, axis);
            if (projection < min) min = projection;
            if (projection > max) max = projection;
        }

        return new double[] {min, max};
    }
}
