package physics;

/**
 * A 2D Vector in polar form.
 *
 * @author Tyler Packard
 */
public class Vector {
    private double radius = 0;

    private double angle = 0;


    public Vector() {

    }

    public Vector(double radius, double angle) {
        this.radius = radius;
        this.angle = angle;
    }


    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getX() {
        return radius * Math.cos(angle);
    }

    private void setX(double x) {
        double y = getY();
        radius = Math.sqrt(x * x + y * y);
        angle = Math.tan(y / x);
    }

    public double getY() {
        return radius * Math.sin(angle);
    }

    private void setY(double y) {
        double x = getX();
        radius = Math.sqrt(x * x + y * y);
        angle = Math.tan(y / x);
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
}
