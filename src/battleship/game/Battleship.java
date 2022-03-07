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
    State gameState = State.VALID;
    Player currentPlayer;
    Player nextPlayer;


    public Battleship() {
    }

    @Override
    public Game createGame() {
        IOUtil.printStartScreen();
        // create players
//        String playerName = IOUtil.askInput("Enter your name: ");
        String playerName = "Edwin";// todo remove after testing
        Player human = new HumanPlayer(playerName);

        players.add(human);
        players.add(new ComputerPlayer());

        IOUtil.printExclamation("Hello " + playerName + ", we're going to play a game of Battleship.");
        // initialize grids
        IOUtil.printExclamation("Below you see your sea.                And your opponents sea with your shots. (An 'X' marks a hit, a '+' marks a miss.)");
        IOUtil.printDisplays(human.getGrid(), human.getOpponentGrid());

        // place ships
        players.forEach(Player::placeShips);
        return this;
    }

    @Override
    public Game startGame() {
        // loop through players
        // pick a random player to start
        currentPlayer = players.get(new Random().nextInt(0,2));
        nextPlayer = currentPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
        while (gameState == State.VALID) {
            if (currentPlayer instanceof HumanPlayer) {
                // print out boards
                IOUtil.printDisplays(currentPlayer.getGrid(), currentPlayer.getOpponentGrid());
            }

            // get coordinate
            Coordinates coordinate = currentPlayer.play();

            // check if a ship is hit
            Optional<Ship> shipOptional = nextPlayer.handleHit(coordinate);

            //print out result
            String message = currentPlayer.notify(shipOptional.isPresent(), shipOptional.orElse(null));
            IOUtil.printExclamation(message);
            // let the computer wait for a second so the player can see what happens
            if (currentPlayer instanceof ComputerPlayer) {
                ((ComputerPlayer) currentPlayer).sleep(1);
            }

            // update grids
            updateGrids(coordinate, shipOptional.isPresent(), currentPlayer, nextPlayer);

            // validate gameState
            validateGameState();

            // change players
            switchPlayers();
        }
        // print out end message
        String message = getEndMessage();
        IOUtil.printExclamation(message);

        return this;
    }

    private void updateGrids(Coordinates coordinate, boolean hit, Player currentPlayer, Player nextPlayer) {
        var symbol = hit ? "X" : "+";
        currentPlayer.updateShotsGrid(coordinate);
        currentPlayer.updateOpponentGrid(coordinate, symbol);
        nextPlayer.updateGrid(coordinate, symbol);
    }

    private void validateGameState() {
        if (nextPlayer.areAllShipsSunken()) {
            Player playerOne = players.get(0);
            gameState = currentPlayer.equals(playerOne) ? State.PLAYER_ONE_WON : State.PLAYER_TWO_WON;
        }
    }

    private void switchPlayers() {
        currentPlayer = currentPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
        nextPlayer = nextPlayer.equals(players.get(1)) ? players.get(0) : players.get(1);
    }

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