import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//This class handles:
//Any rules of the simulation
public class ZombieInvasionModel {
    private Entity[][] grid;
    //Size of grid
    private int row;
    private int column;
    //Used to specify where Case Zero can Start NOTE: maybe scrap
    private int xCordinate;
    private int yCordinate;
    //Default values for # of zombies and medics and location
    private int numOfZombies;
    private int numOfMedics;
    private double infectionRate;
    private double healRate;
    //Default value chosenOne being enabled
    private boolean humanitySaved;


    //overloaded constructor creates Entity[][] grid
    // with specified settings set in GUI
    public ZombieInvasionModel(int row, int column, int numOfZombies,int numOfMedic, boolean humanitySaved, double infectionRate,double healRate){
        this.row = row;
        this.column = column;
        this.numOfZombies = numOfZombies;
        this.numOfMedics = numOfMedic;
        this.humanitySaved = humanitySaved;
        this.infectionRate = infectionRate;
        this.healRate = healRate;
        grid = new Entity[row][column];
        intiliazeEntities();
    }
    //Helper method that creates the grid according to specifications
    private void intiliazeEntities(){
        List<Entity> types = new ArrayList<>();

        //For loop to add exact number of medics and zombies
        for (int i = 0; i < numOfZombies; i++) { types.add(new Zombie()); }
        for (int j = 0; j < numOfMedics; j++) { types.add(new Medic()); }

        //Remaining cells are humans
        int remainingNumOfHumans = (row * column) - (numOfZombies + numOfMedics);
        for (int k = 0; k < remainingNumOfHumans; k++) { types.add(new Human()); }

        //Use collection interface to shuffle grid randomly
        Collections.shuffle(types);

        //for loop to create grid
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++){
                grid[i][j] = types.get(index);
                index++;
            }
        }
    }
    //Update all changes that happen on the grid
    //objects are changed but color is changed in ZombieGUI class
    // BECAUSE OF ORDER OF METHOD CALLS INFECT TAKES PRIORITY
    // I.E zombie will change grid first before humans
    public void updateTick() {
        infect();
        heal();
    }
    //Checks if square is zombie and able to infect
    //calls on performAction method polymorphism
    private void infect(){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++) {
                Entity entity = grid[i][j];
                if (!entity.isHuman()) {
                     entity.performAction(grid, infectionRate, i, j);
                }
            }
        }
    }
    //Check if square is zombie and able to heal
    private void heal() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Entity entity = grid[i][j];
                if (entity instanceof Medic) {    // TODO: need action for humans side
                    entity.performAction(grid,healRate, i, j);
                }
            }
        }
    }
    //GETTER & SETTERS
    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    public Entity[][] getGrid() { return grid; }
    //debug method
    public void printEntity() {
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++) {
                Entity entity = grid[i][j];
                System.out.print("[" + entity.toString() + "]");
                if (j < column - 1) System.out.print(",");
            }
            System.out.println(); // new row
        }
    }
}
