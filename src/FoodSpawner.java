import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodSpawner {

    // Variables
    private int arrayWidth;
    private int arrayHeight;
    private List<int[]> colonyPositions; 
    private Random random;
    
    // Constructor accepts size of the board
    public FoodSpawner(int arrayWidth, int arrayHeight) {
        this.arrayWidth = arrayWidth;
        this.arrayHeight = arrayHeight;
        this.colonyPositions = new ArrayList<>();
        this.random = new Random();
    }
    
    public void addColonyPosition(int x, int y) {
        colonyPositions.add(new int[]{x, y});
    }
    
    // Checks if the position is valid for food spawning
    public boolean isValidFoodPosition(int x, int y) {
        for (int[] colonyPos : colonyPositions) {
            int distanceX = Math.abs(colonyPos[0] - x);
            int distanceY = Math.abs(colonyPos[1] - y);
        if (distanceX <= 4 && distanceY <= 4) { 
            return false;
        }
    }
    return true;
}
    
    // Spawn food at random valid position
    public Food spawnRandomFood() {

        // Limit the number of attempts to avoid infinite loops
        int maxAttempts = 25; 
        int attempts = 0;
        
        while (attempts < maxAttempts) {
            int x = random.nextInt(arrayWidth);
            int y = random.nextInt(arrayHeight);
            
            if (isValidFoodPosition(x, y)) {
                GameBoard.board[x][y] = "F";
                Food food = new Food(x, y);
                Environment.classBoard[x][y] = food; 
                return food;
            }
            
            attempts++;
        }
        
        System.out.println("Could not find valid food position after " + maxAttempts + " attempts");
        return null;
    }
    
    // Spawn multiple food items
    public List<Food> spawnMultipleFood(int count) {
        List<Food> foodList = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            Food food = spawnRandomFood();
            if (food != null) {
                foodList.add(food);
            }
        }
        
        return foodList;
    }
}
