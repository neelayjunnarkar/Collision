package physics;

import java.awt.geom.Point2D;

/**
 * A 2D Vector in rectangular form.
 *
 * @author Tyler Packard
 */
public class Vector implements Cloneable {
    private Point2D.Double head = new Point2D.Double(0, 0);


    public Vector() {}

    public Vector(double magnitude, double angle) {
        setMagnitude(magnitude);
        setAngle(angle);
    }

    public Vector(Point2D.Double head) {
        this.head.setLocation(head.getX(), head.getY());
    }

    public Vector(Point2D.Double base, Point2D.Double head) {
        this.head.x = head.getX() - base.getX();
        this.head.y = head.getY() - base.getY();
    }


    public double getAngle() {
        return Math.atan2(head.getY(), head.getX()) % (2 * Math.PI);
    }

    public void setAngle(double angle) {
        head.setLocation(getMagnitude() * Math.cos(angle), getMagnitude() * Math.sin(angle));
    }

    public void rotate(double angle) {
        setAngle(getAngle() + angle);
    }

    public double getMagnitude() {
        return head.distance(0, 0);
    }

    public void setMagnitude(double magnitude) {
        head.setLocation(magnitude * Math.cos(getAngle()), magnitude * Math.sin(getAngle()));
    }

    public double getX() {
        return head.getX();
    }

    private void setX(double x) {
        head.x = x;
    }

    public double getY() {
        return head.getY();
    }

    private void setY(double y) {
        head.y = y;
    }

    public void add(Vector v) {
        head.x += v.getX();
        head.y += v.getY();
    }

    public static Vector add(Vector v1, Vector v2) {
        Vector sum = v1.clone();
        sum.add(v2);
        return sum;
    }

    public void subtract(Vector v) {
        head.x -= v.getX();
        head.y -= v.getY();
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
        head.x /= getMagnitude();
        head.y /= getMagnitude();
    }

    public Vector normal() {
        Vector normal = new Vector();
        normal.setX(-head.getY());
        normal.setY(head.getX());
        return normal;
    }

    @Override
    public Vector clone() {
        return new Vector(new Point2D.Double(getX(), getY()));
    }

    @Override
    public String toString() {
        return "<" + head.getX() + ", " + head.getY() + ">";
    }
}
