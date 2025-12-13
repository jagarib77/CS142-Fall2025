// This class makes it easier for the predator and queen classes to return the direction they want to move

public class Direction {


    // These function returns an array with 2 elements
    // The first element represents East(+1) and West(-1)
    // The second element represnts North(-1) and South(+1);
    public static int[] South(){
        return new int[] {0,1};
    }

    public static int[] North(){
        return new int[] {0,-1};
    }

    public static int[] East(){
        return new int[] {1,0};
    }

    public static int[] West(){
        return new int[] {-1,0};
    }

    // This function returns the center movement(in other word no movement)
    public static int[] Center(){
        return new int[] {0,0};
    }
}
