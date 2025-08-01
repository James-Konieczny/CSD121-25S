package lab6;

/**
 * A block obstacle that extends the base Obstacle class.
 */
public class Block extends Obstacle {

    /**
     * Constructs a Block obstacle with a predefined sprite and speed.
     * @param speed the speed at which the block moves
     */
    public Block(double speed) {
        super("/sprites/block.png", speed);
    }
}
