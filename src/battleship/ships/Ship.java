package battleship.ships;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    private final String horizontalAscii;
    private final String verticalAscii;
    private boolean orientation;
    private List<Coordinates> coordinates = new ArrayList<>();
    protected final String NAME;
    protected final int SIZE;
    protected int hitCounter = 0;
    protected boolean isSunk = false;

    protected Ship(String name, int size, String horizontalAscii, String verticalAscii) {
        this.NAME = name;
        this.SIZE = size;
        this.horizontalAscii = horizontalAscii;
        this.verticalAscii = verticalAscii;
    }

    // gets the correct character to put in the grid
    public String getAscii(int index) {
        if (orientation) {
            return String.valueOf(horizontalAscii.toCharArray()[index]);
        } else {
            return String.valueOf(verticalAscii.toCharArray()[index]);
        }
    }

    // sets the ship's orientation to either horizontal (true) or vertical (false)
    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    // compares a set of coordinates to the ships coordinates and returns whether it's hit
    public boolean isHit(Coordinates coordinate) {
        return coordinates.contains(coordinate);
    }

    // updates the
    public void updateHitCounter() {
        if (hitCounter < SIZE) {
            hitCounter++;
        }
    }

    // compares the ship's size to it's hitCounter and returns a boolean
    public abstract boolean isSunk();

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }
    public abstract int getSize();
    public abstract String getName();
}
