package lab6;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public abstract class Obstacle extends ImageView {
    protected double speed;
    protected boolean passed = false;

    public Obstacle(String imagePath, double speed) {
        super(new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream(imagePath))));
        this.speed = speed;
        setFitWidth(50);
        setFitHeight(50);
    }

    public void update(double deltaTime) {
        setTranslateX(getTranslateX() - speed * deltaTime);
    }

    public boolean isPassed() {
        return passed;
    }

    public void markPassed() {
        passed = true;
    }
}
