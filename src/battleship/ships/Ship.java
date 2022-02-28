package battleship.ships;

public abstract class Ship {
    private int length;

    protected Ship(int length) {
        this.length = length;
    }

    public boolean isHit(Coordinate coordinate) {
        return false;
    }

    public abstract boolean isSunk();
    public int getLength() {
        return length;
    };
}
