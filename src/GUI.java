import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class GUI extends Application {

    private Timeline animationLoop;
    private MainLogic myModel;
    private Canvas canvas;
    
    // Controls
    private Button startButton;
    private Button stopButton;
    private Button restartButton;
    private CheckBox predatorCheck;
    private CheckBox colonyCheck;
    private Slider speedSlider;

    // Stats panel feilds 
    private Label col1StatsLabel;
    private Label col2StatsLabel;
    private Label spiderStatsLabel;
    private Label beetlesStatsLabel;

    // Global variables(default values and can be changed)
    private static int GRID_WIDTH = 800; 
    private static int GRID_HEIGHT = 600; 
    private static int CELL_SIZE = 4; 
    // Default calculation for food, can be overridden
    private static int foodCount = (int)((GRID_HEIGHT * GRID_WIDTH) / CELL_SIZE * 0.01);
    public static int predatorCount = 5;


    // The Java entry point. It just launches the GUI.
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Shows the configuration window first
        showConfigWindow(primaryStage);
    }

    /*
     * Shows a popup window to configure simulation parameters.
     * Once confirmed, it launches the main simulation.
     */
    private void showConfigWindow(Stage primaryStage) {
        Stage configStage = new Stage();
        configStage.setTitle("Sim Config");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Inputs
        TextField widthInput = new TextField(String.valueOf(GRID_WIDTH));
        TextField heightInput = new TextField(String.valueOf(GRID_HEIGHT));
        TextField sizeInput = new TextField(String.valueOf(CELL_SIZE));
        TextField foodInput = new TextField(String.valueOf(foodCount));
        TextField predInput = new TextField(String.valueOf(predatorCount));

        // Labels
        grid.add(new Label("Grid Width:"), 0, 0);
        grid.add(widthInput, 1, 0);

        grid.add(new Label("Grid Height:"), 0, 1);
        grid.add(heightInput, 1, 1);

        grid.add(new Label("Cell Size:"), 0, 2);
        grid.add(sizeInput, 1, 2);

        grid.add(new Label("Food Count:"), 0, 3);
        grid.add(foodInput, 1, 3);

        grid.add(new Label("Predator Count:"), 0, 4);
        grid.add(predInput, 1, 4);

        Button btnStart = new Button("Launch Simulation");
        grid.add(btnStart, 1, 5);

        // Handle button click
        btnStart.setOnAction(e -> {
            try {
                // Get values
                GRID_WIDTH = Integer.parseInt(widthInput.getText());
                GRID_HEIGHT = Integer.parseInt(heightInput.getText());
                CELL_SIZE = Integer.parseInt(sizeInput.getText());
                foodCount = Integer.parseInt(foodInput.getText());
                predatorCount = Integer.parseInt(predInput.getText());

                // Close window and start main simulation
                configStage.close();
                launchSimulation(primaryStage);

            } catch (NumberFormatException ex) {
                System.out.println("Please enter valid integers.");
            }
        });

        Scene scene = new Scene(grid, 300, 300);
        configStage.setScene(scene);
        configStage.show();
    }

    // Start logi 
    public void launchSimulation(Stage primaryStage) {
        
        // Creates canvas and animation loop
        canvas = new Canvas(GRID_WIDTH, GRID_HEIGHT);
        
        animationLoop = new Timeline();
        animationLoop.setCycleCount(Animation.INDEFINITE);
        
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), event -> { // Slower base speed
            if(myModel.gameOver()) {
                animationLoop.stop();
                return;
            }
            // Updates
            myModel.updateSimulation(); 
            drawSimulation(); 
            updateStats();
        });
        animationLoop.getKeyFrames().add(keyFrame);

        
        // Checkboxes
        predatorCheck = new CheckBox("Include Predators");
        colonyCheck = new CheckBox("Include 2nd Colony");
        HBox optionsBox = new HBox(10, predatorCheck, colonyCheck);
        optionsBox.setAlignment(Pos.CENTER);

        // Buttons
        startButton = new Button("Start");
        stopButton = new Button("Stop");
        restartButton = new Button("Restart");
        HBox buttonBox = new HBox(10, startButton, stopButton, restartButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Slider
        Label speedLabel = new Label("Simulation Speed:");
        speedSlider = new Slider(0.1, 5.0, 1.0); // 0.1x to 5x speed, default 1x
        
        
        // Start Button
        startButton.setOnAction(event -> {
            animationLoop.play();
            
            // Lock options, enable Stop.
            startButton.setDisable(true);
            stopButton.setDisable(false);
            predatorCheck.setDisable(true);
            colonyCheck.setDisable(true);
        });
        
        // Stop Button
        stopButton.setOnAction(event -> {
            animationLoop.stop();
            
            // Enable Start (to resume), disable Stop
            startButton.setDisable(false);
            stopButton.setDisable(true);
        });
        
        // Restart Button
        restartButton.setOnAction(event -> {
            resetSimulation();
        });
        
        // Slider
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            animationLoop.setRate(newVal.doubleValue());
        });

        
        // VBox holds all controls vertically
        VBox controlsVBox = new VBox(10, speedLabel, speedSlider, optionsBox, buttonBox);
        controlsVBox.setPadding(new Insets(10));
        controlsVBox.setAlignment(Pos.CENTER);

        col1StatsLabel = createStatsPanel("COLONY 1 (Blue)");
        col2StatsLabel = createStatsPanel("COLONY 2 (Red)");
        spiderStatsLabel = createStatsPanel("SPIDER");
        beetlesStatsLabel = createStatsPanel("BEETLES");
        
        // Create VBox for Stats Panel
        VBox statsVBox = new VBox(15, 
            new Label("--- SIMULATION STATS ---"), // Title 
            col1StatsLabel, 
            col2StatsLabel, 
            spiderStatsLabel, 
            beetlesStatsLabel
        );
        statsVBox.setPadding(new Insets(10));
        statsVBox.setStyle("-fx-border-color: darkgray; -fx-border-width: 1; -fx-background-color: #f4f4f4;");
        statsVBox.setMinWidth(220); // Min width of panel
        
        // BorderPane is the main layout
        BorderPane root = new BorderPane();
        root.setCenter(canvas); // Canvas in the middle
        root.setBottom(controlsVBox); // All controls at the bottom
        root.setRight(statsVBox);     // Stats panel on the right

        // Set up the stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Ant Colony Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Call resetSimulation() once to start
        resetSimulation();
    }

    // Stops any running simulation, reads the checkboxes,
    // creates a new model, and resets the GUI buttons.
    private void resetSimulation() {
        // Stop the animation
        animationLoop.stop();
        
        // Read the user's options
        boolean includePreds = predatorCheck.isSelected();
        boolean includeColony2 = colonyCheck.isSelected();
        
        // Create a brand new model with these options
        myModel = new MainLogic(
            GRID_WIDTH,
            GRID_HEIGHT,
            CELL_SIZE,
            includePreds,
            includeColony2,
            foodCount
        );
        
        // Reset the GUI
        startButton.setDisable(false); 
        stopButton.setDisable(true); 
        predatorCheck.setDisable(false);
        colonyCheck.setDisable(false);
        
        // Draws a new clean board and update stats for the first time
        drawSimulation();
        updateStats();
    }

    // This method draws the current state of the model onto the canvas.
    private void drawSimulation() {
        // Get the tool for drawing on the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Clear the entire canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        // Get the grid dimensions from the model
        int gridWidth = myModel.getArrayWidth();
        int gridHeight = myModel.getArrayHeight();
        
        // Loop through every cell in the model
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                
                // Get the color of the cell
                Color colorToDraw = myModel.getColorAt(x, y);

                // Draw a rectangle for that cell
                gc.setFill(colorToDraw);
                gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // Outline for the insects
                if(myModel.getTeam(x, y).equals("Red")){
                    gc.setStroke(Color.RED); 
                    gc.setLineWidth(1);
                    gc.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else if(myModel.getTeam(x, y).equals("Blue")){
                    gc.setStroke(Color.rgb(67, 135, 224)); 
                    gc.setLineWidth(1);
                    gc.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else if(myModel.getTeam(x, y).equals("Predator")){
                    gc.setStroke(Color.rgb(250, 225, 87)); 
                    gc.setLineWidth(1);
                    gc.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }

            }

        }
    }
    
    // Helper method for consistent styling of stats labels.
    private Label createStatsPanel(String name) {
        // Set initial text to show name, will be updated immediately by updateStats()
        Label label = new Label(name); 
        label.setPadding(new Insets(8));
        label.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-font-family: monospace; -fx-font-size: 11px;");
        label.setMinWidth(180); 
        return label;
    }

    // Fetches current stats from the model and updates the GUI labels.
    private void updateStats() {
        
        // Blue colony stats
        int w1Alive = myModel.countCritters("W1");
        int s1Alive = myModel.countCritters("So1");
        int q1Alive = myModel.countCritters("Q1");
        int c1Alive = w1Alive + s1Alive + q1Alive;
        int c1Kills = myModel.getColonyKills(1);
        int c1Food = myModel.getColonyFoodCollected(1);
        int c1Deaths = myModel.getColonyDeaths(1);
        int c1Score = c1Alive + c1Kills + c1Food;
        
        col1StatsLabel.setText(formatColonyStats(
            "COLONY 1 (Blue)", c1Alive, c1Kills, c1Deaths, c1Food, c1Score, w1Alive, s1Alive, q1Alive
        ));

        // Red colony stats
        int w2Alive = myModel.countCritters("W2");
        int s2Alive = myModel.countCritters("So2");
        int q2Alive = myModel.countCritters("Q2");
        int c2Alive = w2Alive + s2Alive + q2Alive;
        int c2Kills = myModel.getColonyKills(2);
        int c2Food = myModel.getColonyFoodCollected(2);
        int c2Deaths = myModel.getColonyDeaths(2);
        int c2Score = c2Alive + c2Kills + c2Food;
        
        // Updatea Colony 2 if it's included in the simulation
        if (MainLogic.includeSecondColony) {
            col2StatsLabel.setText(formatColonyStats(
                "COLONY 2 (Red)", c2Alive, c2Kills, c2Deaths, c2Food, c2Score, w2Alive, s2Alive, q2Alive
            ));
        } else {
            col2StatsLabel.setText("COLONY 2 (Red):\nNOT INCLUDED");
        }


        // Spider stats
        int spiderAlive = myModel.countCritters("Spider");
        int spiderKills = myModel.getSpiderKills();
        int spiderDeaths = myModel.getSpiderDeaths();
        int spiderScore = spiderAlive + spiderKills;
        
        // Beetles stats
        int beetlesAlive = myModel.countCritters("Beetles");
        int beetlesKills = myModel.getBeetlesKills();
        int beetlesDeaths = myModel.getBeetlesDeaths();
        int beetlesScore = beetlesAlive + beetlesKills;

        // Updates predator stats if predators are included
        if (MainLogic.includePredator) {
            spiderStatsLabel.setText(formatPredatorStats(
                "SPIDER", spiderAlive, spiderKills, spiderDeaths, spiderScore
            ));
            beetlesStatsLabel.setText(formatPredatorStats(
                "BEETLES", beetlesAlive, beetlesKills, beetlesDeaths, beetlesScore
            ));
        } else {
            spiderStatsLabel.setText("SPIDER:\nNOT INCLUDED");
            beetlesStatsLabel.setText("BEETLES:\nNOT INCLUDED");
        }
    }

    // Formatting for the colony label
    private String formatColonyStats(String name, int totalAlive, int kills, int deathsSuffered, int food, int score, int w, int s, int q) {
        return String.format(
            "%s:\n" +
            " W: %d | S: %d | Q: %d\n" +
            " %d ALIVE\n" +
            " + %d KILLS\n" +
            " - %d DEATHS\n" +
            " + %d FOOD\n" +
            " = %d TOTAL SCORE",
            name, w, s, q, totalAlive, kills, deathsSuffered, food, score
        );
    }

    // Formatting for the predator label
    private String formatPredatorStats(String name, int alive, int kills, int deathsSuffered, int score) {
        return String.format(
            "%s:\n" +
            " %d ALIVE\n" +
            " + %d KILLS\n" +
            " - %d DEATHS\n" +
            " = %d TOTAL SCORE",
            name, alive, kills, deathsSuffered, score
        );
    }
    
}