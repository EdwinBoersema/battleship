package battleship.ships;

public class Carrier extends Ship{
    public Carrier() {
        super("Carrier", 5);
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
