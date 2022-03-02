package battleship.player;

import battleship.ships.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Player {
    protected HashMap<Coordinate, String> grid;
    protected HashMap<Coordinate, String> opponentGrid;
    protected Ship[] ships;
    protected String name;
    protected List<Coordinate> gridShots = new ArrayList<>();
    // todo: add gridShots | Ship coordinates

    public Player(String name) {
        this.name = name;
        this.createGrids();
        this.createShips();
//        this.placeShips();
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
//        System.out.println(grid.size());
    }

    private void createShips() {
        ships = new Ship[5];
        ships[0] = new Battleship();
        ships[1] = new Carrier();
        ships[2] = new Cruiser();
        ships[3] = new Submarine();
        ships[4] = new Destroyer();
    }

    /*
     * Gets valid coordinates for Ship X
     * horizontal or vertical
     * for the entire size of X
     * Then assigns those coordinates to the ship and puts the ship's ascii in the grid
     */
    public void placeShips() {
        for (Ship ship : ships) {
            List<Coordinate> coordinates = getValidCoordinates(ship, ship.getSize());
            // set ship coordinates and add coordinates to the grid
            ship.setCoordinates(coordinates);
            for (int i = 0; i < ship.getSize(); i ++) {
                Coordinate coordinate = coordinates.get(i);
                grid.replace(coordinate, ship.getAscii(i));
            }

            System.out.printf("%s at coordinates: ", ship.getName());
            coordinates.forEach(System.out::print);
            System.out.print("\n");
        }

    }

    private List<Coordinate> getValidCoordinates(Ship ship, int length) {
        Supplier<Integer> randomInt = () -> new Random().nextInt(1, 11);
        Supplier<Boolean> randomBoolean = () -> new Random().nextBoolean();

        boolean validCoordinate = false;
        List<Coordinate> coordinates = new ArrayList<>();
        // check new coordinates until a valid row or column of Coordinates is found
        while (!validCoordinate) {
            Coordinate randomCoordinate = new Coordinate(randomInt.get(), randomInt.get());
            // get horizontal or vertical coordinates depending on the random boolean
            if (randomBoolean.get()) {
                coordinates = getHorizontalCoordinates(randomCoordinate, length);
                // set ship's alignment
                ship.setHorizontalAlignment(true);
            } else {
                coordinates = getVerticalCoordinates(randomCoordinate, length);
                // set ship's alignment
                ship.setHorizontalAlignment(false);
            }
            // update boolean
            validCoordinate = coordinates.size() == length;
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
                gridShots)
                .flatMap(Collection::stream)
//                .peek(System.out::println)
                .anyMatch(c -> c.equals(coordinate));
        return !empty;
    }

    // getters and setters

    public List<Coordinate> getShipCoordinates() {
        return Stream.of(
                ships[0].getCoordinates(),
                ships[1].getCoordinates(),
                ships[2].getCoordinates(),
                ships[3].getCoordinates(),
                ships[4].getCoordinates())
                .flatMap(Collection::stream)
                .toList();
    }

    public HashMap<Coordinate, String> getGrid() {
        return grid;
    }

    public HashMap<Coordinate, String> getGuesses() {
        return opponentGrid;
    }

    public abstract void play();
}
