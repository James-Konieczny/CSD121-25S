package lab6;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Game class manages the core logic, user interface, and animation
 * of a simple 2D side-scrolling JavaFX game.
 * It handles player input, game state, obstacles, and collision detection.
 */
public class Game {
    private final Pane root = new Pane();
    private final Scene scene = new Scene(root, 800, 600);
    private Player player = new Player();
    private double lastTime;
    private boolean gameRunning = false;
    private int hearts = 5;
    private int obstaclesJumped = 0;

    // UI Elements
    private final UIManager uiManager = new UIManager();

    // Obstacles
    private final List<Obstacle> obstacles = new ArrayList<>();
    private double obstacleTimer = 0;
    private final Random random = new Random();

    /**
     * Returns whether the game is currently running.
     *
     * @return true if the game is running, false otherwise
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Constructs the Game object and sets up initial UI and input.
     * Initializes event handlers for buttons and resets the game.
     */
    public Game() {
        root.getChildren().add(uiManager.createUI(scene.getWidth(), scene.getHeight()));
        uiManager.getStartButton().setOnAction(e -> startGame());
        uiManager.getRestartButton().setOnAction(e -> resetGame());
        resetGame();
        new InputHandler(scene, player, this);
    }

    /**
     * Resets the game to its initial state.
     * Clears obstacles, resets player and hearts, and shows the start screen.
     */
    private void resetGame() {
        gameRunning = false;
        hearts = 5;
        obstaclesJumped = 0;
        obstacles.clear();
        root.getChildren().remove(player != null ? player.getSprite() : null);

        player = new Player();
        root.getChildren().add(player.getSprite());

        for (Obstacle obstacle : obstacles) {
            root.getChildren().remove(obstacle);
        }
        obstacles.clear();

        uiManager.updateHearts(hearts);
        uiManager.updateScore(obstaclesJumped);
        uiManager.hideGameOver();
        uiManager.showStartScreen();

        new InputHandler(scene, player, this);
    }

    /**
     * Starts the game and hides the start screen.
     */
    void startGame() {
        gameRunning = true;
        uiManager.hideStartScreen();
    }

    /**
     * Ends the game, clears obstacles, and shows the game-over screen.
     */
    private void gameOver() {
        gameRunning = false;
        uiManager.showGameOver(obstaclesJumped);

        for (int i = obstacles.size() - 1; i >= 0; i--) {
            Obstacle obstacle = obstacles.get(i);
            root.getChildren().remove(obstacle);
            obstacles.remove(i);
        }
    }

    /**
     * Returns the JavaFX scene associated with this game.
     *
     * @return the game scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Generates a new obstacle and places it on the screen.
     * Randomly chooses between a Block and a Spike.
     */
    private void generateObstacle() {
        Obstacle obstacle = random.nextBoolean() ?
                new Block(200) : new Spike(200); // 200 = speed

        obstacle.setTranslateX(850); // Start just off-screen to the right
        obstacle.setTranslateY(425 - obstacle.getFitHeight()); // Ground level, minus obstacle height

        obstacles.add(obstacle);
        root.getChildren().add(obstacle);
    }

    /**
     * Handles collision between the player and an obstacle.
     * Reduces the player's hearts and ends the game if hearts reach zero.
     *
     * @param obstacle the obstacle that the player collided with
     */
    private void handleCollision(Obstacle obstacle) {
        root.getChildren().remove(obstacle);
        obstacles.remove(obstacle);

        hearts--;
        uiManager.updateHearts(hearts);

        if (hearts <= 0) {
            gameOver();
        }
    }

    /**
     * Starts the game loop using an AnimationTimer.
     * Handles obstacle spawning, player movement, and collision detection.
     */
    public void start() {
        lastTime = System.nanoTime() / 1e9;
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double currentTime = now / 1e9;
                double deltaTime = currentTime - lastTime;
                lastTime = currentTime;

                if (player != null) {
                    player.update(deltaTime, sceneWidth, sceneHeight);

                    if (gameRunning) {
                        obstacleTimer += deltaTime;
                        if (obstacleTimer > 1.5) {
                            generateObstacle();
                            obstacleTimer = 0;
                        }
                    }
                    for (int i = obstacles.size() - 1; i >= 0; i--) {
                        Obstacle obstacle = obstacles.get(i);
                        obstacle.update(deltaTime);

                        // Remove obstacles that go off-screen
                        if (obstacle.getTranslateX() < -100) {
                            root.getChildren().remove(obstacle);
                            obstacles.remove(i);
                        }
                        // Check if player jumped over obstacle
                        else if (!obstacle.isPassed() &&
                                obstacle.getTranslateX() < player.getSprite().getTranslateX()) {
                            obstacle.markPassed();
                            obstaclesJumped++;
                            uiManager.updateScore(obstaclesJumped);
                        }
                        // Check collision
                        else if (player.collidesWith(obstacle)) {
                            handleCollision(obstacle);
                        }
                    }
                }
            }
        };
        timer.start();
    }
}