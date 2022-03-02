package battleship.ships;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinate)) {
            return false;
        }
        return this.x == ((Coordinate) obj).x &&
                this.y == ((Coordinate) obj).y;
    }

    @Override
    public String toString() {
        return String.format("Coordinate: %d, %d", this.x, this.y);
    }
}
