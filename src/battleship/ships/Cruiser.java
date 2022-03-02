package battleship.ships;

public class Cruiser extends Ship {
    public Cruiser() {
        super("Cruiser", 3, "<=>", "^║v");
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
