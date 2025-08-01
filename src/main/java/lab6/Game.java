package lab6;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {
    private final Pane root = new Pane();
    private final Scene scene = new Scene(root, 800, 600);
    private Player player = new Player();
    private double lastTime;
    private boolean gameRunning = false;
    private int hearts = 5;
    private int obstaclesJumped = 0;

    // UI Elements
    private List<Heart> heartIcons = new ArrayList<>();
    private Text scoreText;
    private Button startButton;
    private Text gameOverText;
    private Text finalScoreText;
    private Button restartButton;

    // Obstacles
    private List<Obstacle> obstacles = new ArrayList<>();
    private double obstacleTimer = 0;
    private Random random = new Random();

    public boolean isGameRunning() {
        return gameRunning;
    }

    public Game() {
        createUI();
        resetGame();
        new InputHandler(scene, player, this);
    }

    private void createUI() {
        for (int i = 0; i < 5; i++) {
            Heart heart = new Heart(true);
            heart.setTranslateX(10 + i * 35);
            heart.setTranslateY(10);
            heartIcons.add(heart);
            root.getChildren().add(heart);
        }

        scoreText = new Text("Obstacles: 0");
        scoreText.setTranslateX(700);
        scoreText.setTranslateY(30);
        root.getChildren().add(scoreText);

        startButton = new Button("Start");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);
        startButton.setOnAction(e -> startGame());
        root.getChildren().add(startButton);

        gameOverText = new Text("Game Over");
        gameOverText.setStyle("-fx-font-size: 40; -fx-fill: red;");
        gameOverText.setVisible(false);
        gameOverText.setTranslateX(300);
        gameOverText.setTranslateY(250);
        root.getChildren().add(gameOverText);

        finalScoreText = new Text();
        finalScoreText.setStyle("-fx-font-size: 24;");
        finalScoreText.setVisible(false);
        finalScoreText.setTranslateX(320);
        finalScoreText.setTranslateY(300);
        root.getChildren().add(finalScoreText);

        restartButton = new Button("RESTART");
        restartButton.setLayoutX(350);
        restartButton.setLayoutY(350);
        restartButton.setVisible(false);
        restartButton.setOnAction(e -> resetGame());
        root.getChildren().add(restartButton);
    }

    private void resetGame() {
        gameRunning = false;
        hearts = 5;
        obstaclesJumped = 0;
        obstacles.clear();
        root.getChildren().remove(player != null ? player.getSprite() : null);

        player = new Player();
        root.getChildren().add(player.getSprite());

        new InputHandler(scene, player, this);

        updateHearts();
        updateScore();
        startButton.setVisible(true);
        gameOverText.setVisible(false);
        finalScoreText.setVisible(false);
        restartButton.setVisible(false);

        for (Obstacle obstacle : obstacles) {
            root.getChildren().remove(obstacle);
        }
        obstacles.clear();
    }

    void startGame() {
        gameRunning = true;
        startButton.setVisible(false);
    }

    private void gameOver() {
        gameRunning = false;
        gameOverText.setVisible(true);
        finalScoreText.setText("Score: " + obstaclesJumped);
        finalScoreText.setVisible(true);
        restartButton.setVisible(true);
    }

    private void updateHearts() {
        for (int i = 0; i < 5; i++) {
            heartIcons.get(i).setImage(new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream(
                    i < hearts ? "/sprites/fullheart.png" : "/sprites/emptyheart.png"
            ))));
        }
    }

    private void updateScore() {
        scoreText.setText("Obstacles: " + obstaclesJumped);
    }

    public Scene getScene() {
        return scene;
    }

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
                            updateScore();
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
    private void generateObstacle() {
        Obstacle obstacle = random.nextBoolean() ?
                new Block(200) : new Spike(200); // 200 = speed

        obstacle.setTranslateX(850); // Start just off-screen to the right
        obstacle.setTranslateY(400 - obstacle.getFitHeight()); // Ground level

        obstacles.add(obstacle);
        root.getChildren().add(obstacle);
    }
    private void handleCollision(Obstacle obstacle) {
        root.getChildren().remove(obstacle);
        obstacles.remove(obstacle);

        hearts--;
        updateHearts();

        if (hearts <= 0) {
            gameOver();
        }
    }
}