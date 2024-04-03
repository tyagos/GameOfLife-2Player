import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Computer {

    public List<Player> getPlayerNames() {
        List<Player> players = new ArrayList<>();
        Input input = new Input();
        System.out.println("Exactly two players can play this game.");
        int i = 0;
        int numPlayers = 2;
        while (i != numPlayers) {
            System.out.print("Type in your name, please: ");
            String player_name = input.readPlayerName(players);
            Player player = new Player(player_name);
            players.add(player);
            i++;
            System.out.println(player_name + " is in.");
        }

        players.sort(Comparator.comparing(Player::getName));

        System.out.println("The names in alphabetical order are: ");
        for (Player player : players) {
            System.out.println(player.getName() + " ");
        }
        return players;
    }

    public void getPlayersSymbol(List<Player> players) {
        Input input = new Input();
        for (Player player : players) {
            System.out.println(player.getName() + " what symbol (single Char) do you want to play with? (Whitespace forbidden)");
            Character symbol = input.readSymbol();
            player.setSymbol(symbol);
            if (players.get(0) == player) {
                CellStatus.PLAYER1.setSymbol(symbol);
            } else {
                CellStatus.PLAYER2.setSymbol(symbol);
            }
            System.out.println(player.getName() + "'s symbol is " + player.getSymbol());
        }
    }

    public void playerTurn(Board board, Player thisPlayer, Player otherPlayer) {
        Input input = new Input();
        //first kill otherPLayers cell, then place new one.
        System.out.println(thisPlayer.getName() + " it's your turn now. You can kill one of " + otherPlayer.getName() +
                "s cells. Remember, his/her symbol is: " + otherPlayer.getSymbol());
        System.out.println("Type in the desired coordinate: ");
        input.killCell(board, thisPlayer, otherPlayer);

        System.out.println(thisPlayer.getName() + " can now awaken a cell. Type in its coordinates please: ");
        input.awakenCell(board, thisPlayer);
    }
}
