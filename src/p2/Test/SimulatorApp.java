package p2.Test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import p2.Logging.Simulator;

public class SimulatorApp extends Application {
    private Simulator simulator;  // Simulator class
    private TextArea logArea;     // Text area for simulator logs

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the simulator
        simulator = new Simulator("C://Users//asonj//IdeaProjects//atms2p2//src//p2//Test//TestFile"); // Path to file

        // UI Components
        logArea = new TextArea();
        logArea.setEditable(false);

        Button startButton = new Button("Start Simulation");
        startButton.setOnAction(e -> runSimulation());

        VBox root = new VBox(10, startButton, logArea);
        Scene scene = new Scene(root, 500, 400);

        primaryStage.setTitle("Simulator Control");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void runSimulation() {
        // Use a new Thread to run the simulation to avoid freezing the UI
        new Thread(() -> {
            try {
                simulator.simulate();
                // Here you would need methods to fetch logs from your Simulator and display them
                logArea.appendText("Simulation completed successfully.\n");
            } catch (Exception e) {
                logArea.appendText("Failed to run simulation: " + e.getMessage() + "\n");
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

