package lab5.players;

import java.util.Random;
import lab5.game.Board;
import lab5.game.Position;

public class Randy extends Player{
    private Random rand = new Random();

    public Randy() {
        super("Randy");
    }

    @Override
    public Position pickNextMove(Board currentBoard) {
        var emptyCells = currentBoard.getEmptyCells();
        return emptyCells.get(rand.nextInt(emptyCells.size()));
    }
}
