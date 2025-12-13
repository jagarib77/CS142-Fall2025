import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.paint.Color;

public class Worker extends Queen{
    
    // Movement variables
    private int x;
    private int y;
    private String[] dir = {"N","W","S","E"};
    private boolean moveNorth = true;
    private boolean moveEast = true;
    private String team;

    // Help variables
    private boolean needsHelp = false;
    private boolean isBeingHelped = false;

    // Variables
    private int health = (int)(Math.random() * (4100)) + 8000; // Random health
    private int attack = 30;


    // Constructor accepts initial position and team
    public Worker(int x, int y, String team){
        this.x = x;
        this.y = y;
        this.team = team;

        shuffle(); // Randomizes the direction of movement for each ant
    }

    public void shuffle(){
        // Shuffle method to randomzie movement
        List<String> list = Arrays.asList(dir);
        Collections.shuffle(list);
        for(int i=0;i<dir.length;i++){
            dir[i] = list.get(i);
        }

    }

    @Override
    public void updatePostion(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHealth(){
        return health;
    }

    public int getAttack(){
        return this.attack;
    }

    public String getType(){
        // Returns W1 for Blue team, W2 for any other team (Red)
        return this.team.equals("Blue") ? "W1" : "W2";
    }

    @Override // Movement logic
    public int[] getMove(){
        if(nearWall(x, y, Direction.North())){
            shuffle();
        } 
        else if (nearWall(x, y, Direction.South())){
            shuffle();
        }
        else if (nearWall(x, y, Direction.West())){
            shuffle();
        }
        else if (nearWall(x, y, Direction.East())){
            shuffle();
        }

        String d="";
        // Randomizes the move, but it's more likely to move in one direction
        int rng = (int)(Math.random() * (10)) + 1;
        if(rng<=5){
            d = dir[0];
        } else if(rng <=7){
            d = dir[1];
        } else if(rng <=9){
            d = dir[2];
        } else {
            d = dir[3];
        }

        // Compared if d is N/S/E/W
        if(d.equals("N")){
            return Direction.North();
        } 
        else if(d.equals("S")){
            return Direction.South();
        }
        else if(d.equals("E")){
            return Direction.East();
        }
        else {
            return Direction.West();
        }

        
    }

    // Function to get status on the ant(wheather it needs help or not)
    public void helpNeeded(){
        this.needsHelp = true;
    }

    public void helpNotNeeded(){
        this.needsHelp = false;
    }

    public boolean isHelpNeeded(){
        return this.needsHelp;
    }

    public void beingHelped(){
        this.isBeingHelped = true;
    }

    public void notBeingHelped(){
        this.isBeingHelped = false;
    }

    public boolean getIsBeingHelped(){
        return this.isBeingHelped;
    }

    @Override // Ant is black
    public Color getColor(){
        return Color.rgb(0, 0, 0);
    }

    public String toString(){
        if(super.get_team().equals("Red")){
            return "Wr";
        } 
        else{
            return "Wb";
        }
    }

    public String getTeam(){
        return team;
    }

    // Updates ant hp
    public void updateHP(int hp){
        this.health = hp;
    }
}