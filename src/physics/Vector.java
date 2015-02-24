package physics;

import java.awt.geom.Point2D;

/**
 * A 2D Vector in rectangular form.
 *
 * @author Neelay Junnarkar
 * @author Tyler Packard
 */
public class Vector implements Cloneable {
    private double magnitude;
    private double angle;

    public Vector() {}

    public Vector(double magnitude, double angle) {
        setMagnitude(magnitude);
        setAngle(angle);
    }

    public Vector(Point2D.Double head) {
        setX(head.getX());
        setY(head.getY());
    }

    public Vector(Point2D.Double base, Point2D.Double head) {
        setX(head.getX() - base.getX());
        setY(head.getY() - base.getY());
    }


    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void rotate(double angle) {
        this.angle += angle;
    }

    public double getMagnitude() {
        return magnitude;
    }
    
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public double getX() {
        return magnitude * Math.cos(angle);
    }

    public void setX(double x) {
        setHead(x, getY());
    }

    public double getY() {
        return magnitude * Math.sin(angle);
    }

    public void setY(double y) {
        setHead(getX(), y);
    }

    public Point2D.Double getHead() {
        return new Point2D.Double(getX(), getY());
    }

    public void setHead(double x, double y) {
        magnitude = Math.sqrt(x*x + y*y);
        angle = Math.atan2(y, x);
    }

    public void add(Vector v) {
        Point2D.Double head = new Point2D.Double();
        setX(getX() + v.getX());
        setY(head.y = getY() + v.getY());
    }

    public static Vector add(Vector v1, Vector v2) {
        Vector sum = v1.clone();
        sum.add(v2);
        return sum;
    }

    public void subtract(Vector v) {
        Point2D.Double head = new Point2D.Double();
        setX(getX() - v.getX());
        setY(getY() - v.getY());
    }

    public static Vector subtract(Vector v1, Vector v2) {
        Vector difference = v1.clone();
        difference.subtract(v2);
        return difference;
    }

    public static double dot(Vector v1, Vector v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public void normalize() {
        setX(getX() / getMagnitude());
        setY(getY() / getMagnitude());
    }

    public Vector normal() {
        Vector normal = new Vector();
        normal.setX(-getY());
        normal.setY(getX());
        return normal;
    }

    public Vector project(Vector v) {
        if (v.getMagnitude() == 0.0)
            return v.clone();
        double projX = (dot(this, v)/(v.getX()*v.getX() + v.getY()*v.getY()))*v.getX();
        double projY = (dot(this, v)/(v.getX()*v.getX() + v.getY()*v.getY()))*v.getY();
        return new Vector(Math.sqrt(projX*projX + projY*projY), Math.atan2(projY, projX));
    }
    
    public Vector reject(Vector v) {
        Vector projection = project(v);
        return Vector.subtract(this, projection);
    }

    @Override
    public Vector clone() {
        return new Vector(new Point2D.Double(getX(), getY()));
    }

    @Override
    public String toString() {
        return "<" + getX() + ", " + getY() + ">";
    }
}
