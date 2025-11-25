package view;

import model.world.SimulationGrid;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static model.config.SimulationConstants.*;

public class StatusPanel extends JPanel {
    private final SimulationGrid grid;

    public StatusPanel(SimulationGrid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(STATUS_PANEL_WIDTH, grid.getHeight() * CELL_SIZE));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Map<Character, String> entityInfo = new LinkedHashMap<>();
        entityInfo.put(CIVILIAN_CHAR, CIVILIAN_STRING);
        entityInfo.put(SOLDIER_CHAR, SOLDIER_STRING);
        entityInfo.put(ELITE_ZOMBIE_CHAR, ELITE_ZOMBIE_STRING);
        entityInfo.put(COMMON_ZOMBIE_CHAR, COMMON_ZOMBIE_STRING);
        entityInfo.put(WEAPON_CHAR, WEAPON_STRING);
        entityInfo.put(ARMOR_CHAR, ARMOR_STRING);
        entityInfo.put(MEDKIT_CHAR, MEDKIT_STRING);


        g.setFont(GAME_STAT_FONT);
        int y = 20;

        for (Map.Entry<Character, String> entry : entityInfo.entrySet()) {
            char symbol = entry.getKey();
            String name = entry.getValue();

            int count = grid.countEntitiesBySymbol(symbol);

            g.setColor(getColor(symbol));
            g.fillRect(CELL_SIZE, y - CELL_SIZE, CELL_SIZE, CELL_SIZE);

            g.setColor(Color.WHITE);
            g.drawString(name + ": " + count, 30, y);

            y += 20;
        }
    }

    private Color getColor(char c) {
        return switch (c) {
            case CIVILIAN_CHAR -> COLOR_CIVILIAN;
            case INFECTION_CHAR -> COLOR_INFECTION;
            case SOLDIER_CHAR -> COLOR_SOLDIER;
            case COMMON_ZOMBIE_CHAR -> COLOR_COMMON_ZOMBIE;
            case ELITE_ZOMBIE_CHAR -> COLOR_ELITE_ZOMBIE;
            case WEAPON_CHAR -> COLOR_WEAPON;
            case ARMOR_CHAR -> COLOR_ARMOR;
            case MEDKIT_CHAR -> COLOR_MEDKIT;
            default -> COLOR_DEFAULT;
        };
    }
}
