package lab6;

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * AssetLoader is a utility class responsible for loading image resources from the game's sprites folder.
 */
public class AssetLoader {

    /**
     * Loads an image from the /sprites/ directory inside the project resources.
     *
     * @param fileName The name of the image file (e.g., "player.png").
     * @return An {@link Image} object representing the loaded file.
     * @throws NullPointerException if the resource is not found.
     */
    public static Image load(String fileName) {
        return new Image(Objects.requireNonNull(AssetLoader.class.getResource("/sprites/" + fileName)).toExternalForm());
    }
}