import javafx.scene.paint.Color;

public class Grass extends Environment{
    
    private Color grassColor;
    // Represents grass in the environment
    // Color is set based on the noise value generated from noise generator(FasetNoiseLite.java)
    public Grass(float noiseValue){
        if (noiseValue < -0.2) {
            grassColor = Color.rgb(145, 193, 103);
        } else if (noiseValue < 0.3) {
            grassColor = Color.rgb(163, 204, 110); 
        } else if (noiseValue < 0.6) {
            grassColor = Color.rgb(242, 213, 121);
        }else {
            grassColor = Color.rgb(242, 213, 121);
        }
    }
    
    @Override
    public Color getColor(){
        return grassColor;
    }

}
