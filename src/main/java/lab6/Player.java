package lab6;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;


public class Player {
    private final ImageView sprite;
    private final double frameWidth = 64;  // Your sprite width
    private final double frameHeight = 69; // Your sprite height
    private final Animation idleAnimation;

    public Player() {
        Image spriteSheet;
        try {
            // Get resource URL first for debugging
            java.net.URL imgUrl = getClass().getResource("/sprites/player-idle.png");
            System.out.println("Loading image from: " + imgUrl); // Debug line

            if (imgUrl == null) {
                throw new RuntimeException("Sprite sheet not found at /sprites/player-idle.png");
            }

            spriteSheet = new Image(imgUrl.toExternalForm());
        } catch (Exception e) {
            System.err.println("Failed to load sprite sheet:");
            e.printStackTrace();
            throw e; // Re-throw to see the error
        }

        this.sprite = new ImageView(spriteSheet);
        this.sprite.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
        this.idleAnimation = new Animation(sprite, frameWidth, frameHeight, 2, 0.15);
        
    }

    public void update() {
        idleAnimation.update();
    }

    public ImageView getSprite() {
        return sprite;
    }

    public double getWidth() {
        return frameWidth;
    }

    public double getHeight() {
        return frameHeight;
    }
}