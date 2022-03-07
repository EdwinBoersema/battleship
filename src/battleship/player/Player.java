package battleship.player;

import battleship.ships.*;

import java.util.*;
import java.util.stream.Stream;

public abstract class Player {
    protected Ship[] ships;
    protected String name;
    protected List<Coordinates> shots = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.createShips();
    }

    private void createShips() {
        ships = new Ship[5];
        ships[0] = new Carrier();
        ships[1] = new Battleship();
        ships[2] = new Cruiser();
        ships[3] = new Submarine();
        ships[4] = new Destroyer();
    }

    // check horizontal coordinates
    protected List<Coordinates> checkHorizontalCoordinates(Coordinates startingCoordinate, int length) {
        // determine the number of integers generated
        int limit = getLimit(startingCoordinate.x, length);
        // get empty coordinates
        return Stream.iterate(startingCoordinate.x, n -> n + 1)
                .limit(limit)
                .map(x -> new Coordinates(x, startingCoordinate.y))
                .filter(this::isEmpty)
                .toList();
    }

    // check vertical coordinates
    protected List<Coordinates> checkVerticalCoordinates(Coordinates startingCoordinate, int length) { // fixme doesn't properly work
        // determine the number of integers generated
        int limit = getLimit(startingCoordinate.y, length);
        // get empty coordinates
        return Stream.iterate(startingCoordinate.y, n -> n + 1)
                .limit(limit)
                .map(y -> new Coordinates(startingCoordinate.x, y))
                .filter(this::isEmpty)
                .toList();
    }

    private int getLimit(int start, int length) {
        int end = start + length - 1;
        if (end > 10) {
            return 10 - start;
        } else {
            return length;
        }
    }

    public boolean isEmpty(Coordinates coordinate) {
        boolean empty = Stream.of(
                getShipCoordinates(),
                shots)
                .flatMap(Collection::stream)
                .anyMatch(c -> c.equals(coordinate));
        return !empty;
    }

    public Optional<Ship> handleHit(Coordinates coordinate) {
        Optional<Ship> shipOptional = Optional.empty();

        // check for each ship if it's hit
        for (Ship ship : ships) {
            if (ship.isHit(coordinate)) {
                // if it's hit, update optional
                shipOptional = Optional.of(ship);
                // update ship's hitCounter
                ship.updateHitCounter();
            }
        }

        return shipOptional;
    }

    public boolean areAllShipsSunken() {
        return Arrays.stream(ships)
                .map(Ship::isSunk)
                .allMatch(b -> b.equals(true));
    }

    // getters and setters

    public List<Coordinates> getShipCoordinates() {
        return Arrays.stream(ships)
                .map(Ship::getCoordinates)
                .flatMap(Collection::stream)
                .toList();
    }

    public List<Coordinates> getShots() {
        return shots;
    }

    public void updateShotsGrid(Coordinates coordinate) {
        shots.add(coordinate);
    }

    public String getName() {
        return name;
    }

    public abstract void placeShips();
    public abstract Coordinates play();
    public abstract String notify(boolean hit, Ship ship);
}
