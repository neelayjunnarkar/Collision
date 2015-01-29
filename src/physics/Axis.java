package physics;

import entities.Polygon2D;

/**
 * Represents an axis onto which a polygon is projected.
 */
public class Axis {
    double angle;

    public Axis(double angle) {
        this.angle = angle;
    }

    public double projectionLength(Polygon2D polygon) {
        return 0;
    }
}
