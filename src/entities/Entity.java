package entities;

import physics.Axis;
import physics.Vector;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 * Entity class
 *
 * @author Neelay Junnarkar
 * @author Tyler Packard
 */
public class Entity {
    private HashMap<String, Vector> forces = new HashMap<>();
    private Point2D.Double pos = new Point2D.Double();
    private Polygon2D shape;
    private Color color = new Color(0xEEEEEE);
    private Axis[] sepAxes;

    public Entity(Polygon2D shape) {
        this(shape, new Point2D.Double(0 , 0));
    }
    
    public Entity(Polygon2D shape, Point2D.Double pos) {
        this.pos = pos;
        this.shape = shape;
        shape.setPos(pos);

        genSepAxes();
    }

    private void genSepAxes() {
        Point2D.Double[] vertices = shape.getVertices();
        sepAxes = new Axis[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            Point2D.Double v1 = vertices[i];
            Point2D.Double v2 = vertices[(i+1)%vertices.length];

            sepAxes[i] = new Axis(v1, v2);
        }
    }

    public Point2D.Double getPos() {
        return (Point2D.Double)pos.clone();
    }

    public void setPos(Point2D.Double pt) {
        setPos(pt.getX(), pt.getY());
    }

    public void setPos(double x, double y) {
        shape.setPos(x, y);
        pos.setLocation(x, y);
    }

    public Point2D.Double move(double x, double y) {
        pos.setLocation(pos.getX() + x, pos.getY() + y);
        setPos(pos.getX(), pos.getY());
        return (Point2D.Double)pos.clone();
    }

    public Point2D.Double update(double delta) {
        Vector totalForce = new Vector();
        for (Vector force : forces.values()) {
            totalForce.add(force);
        }
        return move(delta * totalForce.getX(), delta * totalForce.getY());
    }

    public void addForce(String key, Vector force) {
        forces.put(key, force);
    }

    public boolean removeForce(String key) {
        return forces.remove(key) != null;
    }

    private boolean inRange(double point, double[] range) {
        double min = range[0];
        double max = range[1];
        if (max < min) {
            min = range[1];
            max = range[0];
        }

        return (point >= min && point <= max);
    }

    private boolean projectionOverlaps(double[] projectionA, double[] projectionB) {
        if (inRange(projectionA[0], projectionB)) return true;
        if (inRange(projectionA[1], projectionB)) return true;
        if (inRange(projectionB[0], projectionA)) return true;
        if (inRange(projectionB[1], projectionA)) return true;
        return false;
    }

    public boolean overlaps(Entity other) {
        for (Axis axis : sepAxes) {
            double[] thisProjection = axis.projection(shape);
            double[] otherProjection = axis.projection(other.shape);
            if (!projectionOverlaps(thisProjection, otherProjection)) return false;
        }
        for (Axis axis : other.sepAxes) {
            double[] thisProjection = axis.projection(shape);
            double[] otherProjection = axis.projection(other.shape);
            if (!projectionOverlaps(thisProjection, otherProjection)) return false;
        }
        return true;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        shape.draw(g2d);
    }
}
