package lab3.game;

/**
 * Enum representing the columns of the TicTacToe board.
 */
public enum Column {
    LEFT, MIDDLE, RIGHT;

    /**
     * Parses a string into a Column enum value.
     * Accepts: 1, l, left -> LEFT
     *          2, m, c, middle, center -> MIDDLE
     *          3, r, right -> RIGHT
     *
     * @param input the string input
     * @return the corresponding Column enum value
     */
    public static Column parse(String input) {
        input = input.trim().toLowerCase();
        return switch (input) { // added full string cases (left, middle, center, right) - not in instructions, but thought it was a good idea
            case "1", "l", "left" -> LEFT;
            case "2", "m", "c", "middle", "center" -> MIDDLE;
            case "3", "r", "right" -> RIGHT;
            default -> throw new IllegalArgumentException("Invalid column input: " + input);
        };
    }

    /**
     * Converts the column into an index from 0 to 2.
     *
     * @return int index for array access
     */
    public int toIndex() {
        return switch (this) {
            case LEFT -> 0;
            case MIDDLE -> 1;
            case RIGHT -> 2;
        };
    }
}