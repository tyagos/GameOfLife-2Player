import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @org.junit.jupiter.api.Test
    void updateTest() {
        Board b = new Board(10, 10);
        Player player1 = new Player("dummy");
        player1.setSymbol('p');
        CellStatus.PLAYER1.setPlayer(player1);
        CellStatus.PLAYER1.setSymbol('p');
        b.getBoard()[2][2].setStatus(CellStatus.PLAYER1);
        b.getBoard()[2][3].setStatus(CellStatus.PLAYER1);
        b.getBoard()[2][4].setStatus(CellStatus.PLAYER1);
        player1.update(b);
        List<Cell> cells = Arrays.asList(new Cell(2,2,CellStatus.PLAYER1), new Cell(2, 3, CellStatus.PLAYER1),
                new Cell(2, 4, CellStatus.PLAYER1));
        for (int i = 0; i < cells.size(); i++) {
            assertEquals(cells.get(i).getStatus(), player1.getPlayerCells().get(i).getStatus());
        }
    }
    @Test
    void isAliveTest() {
        Player pl = new Player("dummy");
        assertFalse(pl.isAlive());
    }
}