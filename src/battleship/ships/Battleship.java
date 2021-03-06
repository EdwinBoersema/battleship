package battleship.ships;

public class Battleship extends Ship {

    public Battleship() {
        super("Battleship", 4, "<==>", "^║║v");
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
        return hitCounter == SIZE;
    }
}
