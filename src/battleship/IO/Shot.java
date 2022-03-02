package battleship.IO;

public enum Shot {
    MISS("+"),
    HIT("X");

    public final String value;

    private Shot(String value) {
        this.value = value;
    }
}
