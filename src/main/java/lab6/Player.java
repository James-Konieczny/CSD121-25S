package lab6;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

import java.util.Objects;


public class Player {
    private enum State { IDLE, RUNNING, JUMPING, FALLING }

    private final ImageView sprite;
    private State currentState = State.IDLE;

    // Animation objects
    private final Animation idleAnimation;
    private final Animation runAnimation;
    private final Animation jumpAnimation;
    private final Animation fallingAnimation;

    // Physics
    private double velocityY = 0;
    private boolean isOnGround = true;

    public Player() {
        Image idleSheet = loadImage("/sprites/idle.png");
        Image runSheet = loadImage("/sprites/run.png");
        Image jumpSheet = loadImage("/sprites/jump.png");
        Image fallSheet = loadImage("/sprites/fall.png");

        this.sprite = new ImageView(idleSheet);
        // sprite width
        double frameWidth = 32;
        // sprite height
        double frameHeight = 32;
        this.sprite.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));

        this.idleAnimation = new Animation(sprite, idleSheet, frameWidth, frameHeight, 9, 1);
        this.runAnimation = new Animation(sprite, runSheet, frameWidth, frameHeight, 8, 1);
        this.jumpAnimation = new Animation(sprite, jumpSheet, frameWidth, frameHeight, 12, 1);
        this.fallingAnimation = new Animation(sprite, fallSheet, frameWidth, frameHeight, 7, 1);
    }

    private Image loadImage(String path) {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        if (img.isError()) {
            throw new RuntimeException("Failed to load: " + path);
        }
        return img;
    }

    public void update(double deltaTime) {
        double gravity = 0.5;
        velocityY += gravity * deltaTime * 60;
        double newY = Math.round(sprite.getTranslateY() + velocityY * deltaTime);
        sprite.setTranslateY(newY);

        // Landing check
        if (sprite.getTranslateY() >= 400) {
            sprite.setTranslateY(400);
            velocityY = 0;
            isOnGround = true;
            setState(isMoving() ? State.RUNNING : State.IDLE);
        }

        if (velocityY < 0) {
            setState(State.JUMPING);
        } else if (velocityY > 0 && !isOnGround) {
            setState(State.FALLING);
        } else if (sprite.getTranslateX() != 0) {
            setState(State.RUNNING);
        } else {
            setState(State.IDLE);
        }

        getCurrentAnimation().update(deltaTime);
    }

    public void move(double direction) {
        double newX = Math.round(sprite.getTranslateX() + direction * 5);
        sprite.setTranslateX(newX);
        if (direction != 0) {
            sprite.setScaleX(direction > 0 ? 1 : -1);
        }
    }

    private boolean isMoving() {
        return sprite.getTranslateX() != 0;
    }

    public void jump() {
        if (isOnGround) {
            velocityY = -15;
            isOnGround = false;
            setState(State.JUMPING);
        }
    }

    private void setState(State newState) {
        if (currentState != newState) {
            currentState = newState;
            getCurrentAnimation().reset();
        }
    }

    private Animation getCurrentAnimation() {
        return switch (currentState) {
            case RUNNING -> runAnimation;
            case JUMPING -> jumpAnimation;
            case FALLING -> fallingAnimation;
            default -> idleAnimation;
        };
    }

    public ImageView getSprite() {
        return sprite;
    }
}