package lab5.players;

import lab5.game.Board;
import lab5.game.Position;
import lab5.ui.Console;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }
    /**
     * Prompts the player to pick their next move.
     * @param currentBoard The current state of the game board
     * @return The position on the board where the player wants to place their token
     *
     * TODO: Make this an abstract method in an abstract class,
     *          and use this implementation in a HumanPlayer subclass
     */
    @Override
    public Position pickNextMove(Board currentBoard) {
        while (true) {
            var move = Console.promptForPosition(getName() + " pick your next move: ");
            if (currentBoard.isEmptyAt(move)) {
                return move;
            } else {
                Console.printAlert("That position is already taken.");
            }
        }
    }
}
