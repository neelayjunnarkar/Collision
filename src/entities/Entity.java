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
public class Entity {
    private HashMap<String, Vector> forces = new HashMap<>();
    private Point2D.Double pos = new Point2D.Double();
    private Polygon shape;
    private Color color;

    public Entity(Polygon shape) {
        this(shape, new Point2D.Double(0 , 0), new Color(0xEEEEEE));
    }
    
    public Entity(Polygon shape, Point2D.Double pos, Color color) {
        this.pos = pos;
        this.shape = shape;
        this.color = color;
        

        int x_sum = 0, y_sum = 0;
        for (int i = 0; i < shape.npoints; ++i) {
            x_sum += shape.xpoints[i];
            y_sum += shape.ypoints[i];
        }
        Point2D.Double temp_pos = new Point2D.Double(x_sum/shape.npoints, y_sum/shape.npoints);
        double x_dist = pos.getX()-temp_pos.getX();
        double y_dist = pos.getY()-temp_pos.getY();
        shape.translate((int)Math.round(x_dist), (int)Math.round(y_dist));
    }

    public Point2D.Double getPos() {
        return (Point2D.Double)pos.clone();
    }

    public void setPos(Point2D.Double pt) {
        setPos(pt.getX(), pt.getY());
    }

    public void setPos(double x, double y) {
        shape.translate((int)Math.round(x-pos.getX()), (int)Math.round(y-pos.getY()));
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
        g2d.fillPolygon(shape);
       // System.out.println("("+pos.getX()+", "+pos.getY()+")         "+shape.getBounds().getX()+", "+shape.getBounds().getY());
        // For testing purposes
       // g2d.fillRect((int) Math.round(pos.getX()), (int) Math.round(pos.getY()), 16, 16);
    }
}
