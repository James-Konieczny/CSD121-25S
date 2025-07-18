package lab5.players;

import lab5.game.Board;
import lab5.game.Position;
import lab5.game.Row;
import lab5.game.Col;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class OmolaTest {
    @Test
    void picksWinningMove() {
        Board board = new Board("XX OO X  ");
        Position move = new Omola().pickNextMove(board);
        assertEquals(new Position(Row.Bottom, Col.Middle), move); // Should complete row
    }

    @Test
    void blocksOpponentWin() {
        Board board = new Board("OO X X   ");
        Position move = new Omola().pickNextMove(board);
        assertEquals(new Position(Row.Bottom, Col.Left), move); // Should block O's win
    }
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
