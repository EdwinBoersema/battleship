package battleship.ships;

public class Battleship extends Ship {
    public Battleship() {
        super(4);
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
