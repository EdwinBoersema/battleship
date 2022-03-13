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
import java.util.stream.Stream;

public class ComputerPlayer extends Player{
    private Coordinates lastShot;
    private Coordinates lastHit;
    private final List<Coordinates> firingSequence = new ArrayList<>();
    int firingOrientation;

    public ComputerPlayer() {
        super("computer");
    }

    /*
     * gets the computer's next coordinates based on previous results,
     * and notifies the human player what coordinate is being targeted
     */
    @Override
    public Coordinates play() {
        // wait one second so the player can see what the computer does
        sleep(1);

        // get the next coordinates
        Coordinates coordinates = getTargetCoordinates();

        // print out the next coordinates to the console
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

    // notifies the human player of the result of the computer's turn
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

    // makes the computer wait for x seconds
    public void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ignored) {}
    }

    // methods to determine the computers next guess

    // gets the next target coordinate
    private Coordinates getTargetCoordinates() {
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

    /*
     * checks the lastHit's adjacent coordinates depending on the firing orientation
     * random one when orientation == 0
     * horizontal for when orientation == 1
     * vertical when orientation == 2
     */
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
        // returns the first horizontal adjacent coordinate that's within bounds and not yet used
        return Stream.of(
                        new Coordinates(lastHit.x + 1, lastHit.y),
                        new Coordinates(lastHit.x - 1, lastHit.y))
                .filter(this::isWithinBounds)
                .filter(c -> !shots.contains(c))
                .findFirst();
    }

    // checks adjacent horizontal coordinates
    private Optional<Coordinates> checkVerticalTargets() {
        // returns the first vertical adjacent coordinate that's within bounds and not yet used
        return Stream.of(
                new Coordinates(lastHit.x, lastHit.y + 1),
                new Coordinates(lastHit.x, lastHit.y - 1))
                .filter(this::isWithinBounds)
                .filter(c -> !shots.contains(c))
                .findFirst();
    }

    // loops through random coordinates until a valid empty one is found
    private Coordinates getRandomTargetCoordinates() {
        Supplier<Integer> r = () -> new Random().nextInt(1, 11);
        while(true) {
            Coordinates targetCoordinates = new Coordinates(r.get(), r.get());
            // validate that coordinate is valid
            if (!shots.contains(targetCoordinates)) {
                // return coordinate
                return targetCoordinates;
            }
        }
    }

    // returns whether the provided coordinates fall within the grids bounds
    private boolean isWithinBounds(Coordinates coordinates) {
        return coordinates.x > 0 &&
                coordinates.x <= 10 &&
                coordinates.y > 0 &&
                coordinates.y <= 10;
    }

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
        }
    }

    // updates the computer's trackers
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
            } else {
                // if nothing was hit, but firing sequence is not empty, set lastHit to the first coordinates in the sequence
                if (!firingSequence.isEmpty()) {
                    lastHit = new Coordinates(firingSequence.get(0));
                }
            }
        }
    }
}
