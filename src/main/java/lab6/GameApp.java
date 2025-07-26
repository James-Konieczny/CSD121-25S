package lab6;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameApp extends Application{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Pane gameRoot = new Pane();
    private Player player;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Starting game..."); // Debug line

        player = new Player();
        System.out.println("Player sprite loaded: " + player.getSprite().getImage()); // Debug

        gameRoot.getChildren().add(player.getSprite());

        // Debug position
        player.getSprite().setTranslateX(100); // Temporary fixed position
        player.getSprite().setTranslateY(100);
        System.out.println("Sprite position: " + player.getSprite().getTranslateX() + "," +
                player.getSprite().getTranslateY());

        Scene scene = new Scene(gameRoot, WIDTH, HEIGHT);
        scene.setFill(Color.WHITE); // Make background black to contrast
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("Window shown"); // Debug

        createGameLoop();
    }
    private void createGameLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        }.start();
    }

    private void update() {
        player.update();
    }
}