package lab6;

import javafx.scene.image.ImageView;

public class Player {
    private final ImageView sprite = new ImageView();
    private Animation idleAnim, runAnim, jumpAnim, fallAnim;
    private State state = State.IDLE;

    private double velocityY = 0;
    private boolean isOnGround = true;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public Player() {
        double width = 32, height = 32;
        idleAnim = new Animation(sprite, AssetLoader.load("idle.png"), width, height, 4, 0.15, true);
        runAnim = new Animation(sprite, AssetLoader.load("run.png"), width, height, 6, 0.1, true);
        jumpAnim = new Animation(sprite, AssetLoader.load("jump.png"), width, height, 1, 0.2, false);
        fallAnim = new Animation(sprite, AssetLoader.load("fall.png"), width, height, 2, 0.2, false);
        setState(State.IDLE);
        sprite.setTranslateX(100);
        sprite.setTranslateY(400);
    }

    public ImageView getSprite() {
        return sprite;
    }

    public void update(double deltaTime) {
        double gravity = 1000;
        velocityY += gravity * deltaTime;
        sprite.setTranslateY(sprite.getTranslateY() + velocityY * deltaTime);

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
//    private enum State { IDLE, RUNNING, JUMPING, FALLING }
//
//    private final ImageView sprite;
//    private State currentState = State.IDLE;
//
//    // Animation objects
//    private final Animation idleAnimation;
//    private final Animation runAnimation;
//    private final Animation jumpAnimation;
//    private final Animation fallingAnimation;
//
//    // Physics
//    private double velocityY = 0;
//    private boolean isOnGround = true;
//
//    public Player() {
//        Image idleSheet = loadImage("/sprites/idle.png");
//        Image runSheet = loadImage("/sprites/run.png");
//        Image jumpSheet = loadImage("/sprites/jump.png");
//        Image fallSheet = loadImage("/sprites/fall.png");
//
//        this.sprite = new ImageView(idleSheet);
//        // sprite width
//        double frameWidth = 32;
//        // sprite height
//        double frameHeight = 32;
//        this.sprite.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
//
//        this.idleAnimation = new Animation(sprite, idleSheet, frameWidth, frameHeight, 3, 0.1, true);
//        this.runAnimation = new Animation(sprite, runSheet, frameWidth, frameHeight, 3, 0.08, true);
//        this.jumpAnimation = new Animation(sprite, jumpSheet, frameWidth, frameHeight, 3, 0.06, true);
//        this.fallingAnimation = new Animation(sprite, fallSheet, frameWidth, frameHeight, 3, 0.06, false);
//    }
//
//    private Image loadImage(String path) {
//        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
//        if (img.isError()) {
//            throw new RuntimeException("Failed to load: " + path);
//        }
//        return img;
//    }
//
//    public void update(double deltaTime) {
//        double gravity = 1000;
//        velocityY += gravity * deltaTime;
//        sprite.setTranslateY(sprite.getTranslateY() + velocityY *  deltaTime); // Move the sprite vertically
//
//        // Landing check
//        if (sprite.getTranslateY() >= 400) {
//            sprite.setTranslateY(400);
//            velocityY = 0;
//            isOnGround = true;
//
//            if (isMoving()) {
//                setState(State.RUNNING);
//            } else {
//                setState(State.IDLE);
//            }
//        } else {
//            isOnGround = false;
//            if (velocityY < 0) {
//                setState(State.JUMPING);
//            } else {
//                setState(State.FALLING);
//            }
//        }
//
//        getCurrentAnimation().update(deltaTime);
//    }
//
//    public void move(double direction) {
//        double newX = Math.round(sprite.getTranslateX() + direction * 5);
//        sprite.setTranslateX(newX);
//        if (direction != 0) {
//            sprite.setScaleX(direction > 0 ? 1 : -1);
//        }
//    }
//
//    private boolean isMoving() {
//        return sprite.getTranslateX() != 0;
//    }
//
//    public void jump() {
//        if (isOnGround) {
//            velocityY = -500;
//            isOnGround = false;
//            setState(State.JUMPING);
//        }
//    }
//
//    private void setState(State newState) {
//        if (currentState != newState) {
//            currentState = newState;
//            getCurrentAnimation().reset();
//        }
//    }
//
//    private Animation getCurrentAnimation() {
//        return switch (currentState) {
//            case RUNNING -> runAnimation;
//            case JUMPING -> jumpAnimation;
//            case FALLING -> fallingAnimation;
//            default -> idleAnimation;
//        };
//    }
//
//    public ImageView getSprite() {
//        return sprite;
//    }
//}