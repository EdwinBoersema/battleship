package battleship.player;

import battleship.ships.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Player {
    protected HashMap<Coordinates, String> grid;
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
//        ships = new Ship[5];
//        ships[0] = new Carrier();
//        ships[1] = new Battleship();
//        ships[2] = new Cruiser();
//        ships[3] = new Submarine();
//        ships[4] = new Destroyer();

        ships = new Ship[]{new Destroyer()};
    }

    /*
     * todo: replace with abstract function, move existing function to Computer player
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
                coordinatesList = getHorizontalCoordinates(randomCoordinate, ship.getSize());
                // set ship's alignment
                ship.setHorizontalAlignment(true);
            } else {
                coordinatesList = getVerticalCoordinates(randomCoordinate, ship.getSize());
                // set ship's alignment
                ship.setHorizontalAlignment(false);
            }
            // check list
            if (coordinatesList.size() == ship.getSize()) {
                return coordinatesList;
            }
        }
    }

    // check horizontal coordinates
    private List<Coordinates> getHorizontalCoordinates(Coordinates startingCoordinate, int length) {
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
    private List<Coordinates> getVerticalCoordinates(Coordinates startingCoordinate, int length) {
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

    public abstract Coordinates play();
    public abstract String notify(boolean hit, Ship ship);
}
