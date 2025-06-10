package lab3.game;

/**
 * Enum representing the rows of the TicTacToe board.
 */
public enum Row {
    TOP, MIDDLE, BOTTOM;

    /**
     * Parses a string into a Row enum value.
     * Accepts: 1, t, top -> TOP
     *          2, m, c, middle, center -> MIDDLE
     *          3, b, bottom -> BOTTOM
     *
     * @param input the string input
     * @return the corresponding Row enum value
     */
    public static Row parse(String input) {
        input = input.trim().toLowerCase();
        return switch (input) { // added full string cases (top, middle, center, bottom) - not in instructions, but thought it was a good idea
            case "1", "t", "top" -> TOP;
            case "2", "m", "c", "middle", "center" -> MIDDLE;
            case "3", "b", "bottom" -> BOTTOM;
            default -> throw new IllegalArgumentException("Invalid row input: " + input);
        };
    }

    /**
     * Converts the row into an index from 0 to 2.
     *
     * @return int index for array access
     */
    public int toIndex() {
        return switch (this) {
            case TOP -> 0;
            case MIDDLE -> 1;
            case BOTTOM -> 2;
        };
    }
}