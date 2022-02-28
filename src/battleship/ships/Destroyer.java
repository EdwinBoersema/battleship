package battleship.ships;

public class Destroyer extends Ship {
    public Destroyer() {
        super(2);
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
