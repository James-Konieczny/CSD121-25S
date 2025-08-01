package lab6;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * Handles player keyboard input for movement and game control.
 */
public class InputHandler {

    /**
     * Creates a new input handler to listen for keyboard events.
     *
     * @param scene The JavaFX scene where input is captured.
     * @param player The player object to move based on input.
     * @param game The game object to start or check state.
     */
    public InputHandler(Scene scene, Player player, Game game) {

        // When a key is pressed down
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) player.moveLeft(true); // Move player left
            if (e.getCode() == KeyCode.RIGHT) player.moveRight(true); // Move player right
            if (e.getCode() == KeyCode.UP) player.jump(); // Make player jump
            if (e.getCode() == KeyCode.ENTER && !game.isGameRunning()) {
                game.startGame(); // Start the game if not already running
            }
        });

        // When a key is released
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) player.moveLeft(false); // Stop moving left
            if (e.getCode() == KeyCode.RIGHT) player.moveRight(false); // Stop moving right
        });
    }
}