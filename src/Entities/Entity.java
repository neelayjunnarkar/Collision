package Entities;

import physics.Vector;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 * Entity class
 *
 * @author Neelay Junnarkar
 */
public class Entity implements Drawable {
    HashMap<String, Vector> forces;
    Point2D.Double pos = new Point2D.Double();
    Polygon shape;
    Color color;

    public Entity(Polygon shape) {
        this(shape, new Point2D.Double(0 , 0), new Color(255, 255, 255));
    }
    
    public Entity(Polygon shape, Point2D.Double pos, Color color) {
        this.pos = pos;
        this.shape = shape;
        this.color = color;
    }

    public Point2D.Double getPos() {
        return (Point2D.Double)pos.clone();
    }

    public void setPos(Point2D.Double pt) {
        pos.setLocation(pt.getX(), pt.getY());
    }

    public void setPos(double x, double y) {
        pos.setLocation(x, y);
    }

    public Point2D.Double move(double x, double y) {
        pos.setLocation(pos.getX() + x, pos.getY() + y);
        return (Point2D.Double)pos.clone();
    }

    public void addForce(String key, Vector force) {
        forces.put(key, force);
    }

    /**
     * @param key
     * @return returns the Value that is removed
     */
    public Vector removeForce(String key) {
        return forces.remove(key).clone();
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillPolygon(shape);
    }
}
