
public class CheckEnemy {

    public CheckEnemy(){

    }

    // Checks if the object in the given position is and enemy or not.
    public static boolean isEnemy(int currentX, int currentY, int[] enemyPosition){
        
        // Checks if the enemy postion if valid. It's invalid if its outside the arena(aka size of the array).
        if((enemyPosition[0] < 0) || (enemyPosition[0] >= MainLogic.arrayWidth) || (enemyPosition[1] < 0) || (enemyPosition[1] >= MainLogic.arrayHeight)){
            return false;
        }

        String currentAgent = GameBoard.board[currentX][currentY];
        String enemyAgent = GameBoard.board[enemyPosition[0]][enemyPosition[1]];
        
        // Returns false if the object is a grass or colony.
        if(currentAgent.equals("G") || currentAgent.equals("C1") || currentAgent.equals("C2") || currentAgent.equals("F")){
            return false;
        }
        
        // Returns false if the object is itself.
        if(currentAgent.equals(enemyAgent)){
            return false;
        }

        // Returns false if they are in the same team.
        if(Environment.classBoard[currentX][currentY].getTeam().equals(Environment.classBoard[enemyPosition[0]][enemyPosition[1]].getTeam())){
            return false;
        }

        // Returns false if the object is a grass, otherwise returns true(Must be an enemy if )
        return !(enemyAgent.equals("G") || enemyAgent.equals("C1")  || enemyAgent.equals("C2"));
    }
}
