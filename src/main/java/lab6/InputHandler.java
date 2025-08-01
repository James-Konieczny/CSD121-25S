package lab6;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputHandler {

    public InputHandler(Scene scene, Player player, Game game) {

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) player.moveLeft(true);
            if (e.getCode() == KeyCode.RIGHT) player.moveRight(true);
            if (e.getCode() == KeyCode.UP) player.jump();
            if (e.getCode() == KeyCode.ENTER && !game.isGameRunning()) {
                game.startGame();
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) player.moveLeft(false);
            if (e.getCode() == KeyCode.RIGHT) player.moveRight(false);
        });
    }
}