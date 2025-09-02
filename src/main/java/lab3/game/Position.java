package lab3.game;

/**
 * Record that represents a specific position on the board using Row and Column.
 */
public record Position(Row row, Column column) {
    /**
     * Parses two strings (row and column) into a Position.
     *
     * @param rowInput input representing the row
     * @param colInput input representing the column
     * @return the Position object
     */
    public static Position parse(String rowInput, String colInput) {
        return new Position(Row.parse(rowInput), Column.parse(colInput));
    }

    /**
     * Converts the position to a linear board index (0-8).
     *
     * @return int index
     */
    public int toIndex() {
        int rowIndex = row.toIndex();
        int colIndex = column.toIndex();
        return rowIndex * 3 + colIndex;
    }
}