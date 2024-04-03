public enum CellStatus {
    DEAD(null, ' '),
    PLAYER1(null, ' '),
    PLAYER2(null, ' ');

    private Player player;
    private Character symbol;

    CellStatus(Player player, Character symbol) {
        this.player = player;
        this.symbol = symbol;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setSymbol(Character symbol) {
        this.symbol = symbol;
    }

    public Character getCellStatusSymbol() {
        return symbol;
    }
}
