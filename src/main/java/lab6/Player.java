package lab6;

import javafx.scene.image.ImageView;

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

    public Player() {
        idleAnim = new Animation(sprite, AssetLoader.load("idle.png"), WIDTH, HEIGHT, 3, 0.15, true);
        runAnim = new Animation(sprite, AssetLoader.load("run.png"), WIDTH, HEIGHT, 3, 0.1, true);
        jumpAnim = new Animation(sprite, AssetLoader.load("jump.png"), WIDTH, HEIGHT, 4, 0.2, false);
        fallAnim = new Animation(sprite, AssetLoader.load("fall.png"), WIDTH, HEIGHT, 3, 0.1, false);
        setState(State.FALLING);
        sprite.setTranslateX(100);
        sprite.setTranslateY(200);
    }

    public ImageView getSprite() {
        return sprite;
    }

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

    public void moveLeft(boolean isPressed) {
        movingLeft = isPressed;
    }

    public void moveRight(boolean isPressed) {
        movingRight = isPressed;
    }

    public void jump() {
        if (isOnGround) {
            velocityY = -500;
            isOnGround = false;
            setState(State.JUMPING);
        }
    }

    private boolean isMoving() {
        return movingLeft || movingRight;
    }

    public boolean collidesWith(Obstacle obstacle) {
        return sprite.getBoundsInParent().intersects(obstacle.getBoundsInParent());
    }

    private void setState(State newState) {
        if (state != newState) {
            state = newState;
            getCurrentAnimation().reset();
        }
    }

    private Animation getCurrentAnimation() {
        return switch (state) {
            case RUNNING -> runAnim;
            case JUMPING -> jumpAnim;
            case FALLING -> fallAnim;
            default -> idleAnim;
        };
    }
}