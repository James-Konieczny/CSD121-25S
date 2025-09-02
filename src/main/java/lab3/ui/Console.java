package lab3.ui;

// TODO: create a CLASS method to display a tictactoe board
// TODO: create a CLASS method to get the next move from a player

import lab3.game.Position;
import lab3.game.Row;
import lab3.game.Column;
import java.util.Scanner;

/**
 * Handles all user input and output for the TicTacToe game.
 */
public class Console {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to enter a move and returns a Position object.
     * Continues asking until a valid input is received.
     */
    public Position promptForMove(char currentPlayer) {
        while (true) {
            System.out.printf("Enter your move (e.g. '1 3' or 'm r'), player %s: ", currentPlayer);
            String input = scanner.nextLine().trim().toLowerCase();

            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Please enter exactly two values (e.g. '1 3' or 'm r')");
                continue;
            }

            try {
                Row row = Row.parse(parts[0]);
                Column col = Column.parse(parts[1]);
                return new Position(row, col);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid row or column. Try again.");
            }
        }
    }

    /**
     * Prints the current game board to the console.
     * @param boardStr the string representation of the board
     */
    public void printBoard(String boardStr) {
        System.out.println(boardStr);
    }

    /**
     * Prints a victory message to the console.
     */
    public void printWinMessage(char player) {
        System.out.printf("Player %s won! Congratulations!\n", player);
    }

    /**
     * Prints a tied message to the console.
     */
    public void printDrawMessage() {
        System.out.println("It's a draw!");
    }
}