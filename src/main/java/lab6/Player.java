package lab6;

import javafx.scene.image.ImageView;

/**
 * The Player class represents the main character controlled by the user.
 * It manages position, movement, state (idle, running, jumping, falling), and animation frames.
 */
public class Player {
    private final ImageView sprite = new ImageView();
    private final Animation idleAnim;
    private final Animation runAnim;
    private final Animation jumpAnim;
    private final Animation fallAnim;
    private State state = State.IDLE;

    private double velocityY = 0;
    private boolean isOnGround = true;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public static final double WIDTH = 32;
    public static final double HEIGHT = 32;

    /**
     * Constructs a new Player and initializes all animations.
     * Sets the default position and state of the player.
     */
    public Player() {
        idleAnim = new Animation(sprite, AssetLoader.load("idle.png"), WIDTH, HEIGHT, 3, 0.15, true);
        runAnim = new Animation(sprite, AssetLoader.load("run.png"), WIDTH, HEIGHT, 3, 0.1, true);
        jumpAnim = new Animation(sprite, AssetLoader.load("jump.png"), WIDTH, HEIGHT, 4, 0.2, false);
        fallAnim = new Animation(sprite, AssetLoader.load("fall.png"), WIDTH, HEIGHT, 3, 0.1, false);
        setState(State.FALLING);
        sprite.setTranslateX(100);
        sprite.setTranslateY(200);
    }

    /**
     * Returns the ImageView representing the player's sprite.
     *
     * @return the player's ImageView
     */
    public ImageView getSprite() {
        return sprite;
    }

    /**
     * Updates the player's position, velocity, animation, and handles physics.
     *
     * @param deltaTime    Time elapsed since last frame (in seconds)
     * @param sceneWidth   Width of the game scene
     * @param sceneHeight  Height of the game scene
     */
    public void update(double deltaTime, double sceneWidth, double sceneHeight) {
        double gravity = 1000;
        velocityY += gravity * deltaTime;
        sprite.setTranslateY(sprite.getTranslateY() + velocityY * deltaTime);

        double currentY = sprite.getTranslateY();
        double currentX = sprite.getTranslateX();

        if (currentX < 0) {
            sprite.setTranslateX(0);
        } else if (currentX > sceneWidth - WIDTH) {
            sprite.setTranslateX(sceneWidth - WIDTH);
        }

        if  (currentY < 0) {
            sprite.setTranslateY(0);
            velocityY = 0;
        }

        if (sprite.getTranslateY() >= 400) {
            sprite.setTranslateY(400);
            velocityY = 0;
            isOnGround = true;

            setState(isMoving() ? State.RUNNING : State.IDLE);
        } else {
            isOnGround = false;
            setState(velocityY < 0 ? State.JUMPING : State.FALLING);
        }

        if (movingLeft) {
            sprite.setTranslateX(sprite.getTranslateX() - 200 * deltaTime);
            sprite.setScaleX(-1);
        } else if (movingRight) {
            sprite.setTranslateX(sprite.getTranslateX() + 200 * deltaTime);
            sprite.setScaleX(1);
        }

        getCurrentAnimation().update(deltaTime);
    }

    /**
     * Starts or stops moving the player left.
     *
     * @param isPressed true if key is pressed, false if released
     */
    public void moveLeft(boolean isPressed) {
        movingLeft = isPressed;
    }

    /**
     * Starts or stops moving the player right.
     *
     * @param isPressed true if key is pressed, false if released
     */
    public void moveRight(boolean isPressed) {
        movingRight = isPressed;
    }

    /**
     * Makes the player jump if they are on the ground.
     */
    public void jump() {
        if (isOnGround) {
            velocityY = -500;
            isOnGround = false;
            setState(State.JUMPING);
        }
    }

    /**
     * Checks if the player is currently moving left or right.
     *
     * @return true if moving, false otherwise
     */
    private boolean isMoving() {
        return movingLeft || movingRight;
    }

    /**
     * Checks if the player collides with a given obstacle.
     *
     * @param obstacle the obstacle to check against
     * @return true if the player and obstacle overlap, false otherwise
     */
    public boolean collidesWith(Obstacle obstacle) {
        return sprite.getBoundsInParent().intersects(obstacle.getBoundsInParent());
    }

    /**
     * Changes the player's state and resets animation if state has changed.
     *
     * @param newState the new movement state to set
     */
    private void setState(State newState) {
        if (state != newState) {
            state = newState;
            getCurrentAnimation().reset();
        }
    }

    /**
     * Returns the currently active animation based on the player's state.
     *
     * @return the current Animation object
     */
    private Animation getCurrentAnimation() {
        return switch (state) {
            case RUNNING -> runAnim;
            case JUMPING -> jumpAnim;
            case FALLING -> fallAnim;
            default -> idleAnim;
        };
    }
}