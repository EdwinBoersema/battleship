package battleship.game;

import battleship.IO.IOUtil;
import battleship.player.ComputerPlayer;
import battleship.player.HumanPlayer;
import battleship.player.Player;
import battleship.ships.Coordinate;
import battleship.ships.Ship;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import static battleship.IO.Shot.*;

public class Battleship implements Game{
    private ArrayList<Player> players = new ArrayList<>();

    public Battleship() {
    }

    @Override
    public Game createGame() {
        IOUtil.printStartScreen();
        // create players
//        String playerName = IOUtil.askInput("Enter your name: "); // todo remove after testing
        String playerName = "Edwin";
        Player human = new HumanPlayer(playerName);
        human.placeShips(); // todo remove after testing

        players.add(human);
        players.add(new ComputerPlayer());

        IOUtil.printExclamation("Welcome " + playerName + ", we're going to play a game of Battleship.");
        // initialize grids
        IOUtil.printExclamation("Below you see your sea.");
        IOUtil.printGrid(human.getGrid());
        IOUtil.printExclamation("And your opponents sea with your shots.");
        IOUtil.printExclamation("An 'X' marks a hit, a '+" +
                "' marks a miss.");
        IOUtil.printGrid(human.getOpponentGrid());

        // fill grids
        players.get(1).placeShips();
        // todo >> ask player to place ships
//        players.get(0).placeShips();
        return this;
    }

    @Override
    public Game startGame() {
        // play game
//        Player player = players.get(0);
//        player.updateOpponentGrid(new Coordinate(2,2), HIT);
//        player.updateOpponentGrid(new Coordinate(2,3), HIT);
//        player.updateOpponentGrid(new Coordinate(2,4), MISS);
//        player.updateShotsGrid(new Coordinate(2,2));
//        IOUtil.printGrid(player.getOpponentGrid());
        // loop through players
        // pick a random player to start
//        Player currentPlayer = players.get(new Random().nextInt(0,2));
//        Player otherPlayer = currentPlayer.equals(players.get(1)) ? players.get(0) : players.get(1); // todo maybe change with counter for easier assigning of players
        Player currentPlayer = players.get(0);
        Player otherPlayer = players.get(1);
        State gameState= State.VALID;
        while (gameState == State.VALID) {// todo only print on human turn
            if (currentPlayer instanceof HumanPlayer) {
                // print out boards
                IOUtil.printGrid(currentPlayer.getGrid());
                IOUtil.printGrid(currentPlayer.getOpponentGrid());
            }

            // get coordinate
            Coordinate coordinate = currentPlayer.play();

            // check if a ship is hit
            Optional<Ship> shipOptional = otherPlayer.handleHit(coordinate);

            if (currentPlayer instanceof HumanPlayer) { // todo notify player of the computers guess --> move to first line before new turn or wait a bit...
                IOUtil.printExclamation(String.format("It's a %s!", shipOptional.isPresent() ? "hit" : "miss")); // todo refactor into multiple lines
                // print out an extra message if a ship was sunk
                if (shipOptional.isPresent() && shipOptional.get().isSunk()) { // fixme always prints out the ship even if it didn't sink yet...
                    String message = String.format("You sunk the enemies %s!", shipOptional.get().getName());
                    IOUtil.printExclamation(message);
                }
            }

            // update grids
            currentPlayer.updateShotsGrid(coordinate);
            currentPlayer.updateOpponentGrid(coordinate, (shipOptional.isPresent() ? HIT : MISS));
            otherPlayer.updateGrid(coordinate, (shipOptional.isPresent() ? HIT : MISS));
            System.out.println(currentPlayer.getShots());

            // validate gameState
            if (otherPlayer.areAllShipsSunken()) {
                Player playerOne = players.get(0);
                gameState = currentPlayer.equals(playerOne) ? State.PLAYER_ONE_WON : State.PLAYER_TWO_WON;
            }

            // change players
//            currentPlayer = currentPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
//            otherPlayer = otherPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
//            System.out.println(currentPlayer);
//            System.out.println(otherPlayer);
        }

        // print out end message

        return this;
    }

    // todo add function to handle a players turn, this is getting too complicated
}
