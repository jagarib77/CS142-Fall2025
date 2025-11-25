/*
CS&142
Author : Thaknin Hor
Date : 11/24/25
 */
package OceanPath;

import java.util.*;
import java.io.*;

public class Map {
    private Integer row;
    private Integer col;

    public Map(String filename) throws FileNotFoundException {
        ArrayList<String> Rows = readLines(filename);
        this.row = Rows.size();
        this.col = Rows.get(0).split(", ").length;

        for  (int r = 0; r < row; r++) {
            String[] dirs = Rows.get(r).split(", ");
            for (int c = 0; c < col; c++) {
                Location loc =  new Location(c, r);
                if (dirs[c].equals("N")) {
                    loc.addDirection(Direction.N);
                } else if (dirs[c].equals("NE")) {
                    loc.addDirection(Direction.NE);
                } else if (dirs[c].equals("E")) {
                    loc.addDirection(Direction.E);
                } else if (dirs[c].equals("S")) {
                    loc.addDirection(Direction.S);
                } else if (dirs[c].equals("SE")) {
                    loc.addDirection(Direction.SE);
                } else if (dirs[c].equals("W")) {
                    loc.addDirection(Direction.W);
                } else if (dirs[c].equals("NW")) {
                    loc.addDirection(Direction.NW);
                } else if (dirs[c].equals("SW")) {
                    loc.addDirection(Direction.SW);
                }
            }
        }

    }

    public static ArrayList<String> readLines(String fileName) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<String>();
        Scanner input = new Scanner(new File(fileName));
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.length() > 0) {
                lines.add(line);
            }
        }
        return lines;
    }
}
