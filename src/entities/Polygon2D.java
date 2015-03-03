package entities;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * A polygon with double coordinates. Points must be added in counter-clockwise order.
 * 9 methods
 * @author Neelay Junnarkar
 * @author Tyler Packard
 */
public class Polygon2D {
    private Point2D.Double[] vertices;
    private Point2D.Double pos = new Point2D.Double(0, 0);

    /**
     * POINTS MUST BE IN COUNTER-CLOCKWISE ORDER
     *
     * @param xPoints The x-points of the polygon
     * @param yPoints The y-points of the polygon
     */
    public Polygon2D(double[] xPoints, double[] yPoints) {
        this(xPoints, yPoints, new Point2D.Double(0, 0));
    }

    /**
     * POINTS MUST BE IN COUNTER-CLOCKWISE ORDER
     *
     * @param xPoints The x-points of the polygon
     * @param yPoints The y-points of the polygon
     * @param pos The position of the polygon
     */
    public Polygon2D(double[] xPoints, double[] yPoints, Point2D.Double pos) {
        int numPoints = Math.min(xPoints.length, yPoints.length);
        vertices = new Point2D.Double[numPoints];
        for (int i = 0; i < numPoints; ++i) {
            vertices[i] = new Point2D.Double(xPoints[i], yPoints[i]);
        }
        this.pos = pos;
    }

    public Point2D.Double setPos(double x, double y) {
        pos.setLocation(x, y);
        return (Point2D.Double)pos.clone();
    }

    public Point2D.Double setPos(Point2D.Double newPt) {
        return setPos(newPt.getX(), newPt.getY());
    }

    public Point2D.Double getPos() {
        return pos;
    }

    public Point2D.Double move(double x, double y) {
        pos.setLocation(pos.getX()+x, pos.getY()+y);
        return (Point2D.Double)pos.clone();
    }

    public Point2D.Double[] getVertices() {
        return vertices;
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
