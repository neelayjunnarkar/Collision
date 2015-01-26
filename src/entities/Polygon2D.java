package entities;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * A polygon with double coordinates.
 *
 * @author Neelay Junnarkar
 * @author Tyler Packard
 */
public class Polygon2D {
    Point2D.Double[] vertices = new Point2D.Double[]{};
    int npoints = 0;
    Point2D.Double pos = new Point2D.Double(0, 0);


    public Polygon2D(Point2D.Double[] vertices, int npoints, Point2D.Double pos) {
        this.vertices = vertices;
        this.npoints = npoints;
        this.pos = pos;
    }


    public Point2D.Double setPos(double x, double y) {
        pos.setLocation(x, y);
        return (Point2D.Double)pos.clone();
    }

    public Point2D.Double setPos(Point2D.Double new_pt) {
        return setPos(new_pt.getX(), new_pt.getY());
    }

    public Point2D.Double translate(double x, double y) {
        return null;
    }

    public Polygon approxPoly() {
        Polygon approxPoly = new Polygon();
        for (Point2D.Double vertex : vertices) {
            int x = (int) Math.round(vertex.getX() + pos.getX());
            int y = (int) Math.round(vertex.getY() + pos.getY());
            approxPoly.addPoint(x, y);
        }

        return approxPoly;
    }

    public void draw(Graphics2D g2d) {
        g2d.fillPolygon(approxPoly());
    }
}
