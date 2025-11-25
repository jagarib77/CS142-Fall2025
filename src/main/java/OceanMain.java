/*
CS142
Author : Thaknin Hor
Date : 11/25/2025
 */
package OceanPath;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;


public class OceanMain {

    public static void main(String[] donuts) throws FileNotFoundException {
        Random r = new Random();
        Scanner s = new Scanner(System.in);
        System.out.print("Current map file: ");
        String file = s.nextLine();
        Map map = new Map("C://Users//Dell//IdeaProjects//OceanPath//src//main//java//OceanPath//"+file);

        map.printGrid();

        List<Item> items = new ArrayList<>();

        System.out.println();
        System.out.println("Pollute The Ocean! ");
        System.out.println("Do you want to add your own items? Yes / No");
        String Answer = s.nextLine();
        if (Answer.equals("Yes")) {
            System.out.print("How many items do you want to add to? ");
            int numOfItems = s.nextInt();
            System.out.println();

            for (int i = 1; i <= numOfItems; i++) {
                System.out.print("Item " + i + " Start X = ");
                int startX = s.nextInt();
                System.out.println();
                System.out.print("Item " + i + " Start Y = ");
                int startY = s.nextInt();
                System.out.println();
                items.add(new Item(startX, startY));
            }
        } else {
            System.out.print("How many random items do you want to add to? ");
            int numOfItems = s.nextInt();
            items.addAll(creatRandomItems(numOfItems, map.getX(), map.getY(), r));
        }

        OceanGUI panel = new OceanGUI(map, items);
        JFrame frame = new JFrame("Items In Ocean Currents Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel controls = new JPanel();
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton step = new JButton("Step");

        start.addActionListener(e -> panel.start());
        stop.addActionListener(e -> panel.stop());
        step.addActionListener(e -> panel.step());

        controls.add(start);
        controls.add(stop);
        controls.add(step);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(controls, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static List<Item> creatRandomItems(int numOfItems, int maxX, int maxY, Random r){
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < numOfItems; i++){
            items.add(new Item(r.nextInt(maxX - 1), r.nextInt(maxY - 1)));
        }
        return items;
    }
}
