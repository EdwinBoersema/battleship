package battleship.ships;

public class Submarine extends Ship {
    public Submarine() {
        super("Submarine", 3, "<=>", "^║v");
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
