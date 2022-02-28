package battleship.player;

import battleship.ships.*;

import java.util.Random;
import java.util.function.Supplier;

public abstract class Player {
    protected String[][] myGrid;
    protected String[][] opponentGrid;
    protected Ship[] ships;
    protected String name;

    public Player(String name) {
        this.name = name;
        this.createGrid();
        this.createOpponentGrid();
        this.createShips();
    }

    private void createGrid() {
        myGrid = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                myGrid[i][j] = ".";
            }
        }
    }

    private void createOpponentGrid() {
        opponentGrid = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
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

    private void placeShips() {
        Supplier<Integer> random = () -> new Random().nextInt(0, 10);
        /*
         * todo: get valid coordinates for Ship X
         *  horizontal or vertical
         *  for the entire length of X
         */
//        Coordinate coordinate = >> getValidCoordinates()
    }

//    private Coordinate[] getValidCoordinates() {
//
//    }

    // getters and setters

    public String[][] getGrid() {
        return myGrid;
    }

    public String[][] getGuesses() {
        return opponentGrid;
    }

    public abstract void play();
}
