import java.awt.Color;

class Boid extends SimulationObject{
    private Vector2D velocity;

    public Boid(Vector2D position) {
    super(position);
    this.velocity = new Vector2D(Math.random() * 2 - 1, Math.random() * 2 - 1).normalized().times(0.5);
    }

    public void update(Boid[] boids) {
        Vector2D separationDelta = new Vector2D();
        Vector2D alignmentSum = new Vector2D();
        Vector2D cohesionSum = new Vector2D();
        int visibleNeighbors = 0;
        for(Boid boid : boids){
            if(boid == this) continue;
            if(this.position.distanceTo(boid.getPosition()) < this.getProtectedRange()){
                separationDelta = separationDelta.plus(this.position.minus(boid.getPosition()));
            }
            if(this.position.distanceTo(boid.getPosition()) < this.getVisibleRange()){
                alignmentSum = alignmentSum.plus(boid.getVelocity());
                cohesionSum = cohesionSum.plus(boid.getPosition());
                visibleNeighbors++;
            }
        }
        velocity = velocity.plus(separationDelta.times(this.getSeparationFactor()));
        if(visibleNeighbors > 0){
            Vector2D avgAlignment = alignmentSum.times(1.0 / visibleNeighbors);
            velocity = velocity.plus(avgAlignment.minus(velocity).times(this.getAlignmentFactor()));
            Vector2D avgPosition = cohesionSum.times(1.0 / visibleNeighbors);
            Vector2D cohesionForce = avgPosition.minus(this.position).times(this.getAttractionFactor());
            velocity = velocity.plus(cohesionForce);
        }
        double speed = velocity.magnitude();
        if(speed > getMaximumSpeed()){
            velocity = velocity.normalized().times(getMaximumSpeed());
        }else if(speed < getMinimumSpeed()){
            velocity = velocity.normalized().times(getMinimumSpeed());
        }
        position = position.plus(velocity);
    }

    public double getVisibleRange(){
        return 30.0;
    }
    public double getProtectedRange(){
        return 10.0;
    }
    public double getSeparationFactor(){
        return 0.05;
    }
    public double getAlignmentFactor(){
        return 1.0;
    }
    public double getAttractionFactor(){
        return 0.05;
    }

    public double getMinimumSpeed(){
        return 0.3;
    }
    public double getMaximumSpeed(){
        return 1.1;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    public Vector2D getVelocity(){
        return this.velocity;
    }
    
    @Override
    public Color getColor() {
        return new Color(0, 0, 255);
    }
}