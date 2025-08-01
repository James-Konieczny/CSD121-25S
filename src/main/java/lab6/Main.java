package lab6;

import javafx.application.Application;
import javafx.stage.Stage; // Imports the Stage class, which represents the main window


/**
 * The Main class is the entry point for this game.
 * It initializes and launches the game window.
 * This class extends the JavaFX {@code Application} class,
 * which is required to launch any JavaFX-based GUI (graphical user interface).
 */
public class Main extends Application {

    /**
     * This method is called when the JavaFX application starts.
     * It sets up the primary stage (window) and begins the game.
     *
     * @param primaryStage The main window for this JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game(); // Create a new instance of the Game class
        primaryStage.setTitle("My First Game"); // Set the window title
        primaryStage.setScene(game.getScene()); // Set the scene (the content of the window) to the game's scene
        primaryStage.show(); // Display the window on the screen
        game.start(); // Start the game logic (e.g., animations, game loop, etc.)
    }

    /**
     * The main method launches the JavaFX application.
     * This method is required to start the program when run from a standard Java environment.
     *
     * @param args Command-line arguments (not used in this case).
     */
    public static void main(String[] args) {
        launch(args); // Just in case :)
    }
}