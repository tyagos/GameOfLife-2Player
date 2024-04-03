import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private Cell[][] grid;
    private int numGenerations = 0;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public Board(int rows, int columns) {
        this.grid = new Cell[rows][columns];

        // Initialize the game grid with empty cells.
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                this.grid[row][column] = new Cell(row, column, CellStatus.DEAD);
            }
        }
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void displayInfo(List<Player> players) {
        System.out.println("\n-----------------------------------------------");
        System.out.println("Current number of Generation: " + numGenerations);
        System.out.println(players.get(0).getName() + " number of cells: " + players.get(0).getPlayerCells().size());
        System.out.println(players.get(1).getName() + " number of cells: " + players.get(1).getPlayerCells().size());
        System.out.println("-----------------------------------------------\n");
    }

    public void displayBoard() {
        System.out.println("  0 1 2 3 4 5 6 7 8 9  ");
        System.out.println(" +-+-+-+-+-+-+-+-+-+-+ ");
        int nr = 0;
        for (Cell[] row : grid) { // y-axis
            System.out.print(nr + "|");
            for (Cell cell : row) {  // x-axis
                if (cell.getStatus().equals(CellStatus.PLAYER1)) {
                    System.out.print(ANSI_CYAN + CellStatus.PLAYER1.getCellStatusSymbol() + ANSI_RESET);
                } else if (cell.getStatus().equals(CellStatus.PLAYER2)) {
                    System.out.print(ANSI_PURPLE + CellStatus.PLAYER2.getCellStatusSymbol() + ANSI_RESET);
                } else {
                    System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.println(nr);
            nr += 1;
        }
        System.out.println(" +-+-+-+-+-+-+-+-+-+-+ ");
        System.out.println("  0 1 2 3 4 5 6 7 8 9  ");
    }

    public Cell[][] getBoard() {
        return grid;
    }

    public CellStatus getCellStatusFromCoord(int x, int y) {
        return grid[x][y].getStatus();
    }

    public List<Integer> countNeighbors(int x, int y) {
        int player1Neighbors = 0;
        int player2Neighbors = 0;
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if ((i != x || j != y) && i >= 0 && i <= 9 && j >= 0 && j <= 9) {
                    if (grid[i][j].getStatus().equals(CellStatus.PLAYER1)) {
                        player1Neighbors++;
                    } else if (grid[i][j].getStatus().equals(CellStatus.PLAYER2)) {
                        player2Neighbors++;
                    }
                }
            }
        }
        return Arrays.asList(player1Neighbors, player2Neighbors);
    }

    public Cell[][] copyGrid() {
        Cell[][] copyGrid = new Cell[10][10];
        for (int i = 0; i <= 9; i++) {
            System.arraycopy(grid[i], 0, copyGrid[i], 0, 10);
        }
        return copyGrid;

    }

    public void nextGeneration() {
        Cell[][] nextGeneration = copyGrid();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                List<Integer> neighbors = countNeighbors(i, j);
                int player1neighbors = neighbors.get(0);
                int player2neighbors = neighbors.get(1);

                if (grid[i][j].getStatus().equals(CellStatus.DEAD)) { // if both have 3 we do nothing
                    if (player1neighbors == 3 && player2neighbors != 3) {
                        nextGeneration[i][j]  = new Cell(i, j, CellStatus.PLAYER1);
                    } else if (player2neighbors == 3 && player1neighbors != 3) {
                        nextGeneration[i][j]  = new Cell(i, j, CellStatus.PLAYER2);
                    }
                } else if (grid[i][j].getStatus().equals(CellStatus.PLAYER1)) {
                    // Any live cell with two or three live neighbors lives on to the next generation
                    if (player1neighbors != 3 && player1neighbors != 2) {
                        // Otherwise, the cell dies (underpopulation or overpopulation)
                        nextGeneration[i][j] = new Cell(i, j, CellStatus.DEAD);
                    }
                } else if (grid[i][j].getStatus().equals(CellStatus.PLAYER2)) {
                    if (player2neighbors != 3 && player2neighbors != 2) {
                        // Otherwise, the cell dies (underpopulation or overpopulation)
                        nextGeneration[i][j] = new Cell(i, j, CellStatus.DEAD);
                    }
                }
            }
        }
        setGrid(nextGeneration);
        notifyObservers();
        numGenerations++;
        System.out.println("New generation: ");
        displayBoard();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        notifyObservers();
    }
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        notifyObservers();
    }
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update(this);
        }
    }
}
