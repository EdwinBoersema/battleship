package battleship.game;

import battleship.IO.IOUtil;
import battleship.player.ComputerPlayer;
import battleship.player.HumanPlayer;
import battleship.player.Player;

import java.util.ArrayList;

public class Battleship implements Game{
    private ArrayList<Player> players = new ArrayList<>();

    public Battleship() {
    }

    @Override
    public Game createGame() {
        IOUtil.printStartScreen();
        // create players
//        String playerName = IOUtil.askInput("Enter your name: ");
        String playerName = "Edwin";
        Player human = new HumanPlayer(playerName);

        players.add(human);
        players.add(new ComputerPlayer());

        IOUtil.printExclamation("Welcome " + playerName + ", we're going to play a game of Battleship.");
        // initialize grids
//        IOUtil.printExclamation("Below you see your board.");
//        IOUtil.printGrid(human.getGrid());
//        IOUtil.printExclamation("");
//        IOUtil.printGrid(human.getGuesses());

        // fill grids
        human.placeShips();
        return this;
    }

    @Override
    public Game startGame() {
        // play game

        // loop through players
        return this;
    }
}
