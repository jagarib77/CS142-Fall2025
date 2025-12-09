/*
CS 142
AUTHORS: Thaknin Hor, Afnan Ali
12/07/2025
*/

package OceanPath;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class OceanMain {

    public static void main(String[] args) throws FileNotFoundException {
        Random r = new Random();
        Scanner s = new Scanner(System.in);

        System.out.print("Current map file: ");
        String file = s.nextLine();
        Map map = new Map("C:\\Users\\afnan\\Java Projects\\Java Project\\OceanPath\\"+file);

        map.printGrid();

        List<Item> items = new ArrayList<>();

        System.out.println("\nPollute The Ocean!");
        System.out.println("Do you want to add your own items? Yes / No");
        String Answer = s.nextLine();

        if (Answer.equalsIgnoreCase("Yes")) {
            System.out.print("How many items do you want to add? ");
            int n = s.nextInt();

            for (int i = 1; i <= n; i++) {
                System.out.print("Item " + i + " Start X = ");
                int x = s.nextInt();
                System.out.print("Item " + i + " Start Y = ");
                int y = s.nextInt();
                items.add(new Item(x, y));
            }

        } else {
            System.out.print("How many random items do you want to add? ");
            int n = s.nextInt();
            items.addAll(createRandomItems(n, map.getX(), map.getY(), r));
        }

        // GUI panel
        OceanGUI panel = new OceanGUI(map, items);

        // Frame
        JFrame frame = new JFrame("Items In Ocean Currents Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Controls
        JPanel controls = new JPanel();
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton step = new JButton("Step");

        JSlider speedSlider = new JSlider(50, 1000, 500);
        speedSlider.setPreferredSize(new Dimension(150, 40));
        speedSlider.setMajorTickSpacing(250);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> panel.setSpeed(speedSlider.getValue()));

        start.addActionListener(e -> panel.start());
        stop.addActionListener(e -> panel.stop());
        step.addActionListener(e -> panel.step());

        controls.add(start);
        controls.add(stop);
        controls.add(step);
        controls.add(new JLabel("Speed:"));
        controls.add(speedSlider);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        s.close();
    }

    public static List<Item> createRandomItems(int n, int maxX, int maxY, Random r) {
        List<Item> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new Item(r.nextInt(maxX), r.nextInt(maxY)));
        }
        return list;
    }
}
