import java.util.List;
public class Game {
    private final Computer computer;
    private final Board board;
    private final List<Player> players;

    public Game(Computer computer, Board board, List<Player> players) {
        this.computer = computer;
        this.board = board;
        this.players = players;
    }

    public void launch() {
        System.out.println("The game starts now. 4 initial cells for each player have been chosen.");

        for (int i = 4; i <= 5; i++) {
            for (int j = 2; j <= 3; j++) {
                board.getBoard()[i][j].setStatus(CellStatus.PLAYER1);
            }
            for (int k = 6; k <= 7; k++) {
                board.getBoard()[i][k].setStatus(CellStatus.PLAYER2);
            }
        }
        board.notifyObservers();
        board.displayBoard();

        System.out.println(players.get(0).getName() + " your symbol is displayed in cyan.");
        System.out.println(players.get(1).getName() + " your symbol is displayed in purple.");
        System.out.println("To type in coordinates use the following notation (without the '<' & '>'): <x-coordinate>,<y-coordinate>");

        while (players.get(0).isAlive() && players.get(1).isAlive()) {
            computer.playerTurn(board, players.get(0), players.get(1));
            board.nextGeneration();
            board.displayInfo(players);
            if (players.get(0).isAlive() && players.get(1).isAlive()) {
                computer.playerTurn(board, players.get(1), players.get(0));
                board.nextGeneration();
                board.displayInfo(players);
            } else {
                break;
            }
        }

        if (!players.get(0).isAlive() && !players.get(1).isAlive()) {
            System.out.println("Both players don't have any cells left. There is no winner");
        } else if (!players.get(0).isAlive()) {
            System.out.println(players.get(1).getName() + " won. Congrats.");
        } else if (!players.get(1).isAlive()) {
            System.out.println(players.get(0).getName() + " won. Congrats.");
        }
    }
}
