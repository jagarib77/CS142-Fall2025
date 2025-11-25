import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;



public class OceonMain {

    public static void main(String[] donuts) throws FileNotFoundException {

        Scanner s = new Scanner(System.in);
        System.out.print("Current map file: ");

        try {
            String file = s.nextLine();
            Map map = new Map(file);
            map.getLocation(0, 0);
        }

        catch (FileNotFoundException e){
            System.out.println("sucks");

        }

        System.out.print("Starting X position : ");
        int startX = s.nextInt();

        System.out.print("Starting Y position : ");
        int startY = s.nextInt();

        Item item = new Item(startX, startY);

        Location loc = new Location(0, 0);
        loc.addDirection(Direction.E);
        loc.addDirection(Direction.SE);
        loc.addDirection(Direction.NE);

        

        

        






    }
}
