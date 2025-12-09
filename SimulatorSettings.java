package jwp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SimulatorSettings extends JFrame implements ChangeListener {
    JFrame frame;
    JPanel panel;
    JLabel label;
    JSlider slider;
    JButton Finished;
    int tickSpeed;

    SimulatorSettings(){
        frame = new JFrame("Simulator Settings");
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));

        label = new JLabel();
        slider = new JSlider(0, 1000, 500);
        tickSpeed = slider.getValue();
        slider.setPreferredSize(new Dimension(400,200));
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(50);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(250);
        slider.setPaintLabels(true);
        label.setText("Tick Speed (ms): " + slider.getValue());
        label.setPreferredSize(new Dimension(200, label.getPreferredSize().height));
        slider.addChangeListener(this);

        Finished = new JButton("Confirm");
        Finished.setPreferredSize(new Dimension(150,50));
        Finished.setMaximumSize(new Dimension(150,50));
        Finished.setFocusable(false);
        Finished.setAlignmentX(Component.CENTER_ALIGNMENT);
        Finished.addActionListener(e -> {
            MainFrame.tickSpeed = tickSpeed;
            frame.dispose();
        });

        JPanel group1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        group1.add(label);
        group1.add(slider);

        panel.add(group1);
        panel.add(Finished);

        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void stateChanged(ChangeEvent e) {
        tickSpeed = slider.getValue();
        label.setText("Tick Speed (ms): " + tickSpeed);
    }

}
