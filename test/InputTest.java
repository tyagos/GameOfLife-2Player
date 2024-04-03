import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class InputTest {
    private final Input input = new Input();
    private final Player pl1 = new Player("bart");
    private final Player pl2 = new Player("marge");
    private final Board b = new Board(10, 10);
    private final String ls = System.lineSeparator();


    @Test
    public void testReadSymbol(){
        Character s = 'p';
        String s_str = Character.toString(s);
        InputStream in = new ByteArrayInputStream(s_str.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        pl1.setSymbol('p');
        assertEquals(s, input.readSymbol());
    }

    @Test
    public void testReadPlayerName() {
        String s = "homer";
        InputStream in = new ByteArrayInputStream(s.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        List<Player> list = Arrays.asList(pl1, pl2);
        assertEquals(s, input.readPlayerName(list));
    }
    
    @Test
    public void testReadPlayerNameFalse() {
        String s = "bart" + ls + "name";
        InputStream in = new ByteArrayInputStream(s.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        List<Player> list = Arrays.asList(pl1, pl2);
        assertEquals("name", input.readPlayerName(list));
    }

    @Test
    public void testKillCell() {
        CellStatus.PLAYER1.setPlayer(pl1); CellStatus.PLAYER1.setSymbol('1');
        CellStatus.PLAYER2.setPlayer(pl2); CellStatus.PLAYER2.setSymbol('2');
        pl1.setSymbol('1'); pl2.setSymbol('2');
        b.getBoard()[0][0].setStatus(CellStatus.PLAYER1);
        String coord = "0,0";
        InputStream in = new ByteArrayInputStream(coord.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        input.killCell(b, pl2, pl1);
        assertEquals(b.getBoard()[0][0].getStatus(), CellStatus.DEAD);
    }

    @Test
    public void testKillCellFalse() {
        CellStatus.PLAYER1.setPlayer(pl1); CellStatus.PLAYER1.setSymbol('1');
        CellStatus.PLAYER2.setPlayer(pl2); CellStatus.PLAYER2.setSymbol('2');
        pl1.setSymbol('1'); pl2.setSymbol('2');
        b.getBoard()[0][0].setStatus(CellStatus.PLAYER1);
        String coord = "0,5" + ls + "0,0";
        InputStream in = new ByteArrayInputStream(coord.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        input.killCell(b, pl2, pl1);
        assertEquals(b.getBoard()[0][0].getStatus(), CellStatus.DEAD);
    }

    @Test
    public void testAwakenCell1() {
        CellStatus.PLAYER1.setPlayer(pl1); CellStatus.PLAYER1.setSymbol('1');
        CellStatus.PLAYER2.setPlayer(pl2); CellStatus.PLAYER2.setSymbol('2');
        pl1.setSymbol('1'); pl2.setSymbol('2');

        String coord = "0,0";
        InputStream in = new ByteArrayInputStream(coord.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        input.awakenCell(b, pl1);
        assertEquals(b.getBoard()[0][0].getStatus(), CellStatus.PLAYER1);

        String coord2 = "0,1";
        InputStream in2 = new ByteArrayInputStream(coord2.getBytes());
        System.setIn(in2);
        Scanner testScanner2 = new Scanner(in2);
        input.setScanner(testScanner2);
        input.awakenCell(b, pl2);
        assertEquals(b.getBoard()[0][1].getStatus(), CellStatus.PLAYER2);
    }

    @Test
    public void testAwakenCellFalse() {
        CellStatus.PLAYER1.setPlayer(pl1); CellStatus.PLAYER1.setSymbol('1');
        CellStatus.PLAYER2.setPlayer(pl2); CellStatus.PLAYER2.setSymbol('2');
        pl1.setSymbol('1'); pl2.setSymbol('2');
        b.getBoard()[0][5].setStatus(CellStatus.PLAYER1);

        String coord = "0,5" + ls + "0,0";
        InputStream in = new ByteArrayInputStream(coord.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        input.awakenCell(b, pl1);
        assertEquals(b.getBoard()[0][0].getStatus(), CellStatus.PLAYER1);
    }

    @Test
    public void testCheckInvalidInput() {
        String coord = "0:6" + ls + "0,99" + ls + "0," + ls + "3,4";
        InputStream in = new ByteArrayInputStream(coord.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        assertEquals(Arrays.asList(3, 4), input.checkInvalidInput(coord));
    }
}
