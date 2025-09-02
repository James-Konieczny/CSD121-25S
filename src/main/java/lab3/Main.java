package lab3;

// TODO: Initialize a TicTacToe game with a 3x3 board and two players
// TODO: Display the board
// TODO: Check if the game is over or a draw, and if so display the result and exit
// TODO: Get the next move from the player and update the game state

import lab3.game.Board;
import lab3.ui.Console;
import lab3.game.Position;

/**
 * The entry point for the Tic Tac Toe console game.
 * Coordinates the game loop, alternating turns between players,
 * and uses the Console and Board classes to handle I/O and game logic.
 */
public class Main {

    /**
     * Starts the Tic Tac Toe game.
     * Initializes the game board and handles player turns until
     * a win or draw condition is met.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        Board board = new Board();
        Console console = new Console();
        int moveCount = 0;
        char currentPlayer = 'x';

        while (true) {
            console.printBoard(board.toString());

            Position position = console.promptForMove(currentPlayer);
            boolean validMove = board.placeMark(position, currentPlayer);

            if (!validMove) {
                System.out.println("That square is taken! Try again.");
                continue;
            }

            moveCount++;

            if (board.checkWin(currentPlayer)) {
                console.printBoard(board.toString());
                console.printWinMessage(currentPlayer);
                break;
            } else if (board.isDraw(moveCount)) {
                console.printBoard(board.toString());
                console.printDrawMessage();
                break;
            }

            currentPlayer = (currentPlayer == 'x') ? 'o' : 'x';
        }
    }
}