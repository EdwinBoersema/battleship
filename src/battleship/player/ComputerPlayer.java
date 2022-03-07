package battleship.player;

import battleship.ships.Coordinates;
import battleship.ships.Ship;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
                System.out.println("Computer tries: " + randomCoordinate); // todo translate to coordinate i.e. "A2" --> refactor into IOUtil method
                return randomCoordinate;
            }
        }
    }

    /*
     * Assigns a set of coordinates to each ship
     * and places the ship's ascii in the grid
     */
    public void placeShips() {
        System.out.printf("placing %s's ships%n", this.name);
        for (Ship ship : ships) {
            List<Coordinates> coordinatesList = getValidCoordinates(ship);
            // set ship coordinates and add coordinates to the grid
            ship.setCoordinates(coordinatesList);
            for (int i = 0; i < ship.getSize(); i ++) {
                Coordinates coordinate = coordinatesList.get(i);
                grid.replace(coordinate, ship.getAscii(i));
            }


            System.out.printf("%s at coordinates: ", ship.getName()); // todo remove after testing
            coordinatesList.forEach(System.out::print);
            System.out.print("\n");
        }

    }

    private List<Coordinates> getValidCoordinates(Ship ship) {
        Supplier<Integer> randomInt = () -> new Random().nextInt(1, 11);
        Supplier<Boolean> randomBoolean = () -> new Random().nextBoolean();

        List<Coordinates> coordinatesList;
        // check new coordinates until a valid row or column of Coordinates is found
        while (true) {
            Coordinates randomCoordinate = new Coordinates(randomInt.get(), randomInt.get());
            // get horizontal or vertical coordinates depending on the random boolean
            if (randomBoolean.get()) {
                coordinatesList = checkHorizontalCoordinates(randomCoordinate, ship.getSize());
                // set ship's alignment
                ship.setOrientation(true);
            } else {
                coordinatesList = checkVerticalCoordinates(randomCoordinate, ship.getSize());
                // set ship's alignment
                ship.setOrientation(false);
            }
            // check list
            if (coordinatesList.size() == ship.getSize()) {
                return coordinatesList;
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
