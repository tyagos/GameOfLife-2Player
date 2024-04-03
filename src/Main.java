import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(10, 10);
        Computer computer = new Computer();

        List<Player> players = computer.getPlayerNames();
        computer.getPlayersSymbol(players);
        CellStatus.PLAYER1.setPlayer(players.get(0));
        CellStatus.PLAYER2.setPlayer(players.get(1));

        Game game = new Game(computer, board, players);
        board.registerObserver(players.get(0));
        board.registerObserver(players.get(1));

        game.launch();
    }
}