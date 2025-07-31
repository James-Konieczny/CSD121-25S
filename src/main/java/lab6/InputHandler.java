package lab6;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputHandler {
    public InputHandler(Scene scene, Player player) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) player.moveLeft(true);
            if (e.getCode() == KeyCode.RIGHT) player.moveRight(true);
            if (e.getCode() == KeyCode.SPACE) player.jump();
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) player.moveLeft(false);
            if (e.getCode() == KeyCode.RIGHT) player.moveRight(false);
        });
    }
}