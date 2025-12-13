import javafx.scene.paint.Color;

public class Food extends Environment {
    
    private int foodValue; 
    private int x;
    private int y;
    private boolean isCollected = false;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
        
        int randomVal = (int)(Math.random() * 10); // 0-9
        
        if (randomVal < 7) {
            foodValue = 1; // 70% chance (Yellow)
        } else if (randomVal < 9) {
            foodValue = 2; // 20% chance (Orange)
        } else {
            foodValue = 3; // 10% chance (Red)
        }
        
    }
    
    public Food(int x, int y, int forcedValue) {
        this.x = x;
        this.y = y;
        this.foodValue = forcedValue;
    }


    // Returns the Food Value
    public int getFoodValue() {
        return foodValue;
    }

    // Returns the Color depending on the food value
    @Override
    public Color getColor() {
        if (foodValue == 1) {
            return Color.YELLOW;
        } else if (foodValue == 2) {
            return Color.ORANGE;
        } else {
            return Color.RED;
        }
    }

    // Get position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Mark as collected
    public void collect() {
        isCollected = true;
    }

    public boolean isCollected() {
        return isCollected;
    }

    @Override
    public String toString() {
        return "Food{" + "value=" + foodValue + ", x=" + x + ", y=" + y + '}';
    }
}