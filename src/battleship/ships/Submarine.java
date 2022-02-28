package battleship.ships;

public class Submarine extends Ship {
    public Submarine() {
        super(3);
    }

    @Override
    public boolean isHit(Coordinate coordinate) {
        return super.isHit(coordinate);
    }

    @Override
    public boolean isSunk() {
        return false;
    }
}
