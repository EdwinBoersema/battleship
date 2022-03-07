package battleship.ships;

public class Destroyer extends Ship {

    public Destroyer() {
        super("Destroyer", 2, "<>", "^v");
    }

    @Override
    public int getSize() {
        return super.SIZE;
    }

    @Override
    public String getName() {
        return super.NAME;
    }

    @Override
    public boolean isHit(Coordinates coordinate) {
        return super.isHit(coordinate);
    }

    @Override
    public boolean isSunk() {
        return isSunk;
    }
}
