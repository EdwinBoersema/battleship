package battleship.ships;

public class Battleship extends Ship {
    public Battleship() {
        super("Battleship", 4, "<==>", "^║║v");
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
