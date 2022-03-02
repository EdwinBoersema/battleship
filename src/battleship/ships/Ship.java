package battleship.ships;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    private final String NAME;
    private final int SIZE;
    private List<Coordinate> coordinates = new ArrayList<>();

    protected Ship(String name, int length) {
        this.NAME = name;
        this.SIZE = length;
    }

    public boolean isHit(Coordinate coordinate) {
        return false;
    }

    // todo: give coordinates, and checkCoordinates function

    public int getSize() {
        return SIZE;
    }

    public String getName() {
        return NAME;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public abstract boolean isSunk();


}
