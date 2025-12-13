import java.util.*;

public class Colony extends Environment{

    private String colony_name;
    private int[][] area = new int[49][2];
    private int x;
    private int y;
    private int[] intruder_coord = new int[2];
    private boolean danger;


    // Track accumulated food for spawning
    private int food_bank = 0; 
    private int totalFood = 0;

    // This will be used to move to soldier for global variable
    public int centerX;
    public int centerY;
    public int move_count = 0;

    public void change_move(int a){
        this.move_count = a;
    }

    public int centerX(){
        return centerX;
    }

    public int centerY(){
        return centerY;
    }


    public Colony(){

    }

    // Constructor for the colony
    public Colony(int x, int y, String colony_name){
        int start = 0;
        for(int i=(x-3);i<=(x+3);i++){
            for(int j=(y-3);j<=(y+3);j++){
                area[start] = new int[]{i,j}; 
                start++;
        }

        }
        this.x = x;
        this.y = y;
        this.centerX = x; // Stores the center coordinates of the colony
        this.centerY = y;
        this.colony_name = colony_name;
        this.danger = false;  // Default value
    }

    // Another constructor for the colony
    public Colony(int cx, int cy) {
        this.centerX = cx;
        this.centerY = cy;
    }

    public int[][] get_ColonyArea(){
        return area;
    }

    public int get_Colony_CenterX(){
        return x;
    }

    public int get_Colony_CenterY(){
        return y;
    }

    // Retuns the team name of the colony
    public String get_team(){
        return colony_name;
    }

    public void inc_colony(){

    }

    public void dec_colony(){

    }

    // This function will check for intruders in the colony area
    public void check_intruder(String team){
        ArrayList<int[]> intruders = new ArrayList<>();


        String[][] board = GameBoard.board;

        int startY = y - 3;
        int startX = x - 3;
        for(int i =0; i < 7; i ++){
            for(int j = 0; j < 7; j++){
                String enemy = board[startX+i][startY+j];

                if(team.equals("Blue")){
                    //if not red, then for blue ants

                    if(enemy.equals("W2")){ //w2 is red worker
                        intruders.add(new int[]{startX+i,startY+j});
                    }
                    
                }

                else {
                    //if the colony is red
                    if(enemy.equals("W1")){ //w1 is blue worker
                        intruders.add(new int[]{startX+i,startY+j});
                        
                    }
                }

                //spiders and beetles are common enemies
                if(enemy.equals("Spider") || enemy.equals("Beetles")){
                    intruders.add(new int[]{startX+i,startY+j});
                }

                intruder_coord = new int[]{startX+i,startY+j};
            }
        }

        if(intruders.size() > 0){
            this.move_count = 1;
            danger = true;
            
        } else {
            this.move_count = 0;
            danger = false;
        }
    }

    // Helper functions
    public int check_queen_health(){
        return 0;
    }

    public int[] get_intruder_coord(){
        return intruder_coord;
    }

    public boolean get_Danger(){
        return danger;
    }

    // This function will return the total food in the colony
    public int getTotalFood(){
        return totalFood;
    }

    // This function will add food to the colony
    public String addFood(int value) {
        totalFood += value;
        food_bank += value;
        
        if (food_bank >= 5) {
            food_bank -= 5; // Deduct cost
            
            // 90% chance Worker, 10% Soldier
            double chance = Math.random();
            if (chance < 0.90) {
                return "Worker";
            } else {
                return "Soldier";
            }
        }
        return null; // Not enough food yet
    }

}