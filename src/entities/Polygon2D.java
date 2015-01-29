package entities;

import physics.Vector;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * A polygon with double coordinates. Points must be added in counter-clockwise order.
 *
 * @author Neelay Junnarkar
 * @author Tyler Packard
 */
public class Polygon2D {
    private Point2D.Double[] vertices;
    private Point2D.Double pos = new Point2D.Double(0, 0);

    private double[] sepAxes;


    /**
     * POINTS MUST BE IN COUNTER-CLOCKWISE ORDER
     *
     * @param xPts The x-points of the polygon
     * @param yPts The y-points of the polygon
     * @param npoints The number of points in the polygon
     */
    public Polygon2D(double[] xPts, double[] yPts, int npoints) {
        this(xPts, yPts, npoints, new Point2D.Double(0, 0));
        
    }

    /**
     * POINTS MUST BE IN COUNTER-CLOCKWISE ORDER
     *
     * @param xPts The x-points of the polygon
     * @param yPts The y-points of the polygon
     * @param npoints The number of points in the polygon
     */
    public Polygon2D(double[] xPts, double[] yPts, int npoints, Point2D.Double pos) {
        vertices = new Point2D.Double[npoints];
        for (int i = 0; i < npoints; ++i) {
            vertices[i] = new Point2D.Double(xPts[i], yPts[i]);
        }
        this.pos = pos;

        genSepAxes();
    }

    private void genSepAxes() {
        sepAxes = new double[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            Point2D.Double v1 = vertices[i];
            Point2D.Double v2 = vertices[(i+1)%vertices.length];

            Vector side = new Vector(v1, v2);
            side.rotate(Math.PI / 2);

            sepAxes[i] = side.getAngle();
        }
    }

    public Point2D.Double setPos(double x, double y) {
        pos.setLocation(x, y);
        return (Point2D.Double)pos.clone();
    }

    public Point2D.Double setPos(Point2D.Double newPt) {
        return setPos(newPt.getX(), newPt.getY());
    }

    public Point2D.Double translate(double x, double y) {
        pos.setLocation(pos.getX()+x, pos.getY()+y);
        return (Point2D.Double)pos.clone();
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
