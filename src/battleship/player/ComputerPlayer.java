package battleship.player;

import battleship.IO.IOUtil;
import battleship.ships.Coordinates;
import battleship.ships.Ship;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ComputerPlayer extends Player{

    public ComputerPlayer() {
        super("computer");
    }

    @Override
    public Coordinates play() {
        // wait one second so the player can see what the computer does
        sleep(1);

        Supplier<Integer> r = () -> new Random().nextInt(1, 11);

        while(true) {
            Coordinates randomCoordinate = new Coordinates(r.get(), r.get());
            // validate that coordinate is valid
            if (isEmpty(randomCoordinate)) {
                // return coordinate
                System.out.println("Computer tries: " + randomCoordinate); // todo translate to coordinate i.e. "A2"
                return randomCoordinate;
            }
        }
    }

    @Override
    public String notify(boolean hit, Ship ship) {
        // wait one second so the player can see what the computer does
        sleep(1);

        if (ship != null && ship.isSunk()) {
            return String.format("You sunk the enemies %s!", ship.getName());
        } else {
            String result = hit ? "hit" : "missed";
            return String.format("The computer %s", result);
        }
    }

    public void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ignored) {}
    }
}
