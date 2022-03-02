package battleship.game;

import battleship.IO.IOUtil;
import battleship.player.ComputerPlayer;
import battleship.player.HumanPlayer;
import battleship.player.Player;
import battleship.ships.Coordinate;

import java.util.ArrayList;

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
        players.get(0).placeShips();
        return this;
    }

    @Override
    public Game startGame() {
        // play game
        Player player = players.get(0);
        player.updateOpponentGrid(new Coordinate(2,2), HIT);
        player.updateOpponentGrid(new Coordinate(2,3), HIT);
        player.updateOpponentGrid(new Coordinate(2,4), MISS);
        player.updateShotsGrid(new Coordinate(2,2));
        IOUtil.printGrid(player.getOpponentGrid());
        // loop through players
        return this;
    }
}
