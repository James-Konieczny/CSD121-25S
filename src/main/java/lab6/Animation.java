package lab6;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Animation {
    private final ImageView sprite;
    private final double frameWidth;
    private final double frameHeight;
    private final int frameCount;
    private final double frameDuration;

    private int currentFrame = 0;
    private double elapsedTime = 0;

    public Animation(ImageView sprite, Image spriteSheet, double frameWidth, double frameHeight, int frameCount, double frameDuration) {
        this.sprite = sprite;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.frameDuration = frameDuration;

        sprite.setImage(spriteSheet);
        updateViewport();
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;

        while (elapsedTime >= frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount;
            elapsedTime -= frameDuration;
            updateViewport();
        }
    }

    public void reset() {
        currentFrame = 0;
        elapsedTime = 0;
        updateViewport();
    }

    private void updateViewport() {
        // Calculate the X position of the current frame
        double maxX = sprite.getImage().getWidth() - frameWidth;
        double x = Math.min(currentFrame * frameWidth, maxX);
        sprite.setViewport(new Rectangle2D(x, 0, frameWidth, frameHeight));
    }
}