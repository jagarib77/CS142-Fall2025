import java.lang.reflect.Array;
import javafx.scene.paint.Color;
import java.util.Arrays;

public class Soldier extends Queen{

    private int x;
    private int y;
    private int moves;

    private int moveCounter = 0;

    // Global Variables
    private int health =20000;
    private int attack;
    private String team;
    private boolean hasGuardedNW = false;

    // Danger function variables
    private boolean dangerAnt = false;
    private int antX = -1;
    private int antY = -1;
    private int enemyAntX = -1;
    private int enemyAntY = -1;

    private boolean isHelping = false;

    // Center of colony location
    private int cx;
    private int cy;


    public Soldier(){
        super();
    }


    // Constructor, accepts team and initial position
    public Soldier(int x, int y, String team){
        
        this.x = x;
        this.y = y;
        this.team = team;
        this.moves = 0;

        this.cx = 0;
        this.cy = 0;    
    }  

    // This function is called if a ant is in danger
    public void setDangerAnt(boolean val, int antX, int antY, int enemyAntX, int enemyAntY){
        this.dangerAnt = val;
        this.antX = antX;
        this.antY = antY;
        this.enemyAntX = enemyAntX;
        this.enemyAntY = enemyAntY;
    }

    // Functions to get status
    public boolean getIsHelping(){
        return isHelping;
    }

    public void updateHelpingStatus(boolean val){
        this.isHelping = val;
    }


    // Sets new position
    public void setAntPosition(int x, int y){
        this.antX = x;
        this.antY = y;
    }

    // Updates position
    @Override
    public void updatePostion(int x, int y){
        this.x = x;
        this.y = y;
    }

    // More funtions to get ant status
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHealth(){
        return health;
    }

    public void updateHP(int hp){
        this.health = hp;
    }

    public String getTeam(){
        return this.team;
    }

    @Override
    public String getType(){
        return this.team.equals("Blue") ? "So1" : "So2";
    }

    // Function to get the attack of the soldier ant
    public int getAttack(){
        int dmg = (int)(Math.random() * (41)) + 160; // Random attac
        int crit = (int)(Math.random() * (10)) + 1;
        if(crit == 1){ //10% percent change critical hit
            dmg+=25;
        }    
        return dmg;
    }

    @Override // Move logic
    public int[] getMove(){
        Colony mycol = Environment.getColony(team);
        boolean danger = mycol.get_Danger();

        this.cx = mycol.centerX;
        this.cy = mycol.centerY;

        // If queen is in danger
        if(danger == true && hasGuardedNW == false) {

            int targetX = cx -1;
            int targetY = cy -1;

            // If move count is 1, then there is an intruder

            while(x!= targetX){
                // If the soldier is on left of the queen
                if(x < targetX){
                    return Direction.East();
                }
                else {
                    return Direction.West();
                }
            }

            // Now if the x is already the same, we will have to adjust the y levels

            while(y!= targetY){
                // If the soldier is on left of intruder
                if(y < targetY){
                    return Direction.South();
                }
                else {
                    return Direction.North();
                }
            }

            // Reached target
            if(x == targetX && y == targetY){
                hasGuardedNW = true;
            }

        }


        // If an ant is in danger, move towards it
        if(dangerAnt){
            // Marked as helping
            isHelping = true; 

            // Calculate distance to target
            int rise = enemyAntY - y;
            int run = enemyAntX - x;
            
            // Check if we have arrived near the target(the enemy ant or predator)
            if (Math.abs(rise) <= 1 && Math.abs(run) <= 1) {
                
                // Arrived at target: Reset flags
                this.dangerAnt = false;
                this.isHelping = false;
                
                // Unmark the worker so they can be helped again if needed
                if(Environment.classBoard[antX][antY] != null) {
                    Environment.classBoard[antX][antY].notBeingHelped();
                }
                
                return Direction.Center(); // Stop 
            }

            // Standard movement towards target
            if(Math.abs(rise) > Math.abs(run)){
                // Can move randomly to avoid getting stuck
                int randomMove = (int)(Math.random() * (10)) + 1;
                if(randomMove ==1){
                    int random = (int)(Math.random() * (2)) + 1; //1 or 2
                    if(random == 1){
                        return Direction.East();
                    } else {
                        return Direction.West();
                    }
                }

                // Move in y direction
                if(rise > 0){
                    return Direction.South();
                } else {
                    return Direction.North();
                }
            } else {
                // Can move randomly to avoid getting stuck
                int randomMove = (int)(Math.random() * (10)) + 1;
                if(randomMove ==1){
                    int random = (int)(Math.random() * (2)) + 1; //1 or 2
                    if(random == 1){
                        return Direction.South();
                    } else {
                        return Direction.North();
                    }
                }

                // Move in x direction
                if(run > 0){
                    return Direction.East();
                } else {
                    return Direction.West();
                }
            }
        }
        
        // Solider is free to help other ants
        isHelping = false; 

        // Logic to the colony once finished helping
        int areaX = cx - 8;
        int areaY = cy - 8;
        if(this.x < areaX || this.x > areaX + 16 || this.y < areaY || this.y > areaY + 16){
            if(this.x < cx){
                return Direction.East();
            } else if(this.x > cx){
                return Direction.West();
            } else if(this.y < cy){
                return Direction.South();
            } else if(this.y > cy){
                return Direction.North();
            }
        }
    
    // Normal movement =
        if(danger == false){
                hasGuardedNW = false;
            }        
            if(nearWall(x, y, Direction.North(),danger)){
                return Direction.South();
            } 
            else if (nearWall(x, y, Direction.South(),danger)){
                return Direction.North();
            }
            else if (nearWall(x, y, Direction.West(),danger)){
                return Direction.East();
            }
            else if (nearWall(x, y, Direction.East(),danger)){
                return Direction.West();
            }
            else { // If its not near a wall, move randomly

                int rng = (int)(Math.random() * (4)) + 1; //1 to 4
                if(rng == 1){
                    return Direction.North();
                }
                else if(rng == 2){
                    return Direction.South();
                }
                else if(rng == 3){
                    return Direction.East();
                }
                else{
                    return Direction.West();
                }
        }
}

    // Custom near wall funciton for soldier
    public boolean nearWall(int currentXPosition, int currentYPosition, int[] direction, boolean danger){
        int startX = 0;
        int endX = 0;
        int startY = 0;
        int endY = 0;


        if(danger == false){
            // If danger is false then it will occupy larger area
        // X boundaries
            startX = cx-3-5; 
            endX = cx+3+5;

        // Y boundaries
            startY = cy-3-5;
            endY = cy+3+5;

        } else {
            // If danger is true then it will move smaller area around queen

            // X boundaries
            startX = cx-3; 
            endX = cx+3;

            // Y boundaries
            startY = cy-3;
            endY = cy+3;
        }

        if((currentXPosition + direction[0] < startX) || 
        (currentXPosition + direction[0] > endX) || 
        (currentYPosition + direction[1] < startY) || 
        (currentYPosition + direction[1] > endY)){
            return true;
        }
        return false;
    }

    @Override // Returns color depending on the colony
    public Color getColor(){
        if(team.equals("Blue")){
            return Color.rgb(157, 83, 247);
        }
        return Color.rgb(226, 125, 8);

    }

}