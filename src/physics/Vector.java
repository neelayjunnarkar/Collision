package physics;

/**
 * A 2D Vector in rectangular form.
 *
 * @author Tyler Packard
 */
public class Vector {
    private double x = 0;
    private double y = 0;


    public Vector() {

    }

    public Vector(double radius, double angle) {
        setRadius(radius);
        setAngle(angle);
    }


    public double getAngle() {
        return Math.atan2(y, x);
    }

    public void setAngle(double angle) {
        x = getRadius() * Math.cos(angle);
        y = getRadius() * Math.sin(angle);
    }

    public double getRadius() {
        return Math.sqrt(x*x + y*y);
    }

    public void setRadius(double radius) {
        x = radius * Math.cos(getAngle());
        y = radius * Math.sin(getAngle());
    }

    public double getX() {
        return x;
    }

    private void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    private void setY(double y) {
        this.y = y;
    }

    public static Vector add(Vector v1, Vector v2) {
        Vector sum = new Vector();
        sum.setX(v1.x + v2.x);
        sum.setY(v1.y + v2.y);
        return sum;
    }

    public static Vector subtract(Vector v1, Vector v2) {
        Vector difference = new Vector();
        difference.setX(v1.x - v2.x);
        difference.setY(v1.y - v2.y);
        return difference;
    }
}
