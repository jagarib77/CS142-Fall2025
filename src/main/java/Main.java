public class Main {
    public static void main(String[] args) {
        SetupGUI setupGUI = new SetupGUI();
        setupGUI.waitForStart();
        
        Simulation simulation = new Simulation(80, 80);

        // Adds simulation objects
        for (int i = 0; i < setupGUI.getNumBoids(); i++) {
            simulation.addRandomBoid();
        }
        for (int i = 0; i < setupGUI.getNumSadBoids(); i++) {
            simulation.addRandomSadBoid();
        }
        for (int i = 0; i < setupGUI.getNumSuperBoids(); i++) {
            simulation.addRandomSuperBoid();
        }
        for (int i = 0; i < setupGUI.getNumTrees(); i++) {
            simulation.addRandomTree();
        }
        for (int i = 0; i < setupGUI.getNumCars(); i++) {
            simulation.addRandomCar();
        }
        for (int i = 0; i < setupGUI.getNumWindows(); i++) {
            simulation.addRandomWindow();
        }
        
        // Start the simulation GUI
        SimulationGUI simulationGUI = new SimulationGUI(simulation);
        simulationGUI.setVisible(true);
    }
}
