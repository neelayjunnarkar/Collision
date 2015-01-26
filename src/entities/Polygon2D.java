package entities;

import java.awt.geom.Point2D;

/**
 * 
 * @author Neelay Junnarkar
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
    
    
}
