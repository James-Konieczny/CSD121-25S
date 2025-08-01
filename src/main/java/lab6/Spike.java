package lab6;

/**
 * A spike obstacle that extends the base Obstacle class.
 */
public class Spike extends Obstacle {

    /**
     * Constructs a Spike obstacle with a predefined sprite and speed.
     * @param speed the speed at which the spike moves
     */
    public Spike(double speed) {
        super("/sprites/spike.png", speed);
    }
}
