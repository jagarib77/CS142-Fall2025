/*
Created by Munna
*/

import java.util.ArrayList;

public class Simulation {

    private final int rows;
    private final int cols;
    private SimulationObject[][] grid;
    private ArrayList<SimulationObject> objects;
    private boolean isActive = true;
    private double speedMultiplier = 1.0;

    // Creates the simulation given a grid size
    public Simulation(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new SimulationObject[rows][cols];
        this.objects = new ArrayList<>();
    }

    // These get the number of rows and columns in the grid
    public int getCols() {
        return cols;
    }
    public int getRows() {
        return rows;
    }

    // Adds an object to the simulation
    public void addObject(SimulationObject obj) {
        objects.add(obj);
    }

    // Updates the simulation by one step, which also updates each object in the simulation
    public void step() {
        if(isActive) {
            ArrayList<Boid> boidList = new ArrayList<>();
            for(SimulationObject obj : objects) {
                if(obj instanceof Boid) {
                    boidList.add((Boid)obj);
                }
            }
            Boid[] boids = boidList.toArray(new Boid[0]);
            
            ArrayList<Obstacle> avoidableObstacles = new ArrayList<>();
            ArrayList<Obstacle> collidableObstacles = new ArrayList<>();
            
            for(SimulationObject obj : objects) {
                if(obj instanceof Obstacle) {
                    collidableObstacles.add((Obstacle) obj);
                    if(!(obj instanceof Window)) {
                        avoidableObstacles.add((Obstacle) obj);
                    }
                }
            }
            
            for(SimulationObject obj : objects) {
                if(obj instanceof Boid) {
                    ((Boid)obj).update(boids, avoidableObstacles.toArray(new Obstacle[0]), speedMultiplier);
                } else {
                    obj.update(speedMultiplier);
                }
            }
            
            ArrayList<Boid> boidsToRemove = new ArrayList<>();
            for(Boid boid : boidList) {
                for(Obstacle obstacle : collidableObstacles) {
                    if(obstacle.isOverlapped(boid.getPosition())) {
                        obstacle.bump(boid);
                        boidsToRemove.add(boid);
                        break;
                    }
                }
            }
            
            objects.removeAll(boidsToRemove);
        }
    }

    // Gets a random point in the grid
    public Vector2D getRandomPoint(){
        return new Vector2D(Math.random()*rows, Math.random()*cols);
    }

    // Starts and pauses the simulation
    void start() {
        isActive = true;
    }
    void pause() {
        isActive = false;
    }

    // These add different types of boids to random positions in the grid
    void addRandomBoid() {
        objects.add(new Boid(getRandomPoint(), this.rows, this.cols));
    }
    void addRandomSuperBoid() {
        objects.add(new SuperBoid(getRandomPoint(), this.rows, this.cols));
    }
    void addRandomSadBoid() {
        objects.add(new SadBoid(getRandomPoint(), this.rows, this.cols));
    }

    // These add different types of obstacles to random positions in the grid
    void addRandomCar() {
        objects.add(new Car(getRandomPoint(), 0.2));
    }
    void addRandomTree() {
        objects.add(new Tree(getRandomPoint(), 2));
    }
    void addRandomWindow() {
        objects.add(new Window(getRandomPoint(), 2));
    }

    // This returns the list of objects in the simulation
    Iterable<SimulationObject> getObjects() {
        return objects;
    }

    // This sets the speed multiplier for the simulation, allowing the user to adjust how fast it steps
    public void setSpeedMultiplier(double multiplier) {
        this.speedMultiplier = multiplier;
    }

    // This returns the current speed multiplier for the simulation
    public double getSpeedMultiplier() {
        return speedMultiplier;
    }
}