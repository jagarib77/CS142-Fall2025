import java.awt.Color;
// Created by Apollo
// The Boid class is a class that represents a single boid in the simulation
// It runs the "boids" algorithm in tandem with multiple other boids
class Boid extends SimulationObject{
    private Vector2D velocity;
    private final double edgeMargin = 5.0;
    private final double turnFactor = 1.0;
    private int gridRows;
    private int gridCols;

    // Initializes the boid and puts it on a random grid space
    public Boid(Vector2D position, int gridRows, int gridCols) {
        super(position);
        this.gridRows = gridRows;
        this.gridCols = gridCols;
        this.velocity = new Vector2D(Math.random() * 2 - 1, Math.random() * 2 - 1).normalized().times(0.5);
    }

    // Updates the current boid based on boid and obstacle locations
    public void update(Boid[] boids, Obstacle[] obstacles, double speedMultiplier) {
        Vector2D acceleration = new Vector2D();

        Vector2D separationForce = new Vector2D();
        Vector2D alignmentSum = new Vector2D();
        Vector2D cohesionSum = new Vector2D();
        int visibleNeighbors = 0;

        for(Boid boid : boids){
            if(boid == this) continue;
            double dist = this.position.distanceTo(boid.getPosition());

            if(dist < this.getProtectedRange()){
                separationForce = separationForce.plus(this.position.minus(boid.getPosition()));
            }

            if(dist < this.getVisibleRange()){
                alignmentSum = alignmentSum.plus(boid.getVelocity());
                cohesionSum = cohesionSum.plus(boid.getPosition());
                visibleNeighbors++;
            }
        }

        acceleration = acceleration.plus(separationForce.times(this.getSeparationFactor()));

        if(visibleNeighbors > 0){
            Vector2D avgAlignment = alignmentSum.times(1.0 / visibleNeighbors);
            Vector2D alignmentForce = avgAlignment.minus(this.velocity).times(this.getAlignmentFactor());
            acceleration = acceleration.plus(alignmentForce);

            Vector2D avgPosition = cohesionSum.times(1.0 / visibleNeighbors);
            Vector2D cohesionForce = avgPosition.minus(this.position).times(this.getAttractionFactor());
            acceleration = acceleration.plus(cohesionForce);
        }

        Vector2D obstacleAvoidance = new Vector2D();
        double obstacleAvoidanceRange = 10.0;
        for(Obstacle obstacle : obstacles) {
            double dist = this.position.distanceTo(obstacle.getPosition());
            if(dist < obstacleAvoidanceRange) {
                Vector2D awayFromObstacle = this.position.minus(obstacle.getPosition());
                double strength = (obstacleAvoidanceRange - dist) / obstacleAvoidanceRange;
                obstacleAvoidance = obstacleAvoidance.plus(awayFromObstacle.times(strength));
            }
        }
        double obstacleAvoidanceFactor = 0.5;
        acceleration = acceleration.plus(obstacleAvoidance.times(obstacleAvoidanceFactor));

        Vector2D boundaryForce = new Vector2D();
        if(this.position.x < edgeMargin) boundaryForce.x += turnFactor;
        if(this.position.x > this.gridRows - edgeMargin) boundaryForce.x -= turnFactor;
        if(this.position.y < edgeMargin) boundaryForce.y += turnFactor;
        if(this.position.y > this.gridCols - edgeMargin) boundaryForce.y -= turnFactor;

        acceleration = acceleration.plus(boundaryForce);

        velocity = velocity.plus(acceleration);

        double speed = velocity.magnitude();
        if(speed > getMaximumSpeed()){
            velocity = velocity.normalized().times(getMaximumSpeed());
        }else if(speed < getMinimumSpeed()){
            velocity = velocity.normalized().times(getMinimumSpeed());
        }
        position = position.plus(velocity.times(speedMultiplier));
    }

    // Gets the range that the boid can see other boids
    public double getVisibleRange(){
        return 15.0;
    }
    // Gets the range that the boid can protect itself from
    public double getProtectedRange(){
        return 3.0;
    }

    // These get the factor of separation, alignment, and attraction
    public double getSeparationFactor(){
        return 0.1;
    }
    public double getAlignmentFactor(){
        return 0.01;
    }
    public double getAttractionFactor() {
        return 0.01;
    }

    // These get the minimum and maximum speeds for the boid
    public double getMinimumSpeed(){
        return 0.1;
    }
    public double getMaximumSpeed(){
        return 0.5;
    }

    // Returns the position of the boid
    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    // Returns the velocity of the boid
    public Vector2D getVelocity(){
        return this.velocity;
    }

    // Returns the color of the boid
    @Override
    public Color getColor() {
        return new Color(0, 150, 150);
    }
}