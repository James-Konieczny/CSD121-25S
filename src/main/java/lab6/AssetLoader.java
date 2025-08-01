package lab6;

import javafx.scene.image.Image;

public class AssetLoader {
    public static Image load(String fileName) {
        return new Image(AssetLoader.class.getResource("/sprites/" + fileName).toExternalForm());
    }
}