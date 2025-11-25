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
        gridPanel.setSimulator(new PlagueIncSimulator());

        setContentPane(gridPanel); 
        setResizable(false);       
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setSize(800, 800);         
        setVisible(true);          
    }
}