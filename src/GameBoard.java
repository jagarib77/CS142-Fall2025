// This class stores everything in the gamebaord
public class GameBoard {


    // Stores every point in the simulation as a string
    public static String[][] board;

    // Constructoro initialize the gameboard with all cells as "G" (grass)
    public GameBoard(int width, int height){
        System.out.println(width + " " + height);
        board = new String[width][height];
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                board[i][j] = "G";
            }
        }
    }

    // Return the stirng value on the current cell
    public String getCell(int x, int y){
        return board[x][y];
    }

    // Sets the value of the current cell
    public void setCell(int x, int y, String value){
        board[x][y] = value;
    }

    // Updates the value of the cell each frame
    public void updateCell(int newX, int newY, String value, int oldX, int oldY){
        board[newX][newY] = value;
        board[oldX][oldY] = "G";
    }

    // Generates the string representation of the colony
    public void generateColony(boolean redColony, int x, int y){
        int startY = x - 3;
        int startX = y - 3;
        for(int i =0; i < 7; i ++){
            for(int j = 0; j < 7; j++){
                if(!redColony){
                    
                    board[startX+i][startY+j] = "C1";
                    continue;
                }
                board[startX+i][startY+j] = "C2";
            }
        }

        if(!redColony){
            // If not red colony

            // THIS IS FOR BLUE COLONY
            board[y][x] = "Q1";

            // Spawning the workers
            board[y-2][x-2] = "W1";
            board[y-2][x] = "W1";
            board[y+2][x-2] = "W1";
            board[y][x-2] = "W1";
            board[y+2][x] = "W1";

            board[y][x+2] = "So1"; // Spawning the soldier
        }
        else{

            // THIS IS FOR RED COLONY
            board[y][x] = "Q2"; 

            //spawning the workers
            board[y-2][x-2] = "W2";
            board[y-2][x] = "W2";
            board[y+2][x-2] = "W2";
            board[y][x-2] = "W2";
            board[y+2][x] = "W2";

            board[y][x+2] = "So2"; // Spawning the soldier
        }
    }

    // Spawns predators(the string represenations) on the game board
    public void spawnPredators(){
        int width = board.length;
        int height = board[0].length;
         
        int predatorCount = GUI.predatorCount; // Max number of predators
        // Spawning spiders and beetles at random locations
        while(predatorCount > 0){
            int x = (int)(Math.random() * width);
            int y = (int)(Math.random() * height);
            if(board[x][y].equals("G")){
                int spawn = (int)(Math.random() * 2);
                // Randomly spawning either a beetle or a spider
                if(spawn == 0)
                    board[x][y] = "Beetles";
                else{
                    board[x][y] = "Spider";
                }
                predatorCount--;
            }
        }
    }
}