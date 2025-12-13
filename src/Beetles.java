import javafx.scene.paint.Color;
import java.util.Arrays;

public class Beetles extends Predators{
    
    // Variables 
    private int xPos;
    private int yPos;


    private final int[][] directions = {
        {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
        {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
    };
    private int directionIndex = 0;

    private int moveCounter = 0;

    private int health;
    private final int attack = 50;

    // Constructor accepts position and sets health
    public Beetles(int x, int y){
        super(MainLogic.arrayWidth, MainLogic.arrayHeight);
        this.xPos = x;
        this.yPos = y;
        this.health = (int)((Math.random() * 40100)) + 80000; // 800-1200 health
    }

    @Override
    public void updatePostion(int x, int y){
        this.xPos = x;
        this.yPos = y;

    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    public int getHealth(){
        return health;
    }

    public int getAttack(){
        return attack;
    }

    public void updateHP(int hp){
        this.health = hp;
    }

    @Override
    public String getType(){
        return "Beetles";
    }

    // Returns the move direction of the beetle
    @Override
    public int[] getMove(){
        if (moveCounter < 1) {
            moveCounter++;
            int[] move = directions[directionIndex];
            
            if (nearWall(this.xPos, this.yPos, move)) {
                directionIndex = (directionIndex + 1) % directions.length;
                move = directions[directionIndex];
            }
            
            return move;
        }
        moveCounter = 0;
        directionIndex = (directionIndex + 1) % directions.length; 
        return new int[] {0, 0};
    }
    // Beetles in in team predators
    @Override
    public String getTeam(){
        return "Predator";
    }

    // Returns color of beetle
    @Override
    public Color getColor(){
        return Color.rgb(116, 252, 188);
    }
}