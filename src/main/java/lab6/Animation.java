package lab6;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * The Animation class controls frame-based sprite animations using a sprite sheet.
 * It changes the visible portion of an image (viewport) over time to create motion.
 */
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

    /**
     * Constructs an Animation with the specified parameters.
     *
     * @param sprite        The ImageView that will display the animation.
     * @param spriteSheet   The sprite sheet image with all animation frames.
     * @param frameWidth    The width of each frame in the sprite sheet.
     * @param frameHeight   The height of each frame in the sprite sheet.
     * @param frameCount    The total number of frames in the animation.
     * @param frameDuration The duration (in seconds) of each frame.
     * @param loop          Whether the animation should repeat after finishing.
     */
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

    /**
     * Updates the animation based on the time since the last frame.
     * If enough time has passed, it advances to the next frame.
     *
     * @param deltaTime Time (in seconds) since the last update call.
     */
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

    /**
     * Resets the animation to the first frame and restarts timing.
     * Useful when changing the animation state (e.g., from idle to jump).
     */
    public void reset() {
        sprite.setImage(spriteSheet);
        currentFrame = 0;
        elapsedTime = 0;
        updateViewport();
    }

    /**
     * Calculates and sets the visible portion (viewport) of the sprite image
     * based on the current frame.
     */
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