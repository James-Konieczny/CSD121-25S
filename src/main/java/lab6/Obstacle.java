package lab6;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The Obstacle class is an abstract base class for obstacles in the game.
 * It provides shared behavior like movement, sprite handling, and "passed" tracking.
 * Subclasses like {@code Block} and {@code Spike} define specific obstacle types.
 * This class extends {@link ImageView}, so each obstacle is also a visual object.
 */
public abstract class Obstacle extends ImageView {
    protected double speed;
    protected boolean passed = false;

    /**
     * Constructs an Obstacle with a given image and movement speed.
     *
     * @param imagePath the path to the image file used for the obstacle
     * @param speed     the speed at which the obstacle moves (pixels per second)
     */
    public Obstacle(String imagePath, double speed) {
        super(new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream(imagePath))));
        this.speed = speed;
        setFitWidth(50);
        setFitHeight(50);
    }

    /**
     * Updates the obstacle's position on the screen by moving it leftward.
     *
     * @param deltaTime the time (in seconds) since the last frame
     */
    public void update(double deltaTime) {
        setTranslateX(getTranslateX() - speed * deltaTime);
    }

    /**
     * Returns whether the player has already passed this obstacle.
     *
     * @return true if the obstacle has been passed, false otherwise
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * Marks this obstacle as having been passed by the player.
     * Used to prevent scoring the same obstacle multiple times.
     */
    public void markPassed() {
        passed = true;
    }
}
