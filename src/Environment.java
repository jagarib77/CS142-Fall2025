import com.sun.tools.javac.Main;
import javafx.scene.paint.Color;
import java.util.List;



public class Environment{

    private int foodCount;
    private int width;
    private int height;

    
    // Accessing the game board(which stores the string value of the game)
    public static GameBoard board;

    // Stores the actual elements in the game.
    public static Environment[][] classBoard;


    // Access noise map
    private GrassGenerator noise;

    //Make 2 colonies
    private static Colony col1;
    private static Colony col2;

    //food spawner
    public FoodSpawner foodSpawner;


    public Environment(){
        // This constructor is just for others to access it's function
    }

    public Environment(int x, int y){
        this.width = x;
        this.height = y;
    }


    // Helper constructor for lower classes
    public Environment(int x, int y, boolean includeSecondColony){
        generateColony1();
        if(MainLogic.includeSecondColony){
            generateColony2();
        } 
    }

    // Constructor generates the world with each element in it using the game board
    public Environment(int width, int height, boolean includePredator, boolean includeSecondColony, int foodCount){
        this.foodCount = foodCount;
        this.width = width;
        this.height = height;

        classBoard = new Environment[width][height];

        noise = new GrassGenerator(width, height);
        board = new GameBoard(width, height);
        generateColony1(); // Generates first colony

        // Generate colony 2 if enabled
        if(MainLogic.includeSecondColony){
            generateColony2();
        }

        // Generates predators if enabled
        if(MainLogic.includePredator){
            board.spawnPredators();
        }

        // Initialize food spawner
        foodSpawner = new FoodSpawner(width, height);

        // Add Col 1 area to exclusion
        int[][] c1Area = col1.get_ColonyArea();
        for(int[] pos : c1Area) { foodSpawner.addColonyPosition(pos[0], pos[1]); }
        
        // Add Col 2 area to exclusion
        if(MainLogic.includeSecondColony){
            int[][] c2Area = col2.get_ColonyArea();
            for(int[] pos : c2Area) { foodSpawner.addColonyPosition(pos[0], pos[1]); }
        }

        List<Food> initialFoods = foodSpawner.spawnMultipleFood(foodCount);

        // Assign food values to the GameBoard
        for(Food f : initialFoods) {
           GameBoard.board[f.getX()][f.getY()] = ("F");
        }

        // Generates the objects based on GameBoard
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                // Accessing the game board cell
                String cell = board.getCell(i, j);

                if(cell.equals("Spider")){
                    classBoard[i][j] = new Spider(i, j); 
                } else if(cell.equals("C1")){
                    classBoard[i][j] = new ColonyGrass(false);
                } else if(cell.equals("C2")){
                    classBoard[i][j] = new ColonyGrass(true);
                } else if(cell.equals("W1")){
                    classBoard[i][j] = new Worker(i, j, "Blue");
                } else if(cell.equals("W2")){
                    classBoard[i][j] = new Worker(i, j, "Red");
                } else if(cell.equals("Q1")){
                    classBoard[i][j] = new Queen(i, j, "Blue");
                } else if(cell.equals("Q2")){
                    classBoard[i][j] = new Queen(i, j, "Red");
                } else if(cell.equals("So1")){
                    classBoard[i][j] = new Soldier(i, j, "Blue");
                } else if(cell.equals("So2")){
                    classBoard[i][j] = new Soldier(i, j, "Red");
                } else if(cell.equals("Beetles")){
                    classBoard[i][j] = new Beetles(i, j);
                } 
                else if (cell.equals("F")) {
                    // Do nothing, as food is already handled
                }
                else{
                    // Generate grass based on noise level
                    classBoard[i][j] = new Grass(noise.getNoiseLevel(i, j));
                }
            }
        }
       
    }

    // Returns the string represntation of the value on the cell
    public String getBoardCell(int x, int y){
        return board.getCell(x, y);
    }

    // Returnst the object in the specific (x,y) cell
    public Environment getClassBoard(int x, int y){
        return classBoard[x][y];
    }
    
    // Udpates the class board each frame
    public void updateClassBoardCell(int newX, int newY, Environment env, int oldX, int oldY){

        // Handles food collection
        if(board.getCell(newX, newY).equals("F")){
            collectFood(newX, newY, env.getTeam());
        }
        classBoard[newX][newY] = env;

        // Checks if the old position is in the colony area
        if (((oldX >= col1.get_Colony_CenterX() - 3) && (oldX <= col1.get_Colony_CenterX() + 3)) &&  // Compare X with CenterX
            ((oldY >= col1.get_Colony_CenterY() - 3) && (oldY <= col1.get_Colony_CenterY() + 3))) { // Compare Y with CenterY
            
            classBoard[oldX][oldY] = new ColonyGrass(false);
        } else if(MainLogic.includeSecondColony){
            if (((oldX >= col2.get_Colony_CenterX() - 3) && (oldX <= col2.get_Colony_CenterX() + 3)) && // Compare X with CenterX
                ((oldY >= col2.get_Colony_CenterY() - 3) && (oldY <= col2.get_Colony_CenterY() + 3))) { // Compare Y with CenterY
            
                classBoard[oldX][oldY] = new ColonyGrass(true);
            }
            else{
                classBoard[oldX][oldY] = new Grass(noise.getNoiseLevel(oldX, oldY));
            }
        } else {
            classBoard[oldX][oldY] = new Grass(noise.getNoiseLevel(oldX, oldY));
        }
        // Update the game board string representation
        board.updateCell(newX, newY, board.getCell(oldX, oldY), oldX, oldY);
    }
    
    // Food collection logic for each colony
    public void collectFood(int x, int y, String teamColor) {

        String cell = board.getCell(x, y);

        if (cell.startsWith("F")) {
            
            int foodVal = classBoard[x][y].getFoodValue();
            
            String unitToSpawn = null;
            Colony activeColony = null;

            // Add points to appropriate colony
            if (teamColor.equals("Blue") && col1 != null) {
                unitToSpawn = col1.addFood(foodVal);
                activeColony = col1;

            } else if (teamColor.equals("Red") && col2 != null) {
                unitToSpawn = col2.addFood(foodVal);
                activeColony = col2;
            }

            //Spawn new unit if threshold met
            if (unitToSpawn != null && activeColony != null) {
                spawnNewUnit(activeColony, unitToSpawn, teamColor);
            
            }

            // Respawn the eaten food elsewhere to keep game going
            Food newFood = foodSpawner.spawnRandomFood();
            if(newFood != null) {
                board.updateCell(newFood.getX(), newFood.getY(), "F" + newFood.getFoodValue(), newFood.getX(), newFood.getY());
                classBoard[newFood.getX()][newFood.getY()] = newFood;
            }
        }
    }

    // Logic to spawn a new unit in the colony area
    private void spawnNewUnit(Colony col, String type, String team) {
        int cx = col.get_Colony_CenterX();
        int cy = col.get_Colony_CenterY();
        String id = "";
        
        if (team.equals("Blue")) {
            id = (type.equals("Worker")) ? "W1" : "So1";
        } else {
            id = (type.equals("Worker")) ? "W2" : "So2";
        }

        // Search for empty spot in 7x7 colony area
        for (int i = cx - 3; i <= cx + 3; i++) {
            for (int j = cy - 3; j <= cy + 3; j++) {
                // Check bounds
                if (i < 0 || i >= width || j < 0 || j >= height) continue;

                // Check if spot is occupied only by ColonyGrass (C1/C2) or Grass
                String current = board.getCell(i, j);
                if (current.equals("C1") || current.equals("C2") || current.equals("G")) {
                    
                    // Update Board String
                    board.updateCell(i, j, id, i, j);
                    
                    // Update Class Object
                    if (type.equals("Worker")) {
                        
                        classBoard[i][j] = new Worker(i, j, team);
                    } else {
                        classBoard[i][j] = new Soldier(i, j, team);
                    }
                    GameBoard.board[i][j] = id;
                    return; // Spawned successfully, exit
                }
            }
        }
        System.out.println("Colony full, could not spawn unit.");
    }
    
    // The following funciton are created as base function.
    // These funciotn don't do anything but instead are overridden by the functions in the child calss
    public int[] getMove(){ return new int[] {0,0}; }
    public Color getColor(){ return Color.WHITE; }
    public void updatePostion(int x, int y){}
    public void fight(){}
    public String getTeam(){ return ""; }
    public void updateHP(int hp){}
    public int getHealth(){ return 0; }
    public int getAttack(){ return 0; }
    public int getX(){ return 0; }
    public int getY(){ return 0; }
    public String getType(){ return ""; }
    public void updateElementPositon(Environment element, int x, int y){ element.updatePostion(x, y); }
    public void spawnFood(){} 
    public void helpNeeded(){}
    public void helpNotNeeded(){}
    public boolean isHelpNeeded(){return false;}
    public void beingHelped(){}
    public void notBeingHelped(){}
    public boolean getIsBeingHelped(){return true;}
    public void setDangerAnt(boolean val, int i, int j, int antX, int antY){}
    public boolean getIsHelping(){return true;}
    public void updateHelpingStatus(boolean val){}
    public int getFoodValue() {return 0;}

    // Movement logic
    public int[] moveElements(int x, int y){
        int[] direction = new int[2];
        direction = classBoard[x][y].getMove(); // Gets move of each pixel
        if((x + direction[0] < 0) || (x + direction[0] >= classBoard.length) || (y + direction[1] < 0) || (y + direction[1] >= classBoard[0].length)){
            return Direction.Center(); // If out of bounds, return center
        }
        if(!validMove(x, y, direction)){
            return Direction.Center();
        }
        return direction;
    }

    // Function to check if the agent is near a wall
    public boolean nearWall(int currentXPosition, int currentYPosition, int[] direction){
        if((currentXPosition + direction[0] < 0) || (currentXPosition + direction[0] >= MainLogic.arrayWidth) || (currentYPosition + direction[1] < 0) || (currentYPosition + direction[1] >= MainLogic.arrayHeight)){
            return true;
        }
        return false;
    }

    // Helper function
    public void battle(Environment c){ c.fight(); }

    // Checks the neighbours of the agent, returns an array of strings
    public String[] checkNeighbours(int agentXPosition, int agentYPosition){
        String[] neighbours = new String[8];

        if(nearWall(agentXPosition, agentYPosition, new int[] {-1,1})){
            neighbours[0] = "Wall";
        } else { neighbours[0] = board.getCell(agentXPosition -1 , agentYPosition + 1);}

        if(nearWall(agentXPosition, agentYPosition, new int[] {-1,0})){
            neighbours[1] = "Wall";
        } else { neighbours[1] = board.getCell(agentXPosition -1 , agentYPosition);}

        if(nearWall(agentXPosition, agentYPosition, new int[] {-1,-1})){
            neighbours[2] = "Wall";
        } else { neighbours[2] = board.getCell(agentXPosition -1 , agentYPosition - 1);}

        if(nearWall(agentXPosition, agentYPosition, new int[] {0,-1})){
            neighbours[3] = "Wall";
        } else { neighbours[3] = board.getCell(agentXPosition, agentYPosition - 1);}

        if(nearWall(agentXPosition, agentYPosition, new int[] {1,-1})){
            neighbours[4] = "Wall";
        } else { neighbours[4] = board.getCell(agentXPosition + 1 , agentYPosition - 1);}

        if(nearWall(agentXPosition, agentYPosition, new int[] {1,0})){
            neighbours[5] = "Wall";
        } else { neighbours[5] = board.getCell(agentXPosition + 1, agentYPosition);}

        if(nearWall(agentXPosition, agentYPosition, new int[] {1,1})){
            neighbours[6] = "Wall";
        } else { neighbours[6] = board.getCell(agentXPosition + 1 , agentYPosition + 1);}

        if(nearWall(agentXPosition, agentYPosition, new int[] {0,1})){
            neighbours[7] = "Wall";
        } else { neighbours[7] = board.getCell(agentXPosition, agentYPosition + 1);}

        return neighbours;
    }

    // This function is used to get the coordinates of the enemy based on the position in the neighbours array
    public int[] getEnemyCoords(int x, int y, int arrayPosition){
        return switch (arrayPosition) {
            case 0 -> new int[] {x - 1, y + 1};
            case 1 -> new int[] {x - 1, y};
            case 2 -> new int[] {x - 1, y - 1};
            case 3 -> new int[] {x, y + 1};
            case 4 -> new int[] {x + 1, y - 1};
            case 5 -> new int[] {x + 1, y};
            case 6 -> new int[] {x + 1, y + 1};
            default -> new int[] {x , y + 1};
        };
    }

    // Check if the move is valid based fiven the postion it wants to move to
    public boolean validMove(int agentXPostion, int agentYPosition, int[] direction){
        String object = GameBoard.board[agentXPostion + direction[0]][agentYPosition + direction[1]];
        // Allow moving on Grass, Colony, OR Food (F)
        return (object.equals("G") || object.equals("C1")  || object.equals("C2") || object.startsWith("F"));
    }

    // Generates the first colony
    public void generateColony1(){

        int centerX = (int)((Math.random() * ((MainLogic.arrayWidth - 1) / 2 - 6)) + 3); // Randomly generate x position
        int centerY = (int)((Math.random() * (MainLogic.arrayHeight - 6)) + 3); // Randomly generate y position
        board.generateColony(false, centerY, centerX);
        col1 = new Colony(centerX,centerY, "Blue");
    }

    // Generates the second colony
    public void generateColony2(){

        int centerX = (width-4) - ((int)(Math.random() * (MainLogic.arrayWidth  / 2 - 7))); // Randomly generate x position
        int centerY = (int)((Math.random() * (MainLogic.arrayHeight  - 6)) + 3); // Randomly generate y position
        board.generateColony(true, centerY, centerX);
        col2 = new Colony(centerX,centerY, "Red");
    }

    // Helper function to get the colony object based on the team
    public static Colony getColony(String team) {
        if (team.equals("Blue")) {
            return col1;
        }
        else {
            return col2;
        }
    }

    // Calls the check intruder function for each colony
    public void check_intruder_team(){
        col1.check_intruder("Blue");
        if(MainLogic.includeSecondColony){
            col2.check_intruder("Red");
        }
    }


    // Records food deposited by a worker at the queen.
    public int getFoodDeposit(int colonyNumber) {
        if(colonyNumber == 1){
            return col1.getTotalFood();
        } else if(MainLogic.includeSecondColony){
            return col2.getTotalFood();
        }
        return 0;
    }


    // Check is game is over based on queens health
    public boolean gameOver(){
        if(classBoard[col1.get_Colony_CenterX()][col1.get_Colony_CenterY()].getHealth() <= 0){
            System.out.println("Game Over, Red wins!");
            return true;
        } else if(MainLogic.includeSecondColony){
            if(classBoard[col2.get_Colony_CenterX()][col2.get_Colony_CenterY()].getHealth() <= 0){
                System.out.println("Game Over, Blue wins!");
            return true;
            }   
        }
        return false;
    }
}