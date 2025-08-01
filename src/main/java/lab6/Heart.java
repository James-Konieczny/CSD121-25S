package lab6;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a heart icon used for displaying player health.
 */
public class Heart extends ImageView {
    private static final Image FULL_HEART = AssetLoader.load("fullheart.png");
    private static final Image EMPTY_HEART = AssetLoader.load("emptyheart.png");

    /**
     * Constructs a Heart icon, either full or empty.
     * @param full whether the heart is full
     */
    public Heart(boolean full) {
        super(full ? FULL_HEART : EMPTY_HEART);
        setFitWidth(30);
        setFitHeight(30);
    }

    /**
     * Sets the heart to full or empty.
     * @param full true to display a full heart, false for empty
     */
    public void setFull(boolean full) {
        setImage(full ? FULL_HEART : EMPTY_HEART);
    }
}
