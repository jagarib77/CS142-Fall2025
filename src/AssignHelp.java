public class AssignHelp {
    
    public AssignHelp(){

    }

    public static void assignHelp(Environment agent, Environment enemyAgent){
        // Only assign help if the agent isn't already being helped
        if((agent.isHelpNeeded() && !agent.getIsBeingHelped())){
            
            // Asoldier to help
            Environment nearestSoldier = FindNearestSoldier.findNearestSoldier(agent.getTeam());
            
            if(nearestSoldier != null){
                // Give the soldier a target
                nearestSoldier.setDangerAnt(true, agent.getX(), agent.getY(),enemyAgent.getX(), enemyAgent.getY());
                
                // Mark the soldier as busy
                nearestSoldier.updateHelpingStatus(true);
                
                // Mark the worker as being helped
                agent.beingHelped();
            }
        }
    }
}