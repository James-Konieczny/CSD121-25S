package lab6;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class Animation {
    private final ImageView sprite;
    private final double frameWidth;
    private final double frameHeight;
    private final int frameCount;
    private final double frameDuration;

    private int currentFrame = 0;
    private long lastUpdate = 0;

    public Animation(ImageView sprite, double frameWidth, double frameHeight,
                     int frameCount, double frameDuration) {
        this.sprite = sprite;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.frameDuration = frameDuration;
    }

    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastUpdate > frameDuration * 1000) {
            currentFrame = (currentFrame + 1) % frameCount;
            updateViewport();
            lastUpdate = now;
        }
    }

    private void updateViewport() {
        // Calculate the X position of the current frame
        double x = currentFrame * frameWidth;
        sprite.setViewport(new Rectangle2D(x, 0, frameWidth, frameHeight));
    }
}