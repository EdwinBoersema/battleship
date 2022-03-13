package battleship.player;

import battleship.IO.IOUtil;
import battleship.ships.Coordinates;
import battleship.ships.Ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HumanPlayer extends Player{
    protected HashMap<Coordinates, String> grid;
    protected HashMap<Coordinates, String> opponentGrid;

    public HumanPlayer(String name) {
        super(name);
        this.createGrids();
    }

    // creates the player's grids for visual display
    private void createGrids() {
        grid = new HashMap<>();
        opponentGrid = new HashMap<>();
        for (int y = 1; y <= 10; y++) {
            for (int x = 1; x <= 10; x++) {
                grid.put(new Coordinates(x, y), "~");
                opponentGrid.put(new Coordinates(x,y), "~");
            }
        }
    }

    // gets player input for the next target coordinates
    @Override
    public Coordinates play() {
        // get valid coordinate
        while (true) {
            Coordinates coordinates = getCoordinates("Type in a coordinate to fire at (e.g. \"C4\"): ");
            if (shots.contains(coordinates)) {
                // if coordinate has been tried already, notify user
                IOUtil.printExclamation("That coordinate has been tried already, guess again.");
            } else {
                // if not, notify player and get input again
                return coordinates;
            }
        }
    }

    // lets the player place their ships
    @Override
    public void placeShips() {
        // loop through ships
        for (Ship ship : ships) {
            // get valid coordinates for each ship
            boolean valid = false;
            boolean orientation = false;
            List<Coordinates> coordinatesList = new ArrayList<>();
            while (!valid) {
                Coordinates coordinates = getCoordinates(String.format("Type the starting point where you would like to put your %s at: ", ship.getName()));
                orientation = getOrientation();
                coordinatesList = checkCoordinates(coordinates, orientation, ship.getSize());
                valid = coordinatesList.size() == ship.getSize();
                if (!valid) {
                    IOUtil.printExclamation("Invalid position.");
                }
            }
            // set ship's orientation
            ship.setOrientation(orientation);
            // if coordinate and orientation are valid, place ship in grid and set ship's coordinates
            for (int i = 0; i < ship.getSize(); i++) {
                grid.put(coordinatesList.get(i), ship.getAscii(i));
            }
            ship.setCoordinates(coordinatesList);
            IOUtil.printGrid(grid);
        }
    }

    // gets and validates coordinates for the next ship from the player
    private Coordinates getCoordinates(String message) {
        String characterArray = "abcdefghij";
        while (true) {
            // ask for coordinates
            String input = IOUtil.askInput(message);
            try {
                String letter = input.substring(0,1).toLowerCase();
                // parse 2nd character to integer
                int y = Integer.parseInt(input.substring(1));
                // check if input matches expected format
                if (letter.matches("[a-j]") &&
                        (y > 0 && y <= 10)) {
                    // convert input into coordinate
                    int x = characterArray.indexOf(letter) + 1;
                    return new Coordinates(x, y);
                } else {
                    // if input doesn't match format, notify player and get input again
                    IOUtil.printExclamation("Invalid input, a valid input would be: \"E3\"");
                }
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                IOUtil.printExclamation("Invalid input, a valid input would be: \"E3\"");
            }
        }
    }

    /*
     * gets the orientation for the next ship from the player
     * and converts it into a boolean
     */
    private boolean getOrientation() {
        while (true) {
            String input = IOUtil.askInput("Now type the orientation of the ship (horizontal or vertical): ").toLowerCase();
            if (input.equals("horizontal") || input.equals("h")) {
                return true;
            } else if (input.equals("vertical") || input.equals("v")) {
                return false;
            } else {
                IOUtil.printExclamation("Invalid input, type in either 'horizontal' or 'vertical'");
            }
        }
    }

    // checks the List of coordinates depending on the orientation
    private List<Coordinates> checkCoordinates(Coordinates coordinates, boolean orientation, int length) {
        if (orientation) {
            return checkHorizontalCoordinates(coordinates, length);
        } else {
            return checkVerticalCoordinates(coordinates, length);
        }
    }

    // notifies the player of the result of their guess
    @Override
    public String notify(boolean hit, Ship ship) {
        if (ship != null && ship.isSunk()) {
            return String.format("You sunk the enemies %s!", ship.getName());
        } else {
            String result = hit ? "hit" : "miss";
            return String.format("It's a %s!", result);
        }
    }

    public HashMap<Coordinates, String> getGrid() {
        return grid;
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
}
