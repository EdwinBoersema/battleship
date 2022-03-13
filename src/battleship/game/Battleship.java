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
    private final ArrayList<Player> players = new ArrayList<>();
    private State gameState = State.VALID;
    private Player currentPlayer;
    private Player nextPlayer;

    /*
     * creates the player objects,
     * prints out the grids for visual representation,
     * and places the ships on the grids
     */
    @Override
    public Game createGame() {
        IOUtil.printStartScreen();
        // create players
        String playerName = IOUtil.askInput("Enter your name: ");
        HumanPlayer human = new HumanPlayer(playerName);

        // add players to player arraylist
        players.add(human);
        players.add(new ComputerPlayer());

        // print some welcome messages
        IOUtil.printExclamation("Hello " + playerName + ", we're going to play a game of Battleship.");
        // initialize grids
        IOUtil.printExclamation("Below you see your sea.                And your opponents sea with your shots. (An 'X' marks a hit, a '+' marks a miss.)");
        IOUtil.printDisplays(human.getGrid(), human.getOpponentGrid());

        // place ships
        players.forEach(Player::placeShips);
        return this;
    }

    /*
     * handles the core game loop,
     * and prints out an appropriate message at the end of the game
     */
    @Override
    public Game startGame() {
        // loop through players
        // pick a random player to start
        currentPlayer = players.get(new Random().nextInt(0,2));
        nextPlayer = currentPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
        while (gameState == State.VALID) {
            // print out boards if the current player is a human player
            if (currentPlayer instanceof HumanPlayer) {
                HumanPlayer player = (HumanPlayer) currentPlayer;
                IOUtil.printDisplays(player.getGrid(), player.getOpponentGrid());
            }

            // get coordinate
            Coordinates coordinate = currentPlayer.play();

            // check if a ship is hit
            Optional<Ship> shipOptional = nextPlayer.handleHit(coordinate);

            //print out result
            String message = currentPlayer.notify(shipOptional.isPresent(), shipOptional.orElse(null));
            IOUtil.printExclamation(message);
            if (currentPlayer instanceof ComputerPlayer) {
                ComputerPlayer player = (ComputerPlayer) currentPlayer;
                // let the computer wait for a second so the player can see what happens
                player.sleep(1);
                // update computer's trackers
                boolean sunkOpponentShip = shipOptional.isPresent() && shipOptional.get().isSunk();
                player.updateTrackers(shipOptional.isPresent(), sunkOpponentShip);
            }

            // update grids
            updateGrids(coordinate, shipOptional.isPresent(), currentPlayer, nextPlayer);

            // validate gameState
            validateGameState();

            // change players
            switchPlayers();
        }
        // print out end message
        IOUtil.printExclamation(getEndMessage());

        return this;
    }

    // updates the grids of a HumanPlayer
    private void updateGrids(Coordinates coordinate, boolean hit, Player currentPlayer, Player nextPlayer) {
        var symbol = hit ? "X" : "+";
        currentPlayer.updateShotsGrid(coordinate);
        if (currentPlayer instanceof HumanPlayer) {
            ((HumanPlayer) currentPlayer).updateOpponentGrid(coordinate, symbol);
        }
        if (nextPlayer instanceof HumanPlayer) {
            ((HumanPlayer) nextPlayer).updateGrid(coordinate, symbol);
        }
    }

    // validates the gameState by checking if all ships of the next player have been sunk
    private void validateGameState() {
        if (nextPlayer.areAllShipsSunken()) {
            Player playerOne = players.get(0);
            gameState = currentPlayer.equals(playerOne) ? State.PLAYER_ONE_WON : State.PLAYER_TWO_WON;
        }
    }

    // switches currentPlayer and nextPlayer
    private void switchPlayers() {
        currentPlayer = currentPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
        nextPlayer = nextPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
    }

    /*
     * gets the message at the end of the game
     * depending on which player won
     */
    private String getEndMessage() {
        String winner;
        if (gameState == State.PLAYER_ONE_WON) {
            winner = players.get(0).getName();
        } else {
            winner = players.get(1).getName();
        }
        return String.format("%s has won!", winner);
    }
}