package lab6;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Animation {
    private final ImageView sprite;
    private final Image spriteSheet;
    private final double frameWidth;
    private final double frameHeight;
    private final int frameCount;
    private final double frameDuration;
    private final boolean loop;

    private int currentFrame = 0;
    private double elapsedTime = 0;

    public Animation(ImageView sprite, Image spriteSheet, double frameWidth, double frameHeight, int frameCount, double frameDuration, boolean loop) {
        this.sprite = sprite;
        this.spriteSheet = spriteSheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.frameDuration = frameDuration;
        this.loop = loop;

        sprite.setImage(spriteSheet);
        updateViewport();
    }

    public void update(double deltaTime) {
        if (!loop && currentFrame == frameCount -1) {
            return;
        }

        elapsedTime += deltaTime;

        while (elapsedTime >= frameDuration) {
            currentFrame++;
            elapsedTime -= frameDuration;

            if (currentFrame >= frameCount) {
                currentFrame = loop ? 0 : frameCount - 1;
            }
            updateViewport();
        }
    }

    public void reset() {
        sprite.setImage(spriteSheet);
        currentFrame = 0;
        elapsedTime = 0;
        updateViewport();
    }

    private void updateViewport() {
        // Calculate the X position of the current frame
        double x = currentFrame * frameWidth;

        // Optional safety check:
        if (x + frameWidth > sprite.getImage().getWidth()) {
            System.out.println("⚠️ Frame outside image bounds: " + x + "px");
            x = 0; // fallback to first frame
        }
        sprite.setViewport(new Rectangle2D(x, 0, frameWidth, frameHeight));
    }
}