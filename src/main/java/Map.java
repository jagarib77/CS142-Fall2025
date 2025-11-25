/*
CS&142
Author : Thaknin Hor & Afnan Ali
Date : 11/24/2005
 */

package OceanPath;


import java.util.*;
import java.io.*;

public class Map {

    private int x;
    private int y;
    private Location[][] grid;

    public Map(String filename) throws FileNotFoundException {
        ArrayList<String> lines = readLines(filename);

        this.y = lines.size();
        System.out.println(y);
        this.x = lines.get(0).split(", ").length;
        System.out.println(x);

        grid = new Location[y][x];

        for (int r = 0; r < y; r++) {
            String[] dirs = lines.get(r).trim().split("\\s*,\\s*");

            for (int c = 0; c < dirs.length; c++) {

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
                    default: loc.addDirection(Direction.C); break;
                }

                grid[r][c] = loc;
            }
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Location getLocation(int x,  int y) {
        return grid[y][x];
    }

    public void printGrid() {
        for (Location[] row : grid) {
            System.out.println();
            for (Location loc : row) {
                if (loc != null) {
                    System.out.print(loc.chooseRandomDirection().toString());
                } else {
                    System.out.print(". ");
                }
            }
        }
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
