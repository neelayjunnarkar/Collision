package entities;

import java.awt.geom.Point2D;

/**
 * Created by 95028672 on 1/26/2015.
 */
public class Polygon2D {
    
    Point2D.Double[] vertices = new Point2D.Double[]{};
    int npoints = 0;
    
    public Polygon2D(Point2D.Double[] vertices, int npoints) {
        this.vertices = vertices;
        this.npoints = npoints;
    }
}
