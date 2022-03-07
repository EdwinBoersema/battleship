package battleship.player;

import battleship.ships.*;

import java.util.*;
import java.util.stream.Stream;

public abstract class Player {
    protected HashMap<Coordinates, String> grid; // todo move to human player
    protected HashMap<Coordinates, String> opponentGrid; // todo move to human player
    protected Ship[] ships;
    protected String name;
    protected List<Coordinates> shots = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.createGrids();
        this.createShips();
    }

    private void createGrids() {
        grid = new HashMap<>();
        opponentGrid = new HashMap<>();
        for (int y = 1; y <= 10; y++) {
            for (int x = 1; x <= 10; x++) {
                grid.put(new Coordinates(x, y), ".");
                opponentGrid.put(new Coordinates(x,y), ".");
            }
        }
    }

    private void createShips() {
        ships = new Ship[5];
        ships[0] = new Carrier();
        ships[1] = new Battleship();
        ships[2] = new Cruiser();
        ships[3] = new Submarine();
        ships[4] = new Destroyer();

//        ships = new Ship[]{new Destroyer()};
    }

    // check horizontal coordinates
    protected List<Coordinates> checkHorizontalCoordinates(Coordinates startingCoordinate, int length) {
        // determine the number of integers generated
        int end = (startingCoordinate.x + length) >= 10 ? 10 - startingCoordinate.x : length;

        // get empty coordinates
        return Stream.iterate(startingCoordinate.x, n -> n + 1)
                .limit(end)
                .map(x -> new Coordinates(x, startingCoordinate.y))
                .filter(this::isEmpty)
                .toList();
    }

    // check vertical coordinates
    protected List<Coordinates> checkVerticalCoordinates(Coordinates startingCoordinate, int length) {
        // determine the number of integers generated
        int end = (startingCoordinate.y + length) >= 10 ? 10 - startingCoordinate.y : length;

        // get empty coordinates
        return Stream.iterate(startingCoordinate.y, n -> n + 1)
                .limit(end)
                .map(y -> new Coordinates(startingCoordinate.x, y))
                .filter(this::isEmpty)
                .toList();
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

    public HashMap<Coordinates, String> getGrid() {
        return grid;
    }

    public List<Coordinates> getShots() {
        return shots;
    }

    public void updateGrid(Coordinates coordinate, String symbol) {
        grid.put(coordinate, symbol);
    }

    public HashMap<Coordinates, String> getOpponentGrid() {
        return opponentGrid;
    }

    public void updateOpponentGrid(Coordinates coordinate, String symbol) {
        opponentGrid.replace(coordinate, symbol);
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
