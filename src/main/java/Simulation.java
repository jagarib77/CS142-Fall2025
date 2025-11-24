/*
Munna
*/

import java.util.ArrayList;

public class Simulation {

    private int rows;
    private int cols;
    private SimulationObject[][] grid;
    private ArrayList<SimulationObject> objects;
    private boolean isActive = true;
    private double speedMultiplier = 1.0;

    public Simulation(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new SimulationObject[rows][cols];
        this.objects = new ArrayList<>();
        initializeGrid();
    }

    private void initializeGrid() {
        Boid b = new Boid(new Vector2D(1, 1));
        Tree t = new Tree(new Vector2D(1, 1), 5);
        Car c = new Car(new Vector2D(3, 2), 2);

        addObject(b);
        addObject(t);
        addObject(c);

        grid[1][1] = b;
        grid[2][3] = t;
        grid[0][4] = c;
    }

    public void addObject(SimulationObject obj) {
        objects.add(obj);
    }

    public void step() {
        if(isActive) {
            ArrayList<Boid> boidList = new ArrayList<>();
            for(SimulationObject obj : objects) {
                if(obj instanceof Boid) {
                    boidList.add((Boid)obj);
                }
            }
            Boid[] boids = boidList.toArray(new Boid[0]);
            
            for(SimulationObject obj : objects) {
                if(obj instanceof Boid) {
                    ((Boid)obj).update(boids, speedMultiplier);
                } else {
                    obj.update();
                }
            }
        }
    }

    public Vector2D getRandomPoint(){
        return new Vector2D(Math.random()*rows, Math.random()*cols);
    }

    void start() {
        isActive = true;
    }

    void pause() {
        isActive = false;
    }

    void addRandomBoid() {
        objects.add(new Boid(getRandomPoint()));
    }

    void addRandomSuperBoid() {
        objects.add(new SuperBoid(getRandomPoint()));
    }

    void addRandomSadBoid() {
        objects.add(new SadBoid(getRandomPoint()));
    }

    void addRandomCar() {
        objects.add(new Car(getRandomPoint(), 1));
    }

    void addRandomTree() {
        objects.add(new Tree(getRandomPoint(), 2));
    }

    void addRandomWindow() {
        objects.add(new Window(getRandomPoint(), 2));
    }

    Iterable<SimulationObject> getObjects() {
        return objects;
    }

    public void setSpeedMultiplier(double multiplier) {
        this.speedMultiplier = multiplier;
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }
}