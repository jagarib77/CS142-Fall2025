public class Fight {
    
    public Fight(){

    }

    public static void battle(Environment agent1, Environment agent2){
        // For agent 1
        int hp1 = agent1.getHealth();
        int dmg1 = agent1.getAttack();

        // For agent 2
        int hp2 = agent2.getHealth();
        int dmg2 = agent2.getAttack();

        // Both agents attack each other
        hp1 -= dmg2;
        hp2 -= dmg1;

        // Update the health of both agents
        agent1.updateHP(hp1);
        agent1.helpNeeded();
        agent2.updateHP(hp2);
        agent2.helpNeeded();


        // Checks if the agent is a worker, and if so, they can ask for help
        if(GameBoard.board[agent2.getX()][agent2.getY()].equals("W1")){
            AssignHelp.assignHelp(agent2, agent1);
        }

        else if(GameBoard.board[agent1.getX()][agent1.getY()].equals("W1") ){
            AssignHelp.assignHelp(agent1, agent2);
        }   
        else if(GameBoard.board[agent1.getX()][agent1.getY()].equals("W2")){
            AssignHelp.assignHelp(agent1, agent2); 
        }
        
        else if(GameBoard.board[agent2.getX()][agent2.getY()].equals("W2")){
            AssignHelp.assignHelp(agent2, agent1);
        }
    }
}
