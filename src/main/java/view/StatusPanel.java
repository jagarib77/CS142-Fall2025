package view;

import model.world.SimulationGrid;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatusPanel extends JPanel {
    private final SimulationGrid grid;

    public StatusPanel(SimulationGrid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(200, grid.getHeight() * 12));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Map symbol -> entity name
        Map<Character, String> entityInfo = new LinkedHashMap<>();
        entityInfo.put('C', "Civilian");
        entityInfo.put('S', "Soldier");
        entityInfo.put('E', "EliteZombie");
        entityInfo.put('Z', "CommonZombie");
        entityInfo.put('W', "Weapon");
        entityInfo.put('A', "Armor");
        entityInfo.put('M', "Medkit");

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        int y = 20;

        for (Map.Entry<Character, String> entry : entityInfo.entrySet()) {
            char symbol = entry.getKey();
            String name = entry.getValue();

            int count = grid.countEntitiesBySymbol(symbol); // method to add in SimulationGrid

            // Draw color box
            g.setColor(getColorForSymbol(symbol));
            g.fillRect(10, y - 12, 12, 12);

            // Draw text
            g.setColor(Color.WHITE);
            g.drawString(name + ": " + count, 30, y);

            y += 20;
        }
    }

    private Color getColorForSymbol(char c) {
        return switch (c) {
            case 'C', 'S', 'I' -> new Color(100, 180, 255);
            case 'Z' -> Color.YELLOW;
            case 'E' -> Color.MAGENTA;
            case 'W' -> Color.ORANGE;
            case 'A' -> Color.LIGHT_GRAY;
            case 'M' -> Color.GREEN;
            default -> Color.DARK_GRAY;
        };
    }
}
