package lab5.players;

import lab5.game.Board;
import lab5.game.Position;
import lab5.game.PlayerToken;
import java.util.List;

public class Omola extends Player {
    public Omola() {
        super("Omola");
    }

    @Override
    public Position pickNextMove(Board currentBoard) {
        PlayerToken myToken = currentBoard.getNextTurnToken();
        PlayerToken opponentToken = myToken.opponent();

        List<Position> emptyCells = currentBoard.getEmptyCells();


        for (Position pos : emptyCells) {
            Board testBoard = new Board(currentBoard);
            testBoard.placeNextToken(pos);
            if (testBoard.getWinner() == myToken) {
                return pos; // Win immediately
            }
        }

        // Block opponent's winning move
        for (Position pos : emptyCells) {
            Board testBoard = new Board(currentBoard);
            testBoard.place(pos, opponentToken);
            if (testBoard.getWinner() == opponentToken) {
                return pos; // Block opponent
            }
        }

            // Default back to picking randomly
            return emptyCells.get((int) (Math.random() * emptyCells.size()));
    }
}
