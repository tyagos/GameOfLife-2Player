import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ComputerTest {
    private final String ls = System.lineSeparator();
    private final Input input = new Input();
    private final Computer computer = new Computer();

    @Test
    public void testGetPlayerNames() {
        String names = "borat" + ls + "spiderman";
        InputStream in = new ByteArrayInputStream(names.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        List<Player> players = computer.getPlayerNames();
        assertEquals(players.get(0).getName(), "borat");
        assertEquals(players.get(1).getName(), "spiderman");
    }

    @Test
    public void testGetPlayerSymbol() {
        Player pl1 = new Player("Pop Smoke");
        Player pl2 = new Player("Zac Efron");
        List<Player> players = Arrays.asList(pl1, pl2);
        String symbols = "B" + ls + "S";
        InputStream in = new ByteArrayInputStream(symbols.getBytes());
        System.setIn(in);
        Scanner testScanner = new Scanner(in);
        input.setScanner(testScanner);
        computer.getPlayersSymbol(players);
        assertEquals(players.get(0).getSymbol(), 'B');
        assertEquals(players.get(1).getSymbol(), 'S');
    }
}