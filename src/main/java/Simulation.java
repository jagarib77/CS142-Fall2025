import java.util.ArrayList;

public class Simulation {

    private int rows;
    private int cols;
    private SimulationObject[][] grid;
    private ArrayList<SimulationObject> objects;

    public Simulation(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new SimulationObject[rows][cols];
        this.objects = new ArrayList<>();
        initializeGrid();
    }

    /**
     * Example setup for milestone: adding basic objects.
     */
    private void initializeGrid() {
        // Sample objects - make sure these classes exist in your project
        Boid b = new Boid(1, 1);
        Tree t = new Tree(2, 3);
        Car c = new Car(0, 4);
        Obstacle o = new Obstacle(3, 2);

        addObject(b);
        addObject(t);
        addObject(c);
        addObject(o);

        grid[1][1] = b;
        grid[2][3] = t;
        grid[0][4] = c;
        grid[3][2] = o;
    }

    /**
     * Adds an object to the simulation list.
     */
    public void addObject(SimulationObject obj) {
        objects.add(obj);
    }

    /**
     * Prints the initial contents of the grid.
     */
    public void printGrid() {
        System.out.println("Initial Simulation Grid:\n");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(grid[r][c].getSymbol() + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Placeholder simulation step for milestone.
     */
    public void step() {
        System.out.println("Simulation step executed (placeholder).");
    }
}
