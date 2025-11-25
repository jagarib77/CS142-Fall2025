/*
CS&142
Author : Thaknin Hor & Afnan Ali
Date : 11/24/2005
 */

package OceanPath;


import java.util.*;
import java.io.*;

public class Map {

    private int rows;
    private int cols;
    private Location[][] grid;

    public Map(String filename) throws FileNotFoundException {
        ArrayList<String> lines = readLines(filename);

        this.rows = lines.size();
        this.cols = lines.get(0).split(", ").length;

        grid = new Location[rows][cols];

        for (int r = 0; r < rows - 1; r++) {
            String[] dirs = lines.get(r).split(", ");

            for (int c = 0; c < cols - 1; c++) {

                Location loc = new Location(c, r);

                switch (dirs[c]) {
                    case "N": loc.addDirection(Direction.N); break;
                    case "NE": loc.addDirection(Direction.NE); break;
                    case "E": loc.addDirection(Direction.E); break;
                    case "SE": loc.addDirection(Direction.SE); break;
                    case "S": loc.addDirection(Direction.S); break;
                    case "SW": loc.addDirection(Direction.SW); break;
                    case "W": loc.addDirection(Direction.W); break;
                    case "NW": loc.addDirection(Direction.NW); break;
                }

                grid[r][c] = loc;
            }
        }
    }

    public Location getLocation(int x,  int y) {
        return grid[y][x];
    }

    private static ArrayList<String> readLines(String fileName) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(fileName));
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.length() > 0) {
                    lines.add(line);
                }
            }

            input.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found.");
        }
        return lines;
        
    }
}
