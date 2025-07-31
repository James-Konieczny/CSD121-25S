package lab6;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Game {
    private final Pane root = new Pane();
    private final Scene scene = new Scene(root, 800, 600);
    private final Player player = new Player();
    private double lastTime;

    public Game() {
        root.getChildren().add(player.getSprite());
        new InputHandler(scene, player);
    }

    public Scene getScene() {
        return scene;
    }

    public void start() {
        lastTime = System.nanoTime() / 1e9;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double currentTime = now / 1e9;
                double deltaTime = currentTime - lastTime;
                lastTime = currentTime;

                player.update(deltaTime);
                // TODO: Update obstacles and collision here
            }
        };
        timer.start();
    }
}

//    public static final int WIDTH = 800;
//    public static final int HEIGHT = 600;
//
//    private final Pane gameRoot = new Pane();
//    private Player player;
//
//    private long lastUpdateTime = 0;
//
//    @Override
//    public void start(Stage primaryStage) {
//        player = new Player();
//        gameRoot.getChildren().add(player.getSprite());
//        gameRoot.setFocusTraversable(true);
//
//        Pane root = new Pane();
//        Scene scene = new Scene(gameRoot, WIDTH, HEIGHT);
//        primaryStage.setScene(scene);
//        setupInput(primaryStage.getScene());
//        primaryStage.show();
//
//        createGameLoop();
//    }
//
//    private void createGameLoop() {
//        new AnimationTimer() {
//
//            @Override
//            public void handle(long now) {
//                if (lastUpdateTime == 0) {
//                    lastUpdateTime = now;
//                    return;
//                }
//                double deltaTime = (now - lastUpdateTime) / 1000000000.0;
//                lastUpdateTime = now;
//                player.update(deltaTime);
//            }
//        }.start();
//    }
//
//    private void setupInput(Scene scene){
//        scene.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.SPACE) {
//                player.jump();
//            } else if (event.getCode() == KeyCode.RIGHT) {
//                player.move(1);
//            } else if (event.getCode() == KeyCode.LEFT) {
//                player.move(-1);
//            }
//        });
//
//        scene.setOnKeyReleased(event -> {
//            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
//                player.move(0);
//            }
//        });
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}