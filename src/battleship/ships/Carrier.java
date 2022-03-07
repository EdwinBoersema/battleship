package battleship.ships;

public class Carrier extends Ship{

    public Carrier() {
        super("Carrier", 5, "<===>", "^║║║v");
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
    public boolean isHit(Coordinates coordinate) {
        return super.isHit(coordinate);
    }

    @Override
    public boolean isSunk() {
        return isSunk;
    }
}
