package lab6;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class GameApp extends Application{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private final Pane gameRoot = new Pane();
    private Player player;

    private long lastUpdateTime = 0;

    @Override
    public void start(Stage primaryStage) {
        player = new Player();
        gameRoot.getChildren().add(player.getSprite());
        gameRoot.setFocusTraversable(true);

        Pane root = new Pane();
        Scene scene = new Scene(gameRoot, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        setupInput(primaryStage.getScene());
        primaryStage.show();

        createGameLoop();
    }

    private void createGameLoop() {
        new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (lastUpdateTime == 0) {
                    lastUpdateTime = now;
                    return;
                }
                double deltaTime = (now - lastUpdateTime) / 1000000000.0;
                lastUpdateTime = now;
                player.update(deltaTime);
            }
        }.start();
    }

    private void setupInput(Scene scene){
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                player.jump();
            } else if (event.getCode() == KeyCode.RIGHT) {
                player.move(1);
            } else if (event.getCode() == KeyCode.LEFT) {
                player.move(-1);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                player.move(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}