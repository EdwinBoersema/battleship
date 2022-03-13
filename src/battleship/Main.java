package battleship;

import battleship.game.Battleship;
import battleship.game.Game;

public class Main {
    public static void main(String[] args) {
        Game battleships = new Battleship();
        battleships.createGame().startGame();
    }
}