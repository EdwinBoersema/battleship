package battleship.ships;

import java.util.Arrays;

// wrapper class used for the Player HashMap
public class Coordinates {
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates coordinates) {
        this.x = coordinates.x;
        this.y = coordinates.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinates)) {
            return false;
        }
        return this.x == ((Coordinates) obj).x &&
                this.y == ((Coordinates) obj).y;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{x, y});
    }

    @Override
    public String toString() {
        return String.format(" [%d, %d] ", this.x, this.y);
    }
}
