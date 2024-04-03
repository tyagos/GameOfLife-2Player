import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private final Board b = new Board(10, 10);
    private final Board nextB = new Board(10, 10);
    Player player1 = new Player("dummy1");
    Player player2 = new Player("dummy2");

    @Test
    void testNextGeneration() {
        player1.setSymbol('p');
        CellStatus.PLAYER1.setPlayer(player1);
        CellStatus.PLAYER1.setSymbol('p');
        for (int i = 3; i <= 4; i++) {
            for (int j = 2; j <= 3; j++) {
                b.getBoard()[i][j].setStatus(CellStatus.PLAYER1);
            }
        }
        b.getBoard()[3][1].setStatus(CellStatus.PLAYER1);

        nextB.getBoard()[2][2].setStatus(CellStatus.PLAYER1);
        nextB.getBoard()[3][1].setStatus(CellStatus.PLAYER1);
        nextB.getBoard()[3][3].setStatus(CellStatus.PLAYER1);
        nextB.getBoard()[4][1].setStatus(CellStatus.PLAYER1);
        nextB.getBoard()[4][3].setStatus(CellStatus.PLAYER1);

        b.nextGeneration();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(b.getBoard()[i][j].getStatus(), nextB.getBoard()[i][j].getStatus());
            }
        }
    }

    @Test
    void nextGenerationPlayer2() {
        player2.setSymbol('p');
        CellStatus.PLAYER2.setPlayer(player2);
        CellStatus.PLAYER2.setSymbol('p');
        for (int i = 3; i <= 4; i++) {
            for (int j = 2; j <= 3; j++) {
                b.getBoard()[i][j].setStatus(CellStatus.PLAYER2);
            }
        }
        b.getBoard()[3][1].setStatus(CellStatus.PLAYER2);

        nextB.getBoard()[2][2].setStatus(CellStatus.PLAYER2);
        nextB.getBoard()[3][1].setStatus(CellStatus.PLAYER2);
        nextB.getBoard()[3][3].setStatus(CellStatus.PLAYER2);
        nextB.getBoard()[4][1].setStatus(CellStatus.PLAYER2);
        nextB.getBoard()[4][3].setStatus(CellStatus.PLAYER2);

        b.nextGeneration();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(b.getBoard()[i][j].getStatus(), nextB.getBoard()[i][j].getStatus());
            }
        }
    }

    @Test
    public void testRegisterObserver() {
        b.registerObserver(player1);
        b.registerObserver(player2);
        List<Observer> x = Arrays.asList(player1, player2);
        assertEquals(b.getObservers(), x);
    }

    @Test
    public void testRemoveObserver() {
        b.registerObserver(player1);
        b.registerObserver(player2);
        b.removeObserver(player2);
        List<Observer> x = Collections.singletonList(player1);
        assertEquals(b.getObservers(), x);
    }
}