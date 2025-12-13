import javafx.scene.paint.Color; 

// Only run this class once!
public class MainLogic {

    private int width;
    private int height;
    private Environment arena;
    private int cellSize;
    public static int foodCount;

    //Array Dimension
    public static int arrayWidth;
    public static int arrayHeight;
    
    public static boolean includePredator;
    public static boolean includeSecondColony;

    // Tracks KILLS (agents killed by this group)
    private int col1Kills = 0;
    private int col2Kills = 0;
    private int spiderKills = 0;
    private int beetlesKills = 0;
    
    // Tracks FOOD COLLECTED
    private int col1FoodCollected = 0;
    private int col2FoodCollected = 0;
    
    // Tracks DEATHS (agents belonging to this group that were killed)
    private int col1Deaths = 0;
    private int col2Deaths = 0;
    private int spiderDeaths = 0;
    private int beetlesDeaths = 0;
    // =========================================================

    // Dimensions
    public MainLogic(int width, int height, int cellSize, boolean includePredator, boolean includeSecondColony, int foodCount) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        MainLogic.includePredator = includePredator;
        MainLogic.includeSecondColony = includeSecondColony;
        arrayWidth = width / cellSize;
        arrayHeight = height / cellSize;
        this.foodCount = foodCount;
        arena = new Environment(arrayWidth, arrayHeight, includePredator, includeSecondColony, foodCount);
    }


    // Updates the simulation
    public void updateSimulation() {
        // ... (code to move your ants)
        boolean[][] hasMoved = new boolean[arrayWidth][arrayHeight];

        for(int i=0 ; i< arrayWidth; i ++){
            for(int j= 0; j< arrayHeight; j++){
                if(hasMoved[i][j]){
                    continue;
                }
                boolean fought = false;
                String[] neighbours = arena.checkNeighbours(i, j);

                for(int k = 0; k < 8; k++){
                    String s = neighbours[k];
                    
                    // Initial check for null or static object (G, C1, C2, Wall, F)
                    if(s != null && !(s.equals("G")) && !(s.equals("C1")) && !(s.equals("C2")) && !(s.equals("Wall")) && !(s.equals("F"))){
                        int[] enemy = arena.getEnemyCoords(i, j, k);
                        
                        // Check if the neighbor is a true enemy
                        if(CheckEnemy.isEnemy(i, j, enemy)){

                            Environment agent1 = arena.getClassBoard(i, j);
                            Environment agent2 = arena.getClassBoard(enemy[0], enemy[1]);
                            
                            // *** ADDED NULL CHECK TO PREVENT CRASHING HERE ***
                            // Ensure both objects exist before starting the battle
                            if (agent1 == null || agent2 == null) {
                                continue; 
                            }
                            
                            Fight.battle(agent1, agent2);

                            // Check if Agent 1 was killed
                            if(agent1.getHealth() <= 0){
                                // RECORD KILL: agent1 was killed by agent2
                                recordKill(agent2.getType());
                                // RECORD DEATH: agent1 was killed
                                recordDeath(agent1.getType());
                                arena.updateClassBoardCell(agent1.getX(), agent1.getY(), new ColonyGrass(true), agent1.getX(), agent1.getY());
                            }
                            // Check if Agent 2 was killed
                            if(agent2.getHealth() <= 0){
                                // RECORD KILL: agent2 was killed by agent1
                                recordKill(agent1.getType());
                                // RECORD DEATH: agent2 was killed
                                recordDeath(agent2.getType());
                                arena.updateClassBoardCell(agent2.getX(), agent2.getY(), new ColonyGrass(true), agent2.getX(), agent2.getY());
                            }
                            fought = true;
                        }
                    }
                }

                if(fought){
                    continue;
                }
                int[] movement = arena.moveElements(i, j);
                if(movement[1] + movement[0] == 0){
                    continue;
                }
                
                arena.updateElementPositon(arena.getClassBoard(i,j), i + movement[0], j + movement[1]);
                arena.updateClassBoardCell(i + movement[0], j + movement[1], arena.getClassBoard(i, j), i, j);

                hasMoved[i+movement[0]][j+movement[1]] = true;
            }
        }
        arena.check_intruder_team();
    }
    
    // Counts the number of critters of a specific type on the board.
    public int countCritters(String type) {
        int count = 0;
        for (int i = 0; i < arrayWidth; i++) {
            for (int j = 0; j < arrayHeight; j++) {
                // Defensive check: ensure the cell object isn't null before calling getType()
                if (arena.getClassBoard(i, j) != null && arena.getClassBoard(i, j).getType().equals(type)) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean gameOver() {
        return arena.gameOver();
    }

    /**
     * Records a kill based on the killer's type string. (Tracks kills *made*.)
     */
    public void recordKill(String killerType) {
        if (killerType == null) return; // Defensive check
        
        if (killerType.endsWith("1") || killerType.equals("Q1")) { 
            col1Kills++;
        } else if (killerType.endsWith("2") || killerType.equals("Q2")) {
            col2Kills++;
        } else if (killerType.equals("Spider")) {
            spiderKills++;
        } else if (killerType.equals("Beetles")) {
            beetlesKills++;
        }
    }
    
    /**
     * Records a death based on the killed agent's type string. (Tracks deaths *suffered*.)
     */
    public void recordDeath(String deadAgentType) {
        if (deadAgentType == null) return; // Defensive check
        
        if (deadAgentType.endsWith("1") || deadAgentType.equals("Q1")) { 
            col1Deaths++;
        } else if (deadAgentType.endsWith("2") || deadAgentType.equals("Q2")) {
            col2Deaths++;
        } else if (deadAgentType.equals("Spider")) {
            spiderDeaths++;
        } else if (deadAgentType.equals("Beetles")) {
            beetlesDeaths++;
        }
    }

    
    // Getters for Kills (Kills Made)
    public int getColonyKills(int colonyNumber) {
        return (colonyNumber == 1) ? col1Kills : col2Kills;
    }

    public int getSpiderKills() { return spiderKills; }
    public int getBeetlesKills() { return beetlesKills; }
    
    // Getters for Deaths (Deaths Suffered)
    public int getColonyDeaths(int colonyNumber) {
        return (colonyNumber == 1) ? col1Deaths : col2Deaths;
    }

    public int getSpiderDeaths() { return spiderDeaths; }
    public int getBeetlesDeaths() { return beetlesDeaths; }

    // Retuns the number of food colected
    public int getColonyFoodCollected(int colonyNumber) {
        return (colonyNumber == 1) ? arena.getFoodDeposit(1) : arena.getFoodDeposit(2);
    }
    

    // Returns width
    public int getWidth() {
        return this.width;
    }

    // Returns height
    public int getHeight() {
        return this.height;
    }

    // Returns the size of the cells
    public int getCellSize(){
        return this.cellSize;
    }

    // Returns the width of the array created.
    public int getArrayWidth(){
        return this.arrayWidth;
    }

    // Retrunst the height of the array created.
    public int getArrayHeight(){
        return this.arrayHeight;
    }

    public String getType(int x, int y){
        // Defensive check before calling getType()
        Environment agent = arena.getClassBoard(x, y);
        return (agent != null) ? agent.getType() : "G";
    }

    public String getTeam(int x, int y){
        // Defensive check before calling getTeam()
        Environment agent = arena.getClassBoard(x, y);
        return (agent != null) ? agent.getTeam() : "";
    }

    // Returns the color of the pixel
    public Color getColorAt(int x, int y) {
        
        Environment agent = arena.getClassBoard(x, y);

        if (agent == null) {
            // Should never run if the logic is correct
            return Color.BLACK; 
        }

        if(arena.getBoardCell(x, y).equals("G")){
            return agent.getColor();
        } else {
            return agent.getColor();
        }
    }

}