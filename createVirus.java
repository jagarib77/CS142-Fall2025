package jwp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class createVirus extends JFrame implements ChangeListener {
    JFrame frame;
    JPanel panel;
    JLabel label, label2, minDurationLabel, maxDurationLabel;
    JSlider slider, slider2, minDurationSlider, maxDurationSlider;
    JButton Finished;
    double IR, DR;
    int minDuration, maxDuration;

    createVirus(PlagueIncSimulator sim){
        frame = new JFrame("Create Virus");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        label = new JLabel("Infection Rate 50%");
        slider = new JSlider(0,100,50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMinorTickSpacing(10);
        slider.setMajorTickSpacing(25);
        slider.addChangeListener(this);

        label2 = new JLabel("Death Rate 50%");
        slider2 = new JSlider(0,100,50);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2.setMinorTickSpacing(10);
        slider2.setMajorTickSpacing(25);
        slider2.addChangeListener(this);

        minDurationLabel = new JLabel("Min Infection Duration: 5");
        minDurationSlider = new JSlider(1,50,5);
        minDurationSlider.setPaintTicks(true);
        minDurationSlider.setPaintLabels(true);
        minDurationSlider.setMinorTickSpacing(1);
        minDurationSlider.setMajorTickSpacing(5);
        minDurationSlider.addChangeListener(this);

        maxDurationLabel = new JLabel("Max Infection Duration: 15");
        maxDurationSlider = new JSlider(1,50,15);
        maxDurationSlider.setPaintTicks(true);
        maxDurationSlider.setPaintLabels(true);
        maxDurationSlider.setMinorTickSpacing(1);
        maxDurationSlider.setMajorTickSpacing(5);
        maxDurationSlider.addChangeListener(this);

        Finished = new JButton("Release your Virus");
        Finished.setAlignmentX(Component.CENTER_ALIGNMENT);
        Finished.addActionListener(e -> {
            sim.setCustomDisease(
                    "Custom Virus",
                    IR,
                    DR,
                    0,
                    minDuration,
                    maxDuration
            );
            new MainFrame(SimulationType.CustomVirus, IR, DR);
            frame.dispose();
        });

        panel.add(createSliderPanel(label, slider));
        panel.add(createSliderPanel(label2, slider2));
        panel.add(createSliderPanel(minDurationLabel, minDurationSlider));
        panel.add(createSliderPanel(maxDurationLabel, maxDurationSlider));
        panel.add(Box.createVerticalStrut(10));
        panel.add(Finished);

        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createSliderPanel(JLabel lbl, JSlider sld){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout(5,5));
        p.add(lbl, BorderLayout.NORTH);
        p.add(sld, BorderLayout.CENTER);
        return p;
    }

    public void stateChanged(ChangeEvent e) {
        IR = slider.getValue() / 100.0;
        DR = slider2.getValue() / 100.0;
        minDuration = minDurationSlider.getValue();
        maxDuration = maxDurationSlider.getValue();

        label.setText("Infection Rate " + slider.getValue() + "%");
        label2.setText("Death Rate " + slider2.getValue() + "%");
        minDurationLabel.setText("Min Infection Duration: " + minDuration);
        maxDurationLabel.setText("Max Infection Duration: " + maxDuration);
    }
}
