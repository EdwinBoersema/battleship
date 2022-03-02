package battleship.player;

import battleship.ships.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Player {
    protected String[][] grid;
    protected String[][] opponentGrid;
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
        grid = new String[10][10];
        opponentGrid = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = ".";
                opponentGrid[i][j] = ".";
            }
        }
    }

    private void createShips() {
        ships = new Ship[5];
        ships[0] = new Battleship();
        ships[1] = new Carrier();
        ships[2] = new Cruiser();
        ships[3] = new Submarine();
        ships[4] = new Destroyer();
    }

    public void placeShips() {
        /*
         * todo: get valid coordinates for Ship X
         *  horizontal or vertical
         *  for the entire length of X
         */
        for (Ship ship : ships) {
            List<Coordinate> coordinates = getValidCoordinates(ship.getSize());
            ship.setCoordinates(coordinates);
            System.out.printf("placed %s at coordinates: ", ship.getName());
            coordinates.forEach(System.out::print);
            System.out.print("\n");
        }

    }

    private List<Coordinate> getValidCoordinates(int length) {
        Supplier<Integer> randomInt = () -> new Random().nextInt(0, 10);
        Supplier<Boolean> randomBoolean = () -> new Random().nextBoolean();

        boolean validCoordinate = false;
        List<Coordinate> coordinates = new ArrayList<>();
        // check new coordinates until a valid row or column of Coordinates is found
        while (!validCoordinate) {
            Coordinate randomCoordinate = new Coordinate(randomInt.get(), randomInt.get());
            // get horizontal or vertical coordinates depending on the random boolean
            if (randomBoolean.get()) {
                coordinates = getHorizontalCoordinates(randomCoordinate, length);
            } else {
                coordinates = getVerticalCoordinates(randomCoordinate, length);
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
//                .peek(System.out::println)
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
//                .peek(System.out::println)
                .toList();
    }

    public boolean isEmpty(Coordinate coordinate) {
//        System.out.println(grid[coordinate.x][coordinate.y]);
        return grid[coordinate.x][coordinate.y].equals(".");
    }

    // getters and setters

    public String[][] getGrid() {
        return grid;
    }

    public String[][] getGuesses() {
        return opponentGrid;
    }

    public abstract void play();
}
