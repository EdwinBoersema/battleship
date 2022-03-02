package battleship.player;

import battleship.IO.IOUtil;
import battleship.ships.Coordinate;

public class HumanPlayer extends Player{

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Coordinate play() {
        String characterArray = "ABCDEFGHJI";

        // get valid coordinate
        while (true) {
            String input = IOUtil.askInput("Type in a coordinate to fire at (e.g. \"C4\"): ");
            // parse 2nd character to int
            try {
                String letter = input.substring(0,1);
                int y = Integer.parseInt(input.substring(1,2));
                // check if input matches expected format
                if (letter.matches("[a-iA-I]") &&
                        (y > 0 && y <= 10)) {
                    // convert input into coordinate
                    int x = characterArray.indexOf(letter);
                    Coordinate shot = new Coordinate(x, y);
                    if (shots.contains(shot)) {
                        // if coordinate has been tried already, notify user
                        IOUtil.printExclamation("That coordinate has been tried already, guess again.");
                    } else {
                        return shot;
                    }
                } else {
                    // if not, notify player and get input again
                    System.out.println("Invalid input, a valid input would be: \"E3\"");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, a valid input would be: \"E3\"");
            }
        }
    }
}
