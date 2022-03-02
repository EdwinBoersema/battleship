package battleship.player;

import battleship.ships.Coordinate;

import java.util.Random;
import java.util.function.Supplier;

public class ComputerPlayer extends Player{

    public ComputerPlayer() {
        super("computer");
    }

    @Override
    public Coordinate play() {
        Supplier<Integer> r = () -> new Random().nextInt(1, 11);

        while(true) {
            Coordinate randomCoordinate = new Coordinate(r.get(), r.get());
            // validate that coordinate is valid
            if (isEmpty(randomCoordinate)) {
                // return coordinate
                return randomCoordinate;
            }
        }
    }
}
