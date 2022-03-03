package battleship.player;

import battleship.IO.Shot;
import battleship.game.State;
import battleship.ships.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Player {
    protected HashMap<Coordinate, String> grid;
    protected HashMap<Coordinate, String> opponentGrid; // todo move to human player
    protected Ship[] ships;
    protected String name;
    protected List<Coordinate> shots = new ArrayList<>();

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
                grid.put(new Coordinate(x, y), ".");
                opponentGrid.put(new Coordinate(x,y), ".");
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
    }

    /*
     * todo: replace with abstract function, move existing function to Computer player
     * Assigns a set of coordinates to each ship
     * and places the ship's ascii in the grid
     */
    public void placeShips() {
        for (Ship ship : ships) {
            List<Coordinate> coordinates = getValidCoordinates(ship);
            // set ship coordinates and add coordinates to the grid
            ship.setCoordinates(coordinates);
            for (int i = 0; i < ship.getSize(); i ++) {
                Coordinate coordinate = coordinates.get(i);
                grid.replace(coordinate, ship.getAscii(i));
            }

            System.out.printf("%s at coordinates: ", ship.getName()); // todo remove after testing
            coordinates.forEach(System.out::print);
            System.out.print("\n");
        }

    }

    private List<Coordinate> getValidCoordinates(Ship ship) {
        Supplier<Integer> randomInt = () -> new Random().nextInt(1, 11);
        Supplier<Boolean> randomBoolean = () -> new Random().nextBoolean();

        boolean validCoordinate = false;
        List<Coordinate> coordinates = new ArrayList<>();
        // check new coordinates until a valid row or column of Coordinates is found
        while (!validCoordinate) {
            Coordinate randomCoordinate = new Coordinate(randomInt.get(), randomInt.get());
            // get horizontal or vertical coordinates depending on the random boolean
            if (randomBoolean.get()) {
                coordinates = getHorizontalCoordinates(randomCoordinate, ship.getSize());
                // set ship's alignment
                ship.setHorizontalAlignment(true);
            } else {
                coordinates = getVerticalCoordinates(randomCoordinate, ship.getSize());
                // set ship's alignment
                ship.setHorizontalAlignment(false);
            }
            // update boolean
            validCoordinate = coordinates.size() == ship.getSize();
        }
        return coordinates;
    }

    // check horizontal coordinates
    private List<Coordinate> getHorizontalCoordinates(Coordinate startingCoordinate,int length) {
        // determine the number of integers generated
        int end = (startingCoordinate.x + length) >= 9 ? 10 - startingCoordinate.x : length;

        // get empty coordinates
        return Stream.iterate(startingCoordinate.x, n -> n + 1)
                .limit(end)
                .map(x -> new Coordinate(x, startingCoordinate.y))
                .filter(this::isEmpty)
                .toList();
    }

    // check vertical coordinates
    private List<Coordinate> getVerticalCoordinates(Coordinate startingCoordinate,int length) {
        // determine the number of integers generated
        int end = (startingCoordinate.y + length) >= 9 ? 10 - startingCoordinate.y : length;

        // get empty coordinates
        return Stream.iterate(startingCoordinate.y, n -> n + 1)
                .limit(end)
                .map(y -> new Coordinate(startingCoordinate.x, y))
                .filter(this::isEmpty)
                .toList();
    }

    public boolean isEmpty(Coordinate coordinate) {
        boolean empty = Stream.of(
                getShipCoordinates(),
                shots)
                .flatMap(Collection::stream)
                .anyMatch(c -> c.equals(coordinate));
        return !empty;
    }

    public Optional<Ship> handleHit(Coordinate coordinate) { // todo maybe move to Ship and make static methods ???
        // check if the coordinate matches a ship's coordinate
        Optional<Ship> shipOptional = Arrays.stream(ships)
                .filter(s -> s.isHit(coordinate))
                .findFirst();
        // if the optional has a ship, update ship's hitCounter
        shipOptional.ifPresent(Ship::updateHitCounter);

        // return whether a ship was hit or not
        return shipOptional;
    }

    public boolean areAllShipsSunken() {
        return Arrays.stream(ships)
                .map(Ship::isSunk)
                .allMatch(b -> b.equals(true));
    }

    // getters and setters

    public List<Coordinate> getShipCoordinates() {
        return Arrays.stream(ships)
                .map(Ship::getCoordinates)
                .flatMap(Collection::stream)
                .toList();
    }

    public HashMap<Coordinate, String> getGrid() {
        return grid;
    }

    public List<Coordinate> getShots() {
        return shots;
    }

    public void updateGrid(Coordinate coordinate, Shot shot) {
        grid.put(coordinate, shot.value);
    }

    public HashMap<Coordinate, String> getOpponentGrid() {
        return opponentGrid;
    }

    public void updateOpponentGrid(Coordinate coordinate, Shot shot) {
        opponentGrid.replace(coordinate, shot.value);
    }

    public void updateShotsGrid(Coordinate coordinate) {
        shots.add(coordinate);
    }

    public abstract Coordinate play();
}
