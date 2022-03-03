package battleship.ships;

public class Cruiser extends Ship {

    public Cruiser() {
        super("Cruiser", 3, "<=>", "^║v");
    }

    @Override
    public int getSize() {
        return this.SIZE;
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    @Override
    public boolean isHit(Coordinate coordinate) {
        return super.isHit(coordinate);
    }

    @Override
    public boolean isSunk() {
        return isSunk;
    }
}
