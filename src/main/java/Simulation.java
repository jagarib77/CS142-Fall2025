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

    public Simulation(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new SimulationObject[rows][cols];
        this.objects = new ArrayList<>();
        initializeGrid();
    }

    private void initializeGrid() {
        Boid b = new Boid(new Vector2D(1, 1));
        Tree t = new Tree(new Vector2D(1, 1), new Vector2D(1 , 1));
        Car c = new Car(new Vector2D(3, 2), new Vector2D(1,1), 2);

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
            for(SimulationObject obj : objects) {
                obj.update();
            }
        }
    }

    void start() {
        isActive = true;
    }

    void pause() {
        isActive = false;
    }

    void addRandomBoid() {
        objects.add(new Boid(new Vector2D(1, 1)));
    }

    void addRandomSuperBoid() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void addRandomSadBoid() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void addRandomCar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void addRandomTree() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void addRandomWindow() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Iterable<SimulationObject> getObjects() {
        return objects;
    }
}
