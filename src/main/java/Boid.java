class Boid extends SimulationObject{
    private Vector2D position;
    private Vector2D velocity;

    public Boid(Vector2D position) {
        super(position);
    }

    public void update(Boid[] boids) {
        Vector2D separationDelta = new Vector2D();
        Vector2D alignmentSum = new Vector2D();
        Vector2D cohesionSum = new Vector2D();
        int visibleNeighbors = 0;
        for(Boid boid : boids){
            if(this.position.distanceTo(boid.getPosition()) < this.getProtectedRange()){
                separationDelta = separationDelta.plus(this.position.minus(boid.getPosition()));
            }
            if(this.position.distanceTo(boid.getPosition()) < this.getVisibleRange()){
                alignmentSum = alignmentSum.plus(boid.getVelocity());
                cohesionSum = cohesionSum.plus((boid.getPosition().minus(this.position)).times(boid.getAttractionFactor()));
                visibleNeighbors++;
            }
        }
        velocity = velocity.plus(separationDelta).times(this.getSeparationFactor());
        velocity = alignmentSum.times((double)1 / visibleNeighbors).minus(velocity).times(this.getAlignmentFactor());
        velocity = cohesionSum.times((double)1 / visibleNeighbors);
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
        return 1.0;
    }
    public double getMaximumSpeed(){
        return 5.0;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    public Vector2D getVelocity(){
        return this.velocity;
    }
}
