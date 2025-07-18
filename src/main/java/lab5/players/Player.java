package lab5.players;

import lab5.game.Board;
import lab5.game.Position;
import lab5.ui.Console;

/**
 * Represents a player in the game.
 *
 * TODO: Make this an abstract class with various subclasses for different types of players
 */
public abstract class Player {

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public abstract Position pickNextMove(Board currentBoard);

}
