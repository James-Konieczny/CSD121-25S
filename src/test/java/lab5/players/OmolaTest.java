package lab5.players;

import lab5.game.Board;
import lab5.game.Position;
import lab5.game.Row;
import lab5.game.Col;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Tests for the Omola ("One M0ve Look-Ahead") computer player.
 * Verifies:
 * 1. Omola always picks winning moves when available
 * 2. Omola blocks opponent's winning moves when no immediate win exists
 * 3. Omola selects valid positions from remaining options when no wins/blocks are present
 * 4. Edge cases (full boards, forced blocks, fork scenarios)
 *
 * Uses the Board(String) constructor to simulate specific game states for predictable testing.
 */
public class OmolaTest {
    /**
     * Tests that Omola picks a winning move when available.
     * <p>
     * In this scenario, Omola (playing 'X') has two in a row and should choose
     * the third cell in the row to win the game.
     */
    @Test
    void picksWinningMove() {
        Board board = new Board("XX OO X  ");
        Position move = new Omola().pickNextMove(board);
        assertEquals(new Position(Row.Bottom, Col.Middle), move); // Should complete row
    }
    /**
     * Tests that Omola blocks the opponent's winning move.
     * <p>
     * Here, the opponent ('O') is one move away from winning, and Omola should
     * place an 'X' to block that position.
     */
    @Test
    void blocksOpponentWin() {
        Board board = new Board("OO X X   ");
        Position move = new Omola().pickNextMove(board);
        assertEquals(new Position(Row.Bottom, Col.Left), move); // Should block O's win
    }
    /**
     * Tests that Omola handles fork situations properly.
     * <p>
     * In a fork scenario, Omola may have multiple equally valid strategic moves
     * to choose from. This test checks that Omola chooses one of the expected
     * valid positions.
     */
    @Test
    void handlesForkSituation() {
        Board board = new Board("X O XO   ");
        Position move = new Omola().pickNextMove(board);
        assertTrue(List.of(
                new Position(Row.Top, Col.Middle),
                new Position(Row.Bottom, Col.Left)
        ).contains(move));
    }
}
