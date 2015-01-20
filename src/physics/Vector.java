package physics;

import java.awt.geom.Point2D;

/**
 * A 2D Vector in rectangular form.
 *
 * @author Tyler Packard
 */
public class Vector implements Cloneable {
    private Point2D.Double head = new Point2D.Double(0, 0);


    public Vector() {

    }

    public Vector(double radius, double angle) {
        setRadius(radius);
        setAngle(angle);
    }


    public double getAngle() {
        return Math.atan2(head.getY(), head.getX());
    }

    public void setAngle(double angle) {
        head.setLocation(getRadius() * Math.cos(angle), getRadius() * Math.sin(angle));
    }

    public double getRadius() {
        return head.distance(0, 0);
    }

    public void setRadius(double radius) {
        head.setLocation(radius * Math.cos(getAngle()), radius * Math.sin(getAngle()));
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

    public static Vector add(Vector v1, Vector v2) {
        Vector sum = new Vector();
        sum.setX(v1.getX() + v2.getX());
        sum.setY(v1.getY() + v2.getY());
        return sum;
    }

    public static Vector subtract(Vector v1, Vector v2) {
        Vector difference = new Vector();
        difference.setX(v1.getX() - v2.getX());
        difference.setY(v1.getY() - v2.getY());
        return difference;
    }

    public static double dot(Vector v1, Vector v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    @Override
    public Vector clone() {
        return new Vector(getX(), getY());
    }
}
