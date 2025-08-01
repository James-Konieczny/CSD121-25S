package lab6;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Heart extends ImageView {
    public Heart(boolean full) {
        try {
            String imagePath = full ? "/sprites/fullheart.png" : "/sprites/emptyheart.png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));

            if (image.isError()) {
                throw new RuntimeException("Failed to load heart image: " + imagePath);
            }

            setImage(image);
            setFitWidth(30);
            setFitHeight(30);
        } catch (NullPointerException e) {
            System.err.println("Heart image not found! Check your resources folder.");
            // Create a placeholder
            setFitWidth(30);
            setFitHeight(30);
            setStyle("-fx-background-color: " + (full ? "red" : "gray") + ";");
        }
    }
}
