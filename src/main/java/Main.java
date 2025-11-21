public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(800, 800);
        SimulationGUI simulationGUI = new SimulationGUI(simulation);
        simulationGUI.setVisible(true);
    }
}
