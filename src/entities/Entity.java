package entities;

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
public class Entity implements Drawable {
    HashMap<String, Vector> forces = new HashMap<>();
    Point2D.Double pos = new Point2D.Double();
    Polygon2D shape;
    Color color = new Color(0xEEEEEE);

    public Entity(Polygon2D shape) {
        this(shape, new Point2D.Double(0 , 0));
    }
    
    public Entity(Polygon2D shape, Point2D.Double pos) {
        this.pos = pos;
        this.shape = shape;
        shape.setPos(pos);
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
        System.out.println((delta * totalForce.getX())+" "+ (delta * totalForce.getY()));
        return move(delta * totalForce.getX(), delta * totalForce.getY());
    }

    public void addForce(String key, Vector force) {
        forces.put(key, force);
    }

    public Vector removeForce(String key) {
        return forces.remove(key).clone();
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        shape.draw(g2d);
       // System.out.println("("+pos.getX()+", "+pos.getY()+")         "+shape.getBounds().getX()+", "+shape.getBounds().getY());
        // For testing purposes
       // g2d.fillRect((int) Math.round(pos.getX()), (int) Math.round(pos.getY()), 16, 16);
    }
}
