package physics;

/**
 * Created by 95028672 on 2/9/2015.
 */
public class Force {
    private double mass;
    private Vector acceleration;
    
    public Force() {
        this(0, 0);        
    }
    
    public Force(double mass, Vector acceleration) {
        this.mass = mass;
        this.acceleration = acceleration;
    }
    
    public double getMass() {
        return mass;        
    }
    
    public Vector getAcceleration() {
        return acceleration;        
    }
}
