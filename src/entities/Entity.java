package entities;

import physics.Axis;
import physics.Vector;
import physics.Force;

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
    
    private HashMap<String, Force> forces = new HashMap<>();
    private Point2D.Double pos = new Point2D.Double();
    private Polygon2D shape;
    private Color color = new Color(0xEEEEEE);
    private Axis[] sepAxes;
    private double mass;
    private Vector velocity;
    private double slowDownDist = 0.01;
    
    public Entity(Polygon2D shape) {
        this(shape, new Point2D.Double(0 , 0), 0);
    }
    
    public Entity(Polygon2D shape, Point2D.Double pos, double mass) {
        this.pos = pos;
        this.shape = shape;
        shape.setPos(pos);
        this.mass = mass;
        velocity = new Vector();
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
        Vector totalVelocityDelta = new Vector();
        for (Force force : forces.values()) {
            Vector deltap = new Vector((force.getMass()/mass)*Math.sqrt(Math.pow(force.getAcceleration().getX(), 2.0) + Math.pow(force.getAcceleration().getY(), 2)),
                    force.getAcceleration().getAngle());

            totalVelocityDelta.add(deltap);//add x and y accelerations to totalVelocityDeta, a2 = (m1*a1)/m2, where m1 and a1 are of force, and a2 is being found for this entity
        }

        velocity = Vector.add(velocity, totalVelocityDelta);
        //System.out.println(velocity.getX()+" "+velocity.getY());
        return move(delta * velocity.getX(), delta * velocity.getY());
    }

    public void addConstantForce(String key, Force force) {
        forces.put(key, force);
    }
    
    public void addForce(Force force) {
        Vector acceleration = new Vector((force.getMass()/mass)*Math.sqrt(Math.pow(force.getAcceleration().getX(), 2.0) + Math.pow(force.getAcceleration().getY(), 2.0)),
                force.getAcceleration().getAngle());
        
        velocity = Vector.add(velocity, acceleration);
        System.out.println(velocity.getX()+" "+velocity.getY());

    }

    public Force removeForce(String key) {
        return forces.remove(key);
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
