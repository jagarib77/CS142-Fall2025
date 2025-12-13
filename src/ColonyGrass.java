import javafx.scene.paint.Color;

public class ColonyGrass extends Environment{

    private Color grassColor;

    // Constructor for the grass environment
    public ColonyGrass(boolean redColony){
        if(!redColony){
            grassColor = Color.rgb(118, 173, 189);
            
        }else{
            grassColor = Color.rgb(150, 134, 89);
        }
    }

    // Returns the color of the grass environment
    public Color getColor(){
        return grassColor;
    }
}