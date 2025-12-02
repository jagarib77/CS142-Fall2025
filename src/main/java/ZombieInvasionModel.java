import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZombieInvasionModel {
    private Entity[][] grid;
    //Size of grid
    private int row;
    private int column;
    //Used to specify where Case Zero can Start
    private int xCordinate;
    private int yCordinate;
    //infection rate/heal rate / otherCustomization
    private double infectionRate;
    private double healRate;
    //checks all possible sorrounding squares with
    //each direction.
    private static final int[][] DIRECTIONS = {
            {-1, 0},  {1, 0},   {0, -1}, {0, 1},
            {-1, 1},  {-1, -1}, {1, 1},  {1, -1}
    };
    //Default values for # of zombies and medics and location
    private int numOfZombies;
    private int numOfMedics;

    //overloaded constructor creats Entity[][] grid
    // with specified rows and column
    //SHOULD BE SAME SIZE AS DIMESNIONS SET IN SETTINGS
    public ZombieInvasionModel(int row, int column) {
        this.row = row;
        this.column = column;
        grid = new Entity[row][column];
    }
    //Overloaded constructor that creates Entity[][]grid
    //specifies how many humans, zombies, medic, ect.
    //And specifies if ChosenOne is available option to put in grid
    public ZombieInvasionModel(int row, int column, int numOfZombies,int numOfMedic) {
        this(row, column);
        intiliazeEntities();
    }
    //Overloaded constructor that creates Entity[][]grid
    //Specifies all of the above and WHERE CASE ZERO IS
    //;where the first zombie starts in simulation
    public ZombieInvasionModel(int row, int column, int numOfZombies,int numOfMedic,
                               boolean humanitySaved){
        this(row,column,numOfZombies,numOfMedic);
        
    }
    //OverLoaded constructor that creates Entity[][]grid
    //specifices infection rate, medic rate, ect., if chosen one allowed
    public ZombieInvasionModel(int row, int column, int numOfZombies,int numOfMedic,
                               boolean humanitySaved, double infectionRate, double healRate){
        this(row,column,numOfZombies,numOfMedic, humanitySaved);
    }

    public Entity[][] getGrid() { return grid; }
    //Update all changes that happen on the grid
    //objects are changed but color is changed in ZombieGUI class
    public static void updateTick() {
        //zombie entity becomes human?
        //if trigger/flag call infect
        //if trigger/flag call heal
        //if trigger/flag call ability
        //
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

    //Checks if square is zombie and able to infect
    //calls on infectNeighbors method
    private void infect(){

        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++) {
                Entity entity = grid[row][column];
                //if (entity.isZombie()) {    // TODO: NEED BOOLEAN METHOD TO CHECK IF ZOMBIE
                    //call helpermethod that changes
                //}
            }
        }
    }
    //helper methods that CHANGE THE ACTUAL GRID OBJECT
    //change happens here
    private void infectNeighbors(){
        //if infect change this cordinate to be zombie IF ITS NOT ZOMBIE
        int[][] possibleAttack = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1,1}, {-1,-1}, {1,1}, {1,-1}
        };

        //if entity[x][y].isHuman()
        //use infectionRate(
        //create new Zombie at those cordinates)

    }
    private void heal(){
    }
    private void chosenOne(){
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
    //create print entity here
}
//This class handles:
//
//The Entity[][] grid
//
//Infecting neighbors
//
//Medics healing zombies
//
//Moving entities (if you have movement)
//
//Any rules of the simulation