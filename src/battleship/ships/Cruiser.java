package battleship.ships;

public class Cruiser extends Ship {
    public Cruiser() {
        super("Cruiser", 3);
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
