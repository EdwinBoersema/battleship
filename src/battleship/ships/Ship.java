package battleship.ships;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    private final String NAME;
    private final int SIZE;
    private final String horizontalAscii;
    private final String verticalAscii;
    private boolean horizontalAlignment;
    private List<Coordinate> coordinates = new ArrayList<>();

    protected Ship(String name, int length, String horizontalAscii, String verticalAscii) {
        this.NAME = name;
        this.SIZE = length;
        this.horizontalAscii = horizontalAscii;
        this.verticalAscii = verticalAscii;
    }

    public boolean isHit(Coordinate coordinate) {
        return false;
    }

    // todo: give coordinates, and checkCoordinates function

    public String getAscii(int index) {
        if (horizontalAlignment) {
            return String.valueOf(horizontalAscii.toCharArray()[index]);
        } else {
            return String.valueOf(verticalAscii.toCharArray()[index]);
        }
    }

    public void setHorizontalAlignment(boolean alignment) {
        this.horizontalAlignment = alignment;
    }

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
