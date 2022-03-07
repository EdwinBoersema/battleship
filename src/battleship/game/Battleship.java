package battleship.game;

import battleship.IO.IOUtil;
import battleship.player.ComputerPlayer;
import battleship.player.HumanPlayer;
import battleship.player.Player;
import battleship.ships.Coordinates;
import battleship.ships.Ship;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

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
        // loop through players
        // pick a random player to start
        Player currentPlayer = players.get(new Random().nextInt(0,2)); // todo refactor into instance variables
        Player otherPlayer = currentPlayer.equals(players.get(1)) ? players.get(0) : players.get(1); // todo maybe change with counter for easier assigning of players
//        Player currentPlayer = players.get(0);
//        Player otherPlayer = players.get(1);
        State gameState= State.VALID;
        while (gameState == State.VALID) {
            if (currentPlayer instanceof HumanPlayer) {
                // print out boards
                IOUtil.printGrid(currentPlayer.getGrid());
                IOUtil.printGrid(currentPlayer.getOpponentGrid());
            }

            // get coordinate
            Coordinates coordinate = currentPlayer.play();

            // check if a ship is hit
            Optional<Ship> shipOptional = otherPlayer.handleHit(coordinate);
            // set name and shot variables depending on the Optional
            Ship ship = shipOptional.orElse(null);

            //print out result
            String message = currentPlayer.notify(shipOptional.isPresent(), ship);
            IOUtil.printExclamation(message);
            if (currentPlayer instanceof ComputerPlayer) {
                ((ComputerPlayer) currentPlayer).sleep(1);
            }

            // update grids
            updateGrids(coordinate, shipOptional.isPresent(), currentPlayer, otherPlayer);

            // validate gameState // todo refactor into method
            if (otherPlayer.areAllShipsSunken()) {
                Player playerOne = players.get(0);
                gameState = currentPlayer.equals(playerOne) ? State.PLAYER_ONE_WON : State.PLAYER_TWO_WON;
            }

            // change players // todo refactor into method
            currentPlayer = currentPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
            otherPlayer = otherPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
        }

        // print out end message
        String winner;
        if (gameState == State.PLAYER_ONE_WON) { // todo move to IOUtils
            winner = players.get(0).getName();
        } else {
            winner = players.get(1).getName();
        }
        IOUtil.printExclamation(String.format("%s has won!", winner));

        return this;
    }

    private void updateGrids(Coordinates coordinate, boolean hit, Player currentPlayer, Player otherPlayer) {
        var symbol = hit ? "X" : "+";
        currentPlayer.updateShotsGrid(coordinate);
        currentPlayer.updateOpponentGrid(coordinate, symbol);
        otherPlayer.updateGrid(coordinate, symbol);
    }

}