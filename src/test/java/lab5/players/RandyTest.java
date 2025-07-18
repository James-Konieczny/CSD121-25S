package lab5.players;

import lab5.game.Board;
import lab5.game.Position;
import lab5.game.Row;
import lab5.game.Col;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Randy computer player.
 * Verifies:
 * 1. Randy only picks empty positions
 * 2. Randy's moves are uniformly distributed
 * 3. Edge cases (full/near-full boards)
 */
public class RandyTest {

    @Test
    void testRandyOnlyPicksEmptyPositions() {
        // Arrange
        Board board = new Board("X O X   O ");
        Randy randy = new Randy();

        // Act & Assert: Test 100 times to ensure validity
        for (int i = 0; i < 100; i++) {
            Position move = randy.pickNextMove(board);
            assertTrue(board.isEmptyAt(move),
                    "Randy picked an occupied position: " + move);
        }
    }

    @Test
    void testRandyPicksAllPositionsWithEqualProbability() {
        // Arrange
        Board emptyBoard = new Board("         ");
        Randy randy = new Randy();
        Map<Position, Integer> positionCounts = new HashMap<>();
        int totalTrials = 999; // High number for statistical significance

        // Initialize counts for all 9 positions
        for (Row row : Row.values()) {
            for (Col col : Col.values()) {
                positionCounts.put(new Position(row, col), 0);
            }
        }

        // Act: Run many trials
        for (int i = 0; i < totalTrials; i++) {
            Position move = randy.pickNextMove(emptyBoard);
            positionCounts.put(move, positionCounts.get(move) + 1);
        }

        // Assert: Check distribution (~11% per cell, ±10% tolerance)
        double expectedCount = totalTrials / 9.0; // ~111
        double tolerance = expectedCount * 0.10;  // ±10%

        for (Map.Entry<Position, Integer> entry : positionCounts.entrySet()) {
            int count = entry.getValue();
            assertTrue(
                    Math.abs(count - expectedCount) <= tolerance,
                    "Position " + entry.getKey() + " was picked " + count +
                            " times (expected ~111 ±11)"
            );
        }
    }
    @Test
    void testRandyOnFullBoard() {
        Board fullBoard = new Board("XOXOXOXOX");
        Randy randy = new Randy();
        assertThrows(IndexOutOfBoundsException.class,
                () -> randy.pickNextMove(fullBoard));
    }

    @Test
    void testRandyOnNearFullBoard() {
        Board board = new Board("XOXOXOX  ");
        Randy randy = new Randy();

        // The only valid moves are (Bottom, Left) and (Bottom, Right)
        Position move = randy.pickNextMove(board);
        assertTrue(
                move.equals(new Position(Row.Bottom, Col.Left)) ||
                        move.equals(new Position(Row.Bottom, Col.Right))
        );
    }
}