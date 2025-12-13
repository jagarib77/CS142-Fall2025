public class FindNearestSoldier {
   
    public FindNearestSoldier() {
        // Constructor implementation
    }

    public static Environment findNearestSoldier(String team){
        Environment nearestSoldier = null;
        // Loop through the game board to find a soldier to help the ant
        for(int i=0; i<Environment.classBoard.length; i++){
            for(int j=0; j<Environment.classBoard[0].length; j++){
                if(GameBoard.board[i][j].equals("So1") || GameBoard.board[i][j].equals("So2")){
                    
                    // Check if team matches AND if the soldier is NOT already helping someone
                    if(Environment.classBoard[i][j].getTeam().equals(team) && !Environment.classBoard[i][j].getIsHelping()){
                        return Environment.classBoard[i][j];
                    }   
                }
            }
        }

        return nearestSoldier;
    }
}