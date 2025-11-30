package jwp;

import javax.swing.JFrame;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainFrame() {
        super("Plague"); // window title

        var gridPanel = new GridPanel(); // main drawing panel

        // OLD (example):
        // randomMovement.registerState(gridPanel);
        // gridPanel.setSimulator(new randomMovement());

        // NEW: use PlagueIncSimulator
        PlagueIncSimulator.registerStates(gridPanel);

        // create the simulator
        PlagueIncSimulator simulator = new PlagueIncSimulator();

        // OPTION 1: choose a preset disease profile
        // Uncomment ONE of these to test a specific profile:

        // simulator.setDiseaseProfile(PlagueIncSimulator.PROFILE_MILD_FLU);
        // simulator.setDiseaseProfile(PlagueIncSimulator.PROFILE_DEADLY_VIRUS);
        simulator.setDiseaseProfile(PlagueIncSimulator.PROFILE_ZOMBIE_PLAGUE);

        // OPTION 2: or define a custom disease (comment out the preset if you use this)
        // simulator.setCustomDisease(
        //         "Custom Plague",
        //         0.30, // infection probability
        //         0.15, // death probability
        //         0.70, // movement probability
        //         8,    // min infection duration (ticks)
        //         25    // max infection duration (ticks)
        // );

        // attach the simulator to the grid
        gridPanel.setSimulator(simulator);

        setContentPane(gridPanel); 
        setResizable(false);       
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setSize(800, 800);         
        setVisible(true);          
    }
}