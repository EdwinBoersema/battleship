package battleship.IO;

public enum Shot {
    MISS("+"),
    HIT("X");

    public final String symbol;

    Shot(String symbol) {
        this.symbol = symbol;
    }
}
