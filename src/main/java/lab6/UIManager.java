package lab6;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

/**
 * UIManager handles all user interface elements on the screen:
 * hearts, score, instructions, game over screen, and buttons.
 */
public class UIManager {
    private final Pane uiRoot;
    private final List<Heart> heartIcons = new ArrayList<>();
    private Text scoreText;
    private Text instructions;
    private Button startButton;
    private Text gameOverText;
    private Text finalScoreText;
    private Button restartButton;

    /**
     * Constructs a new UIManager instance with an empty UI pane.
     */
    public UIManager() {
        this.uiRoot = new Pane();
    }

    /**
     * Creates and returns the full user interface.
     *
     * @param width  Width of the scene.
     * @param height Height of the scene.
     * @return A {@link Parent} node containing all UI elements.
     */
    public Parent createUI(double width, double height) {
        uiRoot.setPrefSize(width, height);

        // Background
        Image bgImage = AssetLoader.load("background.png");
        ImageView background = new ImageView(bgImage);
        background.setFitWidth(width);
        background.setFitHeight(height);
        uiRoot.getChildren().add(background);

        // Hearts
        for (int i = 0; i < 5; i++) {
            Heart heart = new Heart(true);
            heart.setTranslateX(10 + i * 35);
            heart.setTranslateY(10);
            heartIcons.add(heart);
            uiRoot.getChildren().add(heart);
        }

        // Score text
        scoreText = new Text("Obstacles: 0");
        scoreText.setStyle("-fx-font-weight: bold; -fx-fill: white; -fx-stroke: black; " +
                "-fx-stroke-width: 1px; -fx-font-size: 25;");
        scoreText.setTranslateX(width - 200); // Position based on width
        scoreText.setTranslateY(30);
        uiRoot.getChildren().add(scoreText);

        // Instructions
        instructions = new Text("CONTROLS:\n↑ (up) to Jump\n← → (left, right) to Move");
        instructions.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        instructions.setFill(Color.WHITE);
        instructions.setTextAlignment(TextAlignment.CENTER);
        instructions.setStyle("-fx-stroke: black; -fx-stroke-width: 2px");
        instructions.setTranslateX(100);
        instructions.setTranslateY(150);
        uiRoot.getChildren().add(instructions);

        // Start button
        startButton = new Button("Start");
        startButton.setLayoutX(width / 2 - 50); // Center horizontally
        startButton.setLayoutY(height / 2);
        startButton.setStyle("-fx-font-size: 20; -fx-padding: 10 20;");
        uiRoot.getChildren().add(startButton);

        // Game over text
        gameOverText = new Text("Game Over");
        gameOverText.setStyle("-fx-font-size: 60; -fx-fill: red; -fx-stroke-width: 2px; " +
                "-fx-stroke: white; -fx-font-weight: bold;");
        gameOverText.setVisible(false);
        gameOverText.setTranslateX(width / 2 - 180); // Center based on text width
        gameOverText.setTranslateY(height / 3);
        uiRoot.getChildren().add(gameOverText);

        // Final score text
        finalScoreText = new Text();
        finalScoreText.setStyle("-fx-font-size: 25; -fx-fill: white; -fx-stroke: black; " +
                "-fx-stroke-width: 1px;");
        finalScoreText.setVisible(false);
        finalScoreText.setTranslateX(width / 2 - 80);
        finalScoreText.setTranslateY(height / 2);
        uiRoot.getChildren().add(finalScoreText);

        // Restart button
        restartButton = new Button("RESTART");
        restartButton.setLayoutX(width / 2 - 50);
        restartButton.setLayoutY(height / 2 + 50);
        restartButton.setVisible(false);
        uiRoot.getChildren().add(restartButton);

        return uiRoot;
    }

    // Getters for UI elements

    /**
     * @return The "Start" button.
     */
    public Button getStartButton() { return startButton; }

    /**
     * @return The "Restart" button.
     */
    public Button getRestartButton() { return restartButton; }

//    /**
//     * @return The instruction text display.
//     */
//    public Text getInstructions() { return instructions; }
//
//    /**
//     * @return The "Game Over" text display.
//     */
//    public Text getGameOverText() { return gameOverText; }
//
//    /**
//     * @return The final score text shown after the game ends.
//     */
//    public Text getFinalScoreText() { return finalScoreText; }

    // UI update methods

    /**
     * Updates the number of visible full hearts based on remaining lives.
     *
     * @param hearts The number of remaining lives (0–5).
     */
    public void updateHearts(int hearts) {
        for (int i = 0; i < 5; i++) {
            heartIcons.get(i).setFull(i < hearts);
        }
    }

    /**
     * Updates the score display text.
     *
     * @param score The number of obstacles jumped over.
     */
    public void updateScore(int score) {
        scoreText.setText("Obstacles: " + score);
    }

    /**
     * Shows the game over screen with final score and restart option.
     *
     * @param finalScore The final score to display.
     */
    public void showGameOver(int finalScore) {
        gameOverText.setVisible(true);
        finalScoreText.setText("Final Score: " + finalScore);
        finalScoreText.setVisible(true);
        restartButton.setVisible(true);
    }

    /**
     * Hides the game over screen and final score.
     */
    public void hideGameOver() {
        gameOverText.setVisible(false);
        finalScoreText.setVisible(false);
        restartButton.setVisible(false);
    }

    /**
     * Shows the start screen with instructions and the start button.
     */
    public void showStartScreen() {
        startButton.setVisible(true);
        instructions.setVisible(true);
    }

    /**
     * Hides the start screen UI elements.
     */

    public void hideStartScreen() {
        startButton.setVisible(false);
        instructions.setVisible(false);
    }
}
