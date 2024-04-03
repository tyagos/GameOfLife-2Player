import java.util.*;

public class Input {
    private Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readPlayerName(List<Player> players) {
        String pl_name = scanner.nextLine();
        for (Player pl : players) {
            if (Objects.equals(pl.getName(), pl_name)) {
                System.out.println("This person is already participating. Try another name: ");
                return readPlayerName(players);
            }
        }
        return pl_name;
    }

    public Character readSymbol() {
        return scanner.next().charAt(0);
    }
    public List<Integer> checkInvalidInput(String cellCoord) {
        if (cellCoord == null || cellCoord.length() != 3) {
            System.out.println("Some invalid input, try again: ");
            cellCoord = scanner.nextLine();
            return checkInvalidInput(cellCoord);
        }
        if (cellCoord.charAt(0) == ',' || cellCoord.charAt(1) != ',' || cellCoord.charAt(2) == ',') {
            System.out.println("Please write in the form x-coord,y-coord. E.g. 3,6. Try again: ");
            cellCoord = scanner.nextLine();
            return checkInvalidInput(cellCoord);
        }
        String[] parts = cellCoord.split(",");
        String x = parts[0].trim();    // x-axis
        String y = parts[1].trim();   // y-axis

        int xCoord; int yCoord;
        try {
            xCoord = Character.getNumericValue(x.charAt(0));
            yCoord = Character.getNumericValue(y.charAt(0));
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Invalid input.");
            cellCoord = scanner.nextLine();
            return checkInvalidInput(cellCoord);
        }
        return Arrays.asList(xCoord, yCoord);
    }
    public void killCell(Board board, Player thisPlayer, Player otherPlayer) {
        String cellCoord = scanner.nextLine();
        List<Integer> coords = checkInvalidInput(cellCoord);
        int xCoord = coords.get(0);
        int yCoord = coords.get(1);

        if (board.getCellStatusFromCoord(xCoord, yCoord).getCellStatusSymbol() == otherPlayer.getSymbol()) {
            board.getBoard()[xCoord][yCoord].setStatus(CellStatus.DEAD);
            board.notifyObservers();
            System.out.println(thisPlayer.getName() + " has killed cell in "+ xCoord +","+ yCoord +".");
        } else {
            System.out.println("Given cell does not belong to your opponent. Try another: ");
            killCell(board, thisPlayer, otherPlayer);
        }
    }
    public void awakenCell(Board board, Player player) {
        String cellCoord = scanner.nextLine();
        List<Integer> coords = checkInvalidInput(cellCoord);
        int xCoord = coords.get(0);
        int yCoord = coords.get(1);

        if (board.getCellStatusFromCoord(xCoord, yCoord).equals(CellStatus.DEAD)) {
            if (player.getSymbol() == CellStatus.PLAYER1.getCellStatusSymbol()) {
                board.getBoard()[xCoord][yCoord].setStatus(CellStatus.PLAYER1);
            } else {
                board.getBoard()[xCoord][yCoord].setStatus(CellStatus.PLAYER2);
            }
            board.notifyObservers();
            System.out.println(player.getName() + " has awaken cell "+ xCoord +","+ yCoord +".");
        } else {
            System.out.println("Given cell is not dead. Try another: ");
            awakenCell(board, player);
        }
    }
}
