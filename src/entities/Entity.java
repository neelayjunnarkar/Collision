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
    
    private HashMap<String, Vector> velocities = new HashMap<>();
    private Point2D.Double pos = new Point2D.Double();
    private Polygon2D shape;
    private Color color = new Color(0xEEEEEE);
    private Axis[] sepAxes;
    private double mass;
    private Vector velocity = new Vector(new Point2D.Double(0, 0));
    public Vector getVelocity() {
        return velocity;
    }
    public double getMass() { return mass; }

    public Entity(Polygon2D shape) {
        this(shape, new Point2D.Double(0 , 0), 1);
    }
    
    public Entity(Polygon2D shape, Point2D.Double pos, double mass) {
        this.pos = pos;
        this.shape = shape;
        shape.setPos(pos);
        this.mass = mass;
        genSepAxes();
    }
    
    public void addVelocity(String key, Vector velocity) {
        if (velocities.get(key) != null) {
            velocities.get(key).setAngle(velocity.getAngle());
            velocities.get(key).setMagnitude(velocity.getMagnitude());
            return;
        }
        velocities.put(key, velocity);
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
        Vector totalVelocity = new Vector();
        for (Vector velocity : velocities.values()) {
            totalVelocity.add(velocity);
        }

        velocity = totalVelocity;
        //System.out.println(velocity.getX()+" "+velocity.getY());
        return move(delta * velocity.getX(), delta * velocity.getY());
    }



    public Vector removeForce(String key) {
        return velocities.remove(key);
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
