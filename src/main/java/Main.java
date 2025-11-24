public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(80, 80);
        SimulationGUI simulationGUI = new SimulationGUI(simulation);
        simulationGUI.setVisible(true);
    }
}
