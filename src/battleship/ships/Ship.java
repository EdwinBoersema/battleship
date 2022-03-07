package battleship.ships;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    private final String horizontalAscii;
    private final String verticalAscii;
    private boolean horizontalAlignment;
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

    public boolean isHit(Coordinates coordinate) {
        return coordinates.contains(coordinate);
    }

    public void updateHitCounter() {
        if (hitCounter < SIZE) {
            hitCounter++;
        }
        if (hitCounter == SIZE) {
            this.isSunk = true;
        }
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    public abstract int getSize();
    public abstract String getName();
    public abstract boolean isSunk(); // fixme always returns true when hit
}
