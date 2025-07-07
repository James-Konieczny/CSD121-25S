package lab4.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testInitialBoardIsEmptyAndInProgress() {
        Board board = new Board();
        assertEquals(Board.Status.InProgress, board.getStatus());
        assertFalse(board.isFull());

        for (Row row : Row.values()) {
            for (Col col : Col.values()) {
                Position pos = new Position(row, col);
                assertFalse(board.isOccupiedAt(pos));
            }
        }
    }
    @Test
    public void testPlaceXAndO() {
        Board board = new Board();
        Position center = new Position(Row.Middle, Col.Middle);

        board.placeX(center);
        assertTrue(board.isOccupiedAt(center));
        assertEquals(Board.Status.InProgress, board.getStatus());

        Board board2 = new Board();
        Position corner = new Position(Row.Top, Col.Left);
        board2.placeO(corner);
        assertTrue(board2.isOccupiedAt(corner));
        assertEquals(Board.Status.InProgress, board2.getStatus());
    }
    @Test
    public void testDrawCondition() {
        Board board = new Board();

        board.placeX(new Position(Row.Top, Col.Left));
        board.placeO(new Position(Row.Top, Col.Middle));
        board.placeX(new Position(Row.Top, Col.Right));

        board.placeO(new Position(Row.Middle, Col.Left));
        board.placeX(new Position(Row.Middle, Col.Middle));
        board.placeX(new Position(Row.Middle, Col.Right));

        board.placeO(new Position(Row.Bottom, Col.Left));
        board.placeX(new Position(Row.Bottom, Col.Middle));
        board.placeO(new Position(Row.Bottom, Col.Right));

        assertTrue(board.isFull());
        assertEquals(Board.Status.Draw, board.getStatus());
    }
    @Test
    public void testOWinsOnDiagonal() {
        Board board = new Board();
        board.placeO(new Position(Row.Top, Col.Left));
        board.placeO(new Position(Row.Middle, Col.Middle));
        board.placeO(new Position(Row.Bottom, Col.Right));

        assertEquals(Board.Status.OWins, board.getStatus());
    }
    @Test
    public void testToStringOutput() {
        Board board = new Board();
        board.placeX(new Position(Row.Top, Col.Left));
        board.placeO(new Position(Row.Middle, Col.Middle));
        board.placeX(new Position(Row.Bottom, Col.Right));

        String expected = "X..\n" + ".O.\n" + "..X\n";
        assertEquals(expected, board.toString());
    }
}