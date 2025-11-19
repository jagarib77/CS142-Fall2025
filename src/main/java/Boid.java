class Boid extends SimulationObject{
    private double maxSpeed = 5.0;
    private double minSpeed = 1.0;
    private double visibleRange = 30.0;
    private double protectedRange = 10.0;
    private double separationFactor = 0.05;
    private double alignmentFactor = 1.0;
    private double cohesionFactor = 0.05;

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
            if(this.position.distanceTo(boid.getPosition()) < protectedRange){
                separationDelta = separationDelta.plus(this.position.minus(boid.getPosition()));
            }
            if(this.position.distanceTo(boid.getPosition()) < visibleRange){
                alignmentSum = alignmentSum.plus(boid.getVelocity());
                cohesionSum = cohesionSum.plus((boid.getPosition().minus(this.position)).times(boid.getAttractionFactor()));
                visibleNeighbors++;
            }
        }
        velocity = velocity.plus(separationDelta).times(separationFactor);
        velocity = alignmentSum.times((double)1 / visibleNeighbors).minus(velocity).times(alignmentFactor);
        velocity = cohesionSum.times((double)1 / visibleNeighbors);
    }

    public double getAttractionFactor(){
        return this.cohesionFactor;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    public Vector2D getVelocity(){
        return this.velocity;
    }
}
