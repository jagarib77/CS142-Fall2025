import javafx.scene.paint.Color;
import java.util.Arrays;

public class Spider extends Predators{
    
    private int xPos;
    private int yPos;

    // Variables for movement
    private final int[][] directions = {Direction.North(), Direction.South(), Direction.East(), Direction.West()};
    private boolean directionIsNorth = true;
    private boolean directionIsEast = true;
    private int moveCounter = 0;

    // Global variables
    private int health =30000;
    private final int attack;

    // Constructor accepts inital postion
    public Spider(int x, int y){
        super(MainLogic.arrayWidth, MainLogic.arrayHeight);
        this.xPos = x;
        this.yPos = y;
        attack = (int)((Math.random() * 41)) + 160;
    }

    @Override // update position
    public void updatePostion(int x, int y){
        this.xPos = x;
        this.yPos = y;
    }

    // Funciton to return status of the spider
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
        return "Spider";
    }

    @Override // Spider is in predator team
    public String getTeam(){
        return "Predator";
    }

    @Override // Move logic
    public int[] getMove(){

        // Spider moves in a L shpae
        if(moveCounter < 3){
            moveCounter++;
            if(nearWall(this.xPos, this.yPos, helperGetMove())){
                directionIsNorth = !directionIsNorth;
            }
            if(directionIsNorth){
                return directions[0];
            }
            return directions[1];
        }
        // Bouce if near the wall
        moveCounter = 4;
        if(nearWall(this.xPos, this.yPos, helperGetMove())){
            directionIsEast = !directionIsEast;
        }
        moveCounter = 0;
        if(directionIsEast){
            return directions[2];
        }
        return directions[3];
       
    }

    // Helper function for movement
    public int[] helperGetMove(){
        if(moveCounter < 3){
            if(directionIsNorth){
                return Direction.North();
            }
            return Direction.South();
        }
        if(directionIsEast){
            return Direction.East();
        }
        return Direction.West();
    }
}