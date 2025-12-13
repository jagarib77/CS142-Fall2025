
// This class` might be used to generate the world at the beginning of the simulation

public class GrassGenerator {

    private FastNoiseLite noiseGenerator;
    private float[][] noiseMap;


    public GrassGenerator(int arrayWidth, int arrayHeight){
        // Set up the noise generator
        noiseGenerator = new FastNoiseLite();
        noiseGenerator.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noiseGenerator.SetFrequency(0.012f); // Controls the "zoom" level. Experiment!

        // Create the noise map
        noiseMap = new float[arrayWidth][arrayHeight];
        for (int y = 0; y < arrayHeight; y++) {
            for (int x = 0; x < arrayWidth; x++) {
                // Get a noise value (from -1.0 to 1.0) for this coordinate
                noiseMap[x][y] = noiseGenerator.GetNoise(x, y);
            }
        }
    }
    
    public float getNoiseLevel(int x, int y){
        float noiseVal = noiseMap[x][y];
        return noiseVal;
    }

}
