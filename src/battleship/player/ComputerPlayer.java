package battleship.player;

import battleship.IO.IOUtil;
import battleship.ships.Coordinates;
import battleship.ships.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ComputerPlayer extends Player{
    private Coordinates lastShot;
    private Coordinates lastHit;
    private Coordinates nextShot;
    private List<Coordinates> firingSequence = new ArrayList<>();
    private List<Coordinates> shotsToCheck = new ArrayList<>();
    int firingOrientation;

    public ComputerPlayer() {
        super("computer");
    }

    @Override
    public Coordinates play() {
        // wait one second so the player can see what the computer does
        sleep(1);

        Coordinates coordinates = getTargetCoordinates();
//        System.out.println("Computer tries: " + coordinates); // todo translate to coordinate i.e. "A2" --> refactor into IOUtil method
        IOUtil.printComputerCoordinates(coordinates);
        // set lastShot coordinates
        lastShot = new Coordinates(coordinates);
        shots.add(coordinates);
        return coordinates;
    }

    /*
     * Assigns a set of coordinates to each ship
     */
    public void placeShips() {
        System.out.printf("placing %s's ships%n", this.name);
        for (Ship ship : ships) {
            List<Coordinates> coordinatesList = getValidCoordinates(ship);
            // set ship coordinates and add coordinates to the grid
            ship.setCoordinates(coordinatesList);
            for (int i = 0; i < ship.getSize(); i ++) {
                Coordinates coordinate = coordinatesList.get(i);
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
            return String.format("The computer sunk your %s!", ship.getName());
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

    // methods to determine the computers next guess

    private Coordinates getTargetCoordinates() {
        if (nextShot != null) {
            return nextShot;
        }
        if (lastHit != null) {
            // check the lastHit's adjacent coordinates
            Optional<Coordinates> targetCoordinates = checkAdjacentCoordinates();

            // return targetCoordinates
            return targetCoordinates.orElseGet(this::getRandomTargetCoordinates);
        } else {
            // if lastHit is empty, pick random Coordinates
            return getRandomTargetCoordinates();
        }
    }

    private Optional<Coordinates> checkAdjacentCoordinates() {
        Optional<Coordinates> optionalCoordinates;
        // check horizontal or vertical adjacent coordinates based on firingOrientation
        if (firingOrientation == 1) {
            optionalCoordinates = checkHorizontalTargets();
        } else if (firingOrientation == 2) {
            optionalCoordinates = checkVerticalTargets();
        } else {
            // if firingOrientation isn't set yet, pick a random orientation
            boolean bool = new Random().nextBoolean();
            optionalCoordinates = bool ? checkHorizontalTargets() : checkVerticalTargets();
            // if no coordinates were returned, check the other orientation
            if (optionalCoordinates.isEmpty()) {
                optionalCoordinates = !bool ? checkHorizontalTargets() : checkVerticalTargets();
            }
         }
        return optionalCoordinates;
    }

    // checks adjacent horizontal coordinates
    private Optional<Coordinates> checkHorizontalTargets() {
        // make a list of the two horizontal adjacent coordinates
        List<Coordinates> list = List.of(
                new Coordinates(lastHit.x + 1, lastHit.y),
                new Coordinates(lastHit.x - 1, lastHit.y)
        );
        // check if coordinate is empty, return first empty coordinate or empty Optional
        return list.stream()
                .filter(this::isWithinBounds)
                .filter(c -> !shots.contains(c))
//                .peek(c -> System.out.print("checking: " + c)) // todo remove
                .findFirst();
    }

    // checks adjacent horizontal coordinates
    private Optional<Coordinates> checkVerticalTargets() {
        // make a list of the two vertical adjacent coordinates
        List<Coordinates> list = List.of(
                new Coordinates(lastHit.x, lastHit.y + 1),
                new Coordinates(lastHit.x, lastHit.y - 1)
        );
        // check if coordinate is empty, return first empty coordinate or empty Optional
        return list.stream()
                .filter(this::isWithinBounds)
                .filter(c -> !shots.contains(c))
//                .peek(c -> System.out.print("checking: " + c)) // todo remove
                .findFirst();
    }

    // loops through random coordinates until a valid empty one is found
    private Coordinates getRandomTargetCoordinates() {
        Supplier<Integer> r = () -> new Random().nextInt(1, 11);
//        System.out.println("picking random coordinates"); // todo remove

        while(true) {
            Coordinates targetCoordinates = new Coordinates(r.get(), r.get());
            // validate that coordinate is valid
            if (!shots.contains(targetCoordinates)) {
                // return coordinate
                return targetCoordinates;
            }
        }
    }

    private boolean isWithinBounds(Coordinates coordinates) {
        return coordinates.x > 0 &&
                coordinates.x <= 10 &&
                coordinates.y > 0 &&
                coordinates.y <= 10;
    }
//
//    private Coordinates getNextShot() {
//
//    }

    /*
     * sets the firing orientation depending on the values of the 2 last hits
     * 0 = random
     * 1 = horizontal
     * 2 = vertical
    */
    private void setFiringOrientation() {
        // compare x
        if ((lastHit.x -1) == lastShot.x ||
                (lastHit.x + 1) == lastShot.x) {
            firingOrientation = 1;
        }
        // compare y
        else if ((lastHit.y - 1) == lastShot.y ||
                (lastHit.y + 1) == lastShot.y) {
            firingOrientation = 2;
        } else {
            firingOrientation = 0;
            //put diagonal coordinate is separate tracker for later
            shotsToCheck.add(lastShot);
        }
    }

    public void updateTrackers(boolean hit, boolean sunkOpponentShip) {
        // reset trackers if the last shot sunk an enemy ship
        if (sunkOpponentShip) {
            lastHit = null;
            firingOrientation = 0;
            firingSequence.clear();
        } else {
            if (hit) {
                // update firingOrientation if it's 0
                if (firingOrientation == 0 && lastHit != null) {
                    setFiringOrientation();
                }
                // add lastHit to the firingSequence
                lastHit = new Coordinates(lastShot);
                firingSequence.add(new Coordinates(lastHit));
//                System.out.printf("adding %s to the firing sequence%n", lastHit); // todo remove print calls
            } else {
                if (!firingSequence.isEmpty()) {
//                    System.out.println("firing sequence: " + firingSequence);
                    lastHit = new Coordinates(firingSequence.get(0));
//                    System.out.printf("setting lastHit to: %s, orientation: %d%n", lastHit, firingOrientation);
//                    nextShot = getNextShot();
                }
            }
        }
    }
}
