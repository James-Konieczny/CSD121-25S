package lab3.game;

// TODO: encapsulate the representation of the tictactoe board and provide instance methods to access and update it

/**
 * Class that represents the game board and contains logic for moves and win conditions.
 */
public class Board {
    private final char[][] board;

    /**
     * Constructs a new board with square labels 1-9.
     */
    public Board() {
        this.board = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
    }

    /**
     * Places a player's mark (X or O) on the board at the specified position.
     *
     * @param pos the position on the board where the player wants to place a mark;
     *           must not be {@code null}, and must be within bounds.
     * @param player the character representing the player ('X' or 'O')
     * @return {@code true} if the mark was successfully placed;
     *        {@code false} if the cell was already occupied
     */
    public boolean placeMark(Position pos, char player) {
        int row = rowToIndex(pos.row());
        int col = columnToIndex(pos.column());

        if (board[row][col] != ' ') {
            return false;
        }
        board[row][col] = player;
        return true;
    }


    /**
     * Checks if the specified player has won the game.
     *
     * @param mark the character representing the player ('X' or 'O')
     * @return {@code true} if the player has a winning combination on the board;
     *         {@code false} otherwise
     */
    public boolean checkWin(char mark) { // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == mark && board[i][1] == mark && board[i][2] == mark) ||
                    (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark)) {
                return true;
            }
        }

        return (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark) ||
                (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark);
    }

    /**
     * Checks if the game is a draw.
     *
     * @param moveCount the total number of moves so far
     * @return true if the game is a draw
     */
    public boolean isDraw(int moveCount) {
        return moveCount >= 9;
    }

    /**
     * Returns a string representation of the current board state.
     *
     * @return the board formatted as a string
     */
    @Override // overriding the toString() method inherited from the Object class
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                sb.append(board[r][c] == ' ' ? '-' : board[r][c]);
                if (c < 2) sb.append(" | ");
            }
            sb.append("\n");
            if (r < 2) sb.append("---------\n");
        }
        return sb.toString();
    }

    private int rowToIndex(Row row) {
        return switch (row) {
            case TOP -> 0;
            case MIDDLE -> 1;
            case BOTTOM -> 2;
        };
    }

    private int columnToIndex(Column column) {
        return switch (column) {
            case LEFT -> 0;
            case MIDDLE -> 1;
            case RIGHT -> 2;
        };
    }
}