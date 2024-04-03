public class Cell {

    private CellStatus status;
    private final int x;
    private final int y;

    public Cell(int x, int y, CellStatus status) {
        this.status = status;
        this.x = x;
        this.y = y;

    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }

    public CellStatus getStatus() {
        return this.status;
    }

}
