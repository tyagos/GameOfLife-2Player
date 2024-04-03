import java.util.ArrayList;
import java.util.List;

public class Player implements Observer {
    private final String name;
    private Character symbol;
    private List<Cell> playerCells;

    public Player(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.playerCells = new ArrayList<>();
    }
    public String getName() {return name;}

    public Character getSymbol(){return symbol;}

    public List<Cell> getPlayerCells() {
        return playerCells;
    }

    public void setPlayerCells(List<Cell> playerCells) {
        this.playerCells = playerCells;
    }

    public void setSymbol(Character symbol) {
        this.symbol = symbol;
    }

    public boolean isAlive() {
        return !playerCells.isEmpty();
    }

    @Override
    public void update(Board b) {
        List<Cell> updatedPlayerCells = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (b.getCellStatusFromCoord(row, col).getCellStatusSymbol() == symbol) {
                    // add cell to playerCells
                    updatedPlayerCells.add(b.getBoard()[row][col]);
                }
            }
        }
        setPlayerCells(updatedPlayerCells);
    }
}
