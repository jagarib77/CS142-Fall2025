import javafx.scene.paint.Color;

public class Queen extends Colony {
    
    private int x;
    private int y;

    // Global Varibales
    private int health = 200000;
    private final int attack = 0; // No damage
    private String team; // This holds "Red" or "Blue"

    public Queen(){
        // Empty constructor
    }

    // Main constructor
    public Queen(int x, int y, String team){
        this.x = x;
        this.y = y;
        this.team = team;
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
        return attack;
    }

    public void updateHP(int hp){
        this.health = hp;
    }

    public int[] getMove(){
        return Direction.Center();
    }

    public String getTeam(){
        return this.team;
    }

    @Override
    public Color getColor(){
        return Color.PINK; // Returns color of the queen(same for both colony)
    }

    @Override // Returns type depending on the team
    public String getType(){
        if (this.team != null) {
            if (this.team.equals("Blue")) {
                return "Q1";
            } else if (this.team.equals("Red")) {
                return "Q2";
            }
        }
        return "Queen"; 
    }
}