package physics;

public class Force implements Cloneable {
    private double mass;
    private Vector acceleration;

    public Force(Force copy) {
        this.acceleration = copy.getAcceleration();
        this.mass = copy.getMass();
    }

    public Force() {
        this(0, new Vector());
    }

    public Force(double mass, Vector acceleration) {
        this.mass = mass;
        this.acceleration = acceleration.clone();
    }

    public double getMass() {
        return mass;
    }

    public Vector getAcceleration() {
        return acceleration.clone();
    }

    @Override
    public Force clone() {
        return new Force(this);
    }
}
