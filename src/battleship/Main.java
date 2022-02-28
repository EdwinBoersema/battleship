package battleship;

import battleship.game.Battleship;
import battleship.game.Game;

public class Main {
    public static void main(String[] args) {
        Game battleships = new Battleship();
        battleships.createGame().startGame();
    }
}

/*
 * todo; requirements:
 *  minimal 10 classes
 *  small main method with max 3 method calls
 *  code adheres to all naming conventions
 *  application uses inheritance
 *  application uses polymorphism
 *  Board: 10x10
 *  each ship has to be placed horizontally or vertically, no diagonals
 *  each ship has to completely fall within the coordinates
 *  ships can touch, but not overlap each other
 */

// User Stories
/*
 * done: given that I have a laptop with Java 11
 *  when I start the game
 *  I see a nice starting screen
 */

/*
 * done: Given that I'm at the starting screen
 *  when I enter my name
 *  it will be saved for later
 */

/*
 * todo: Given that I'm at the starting screen
 *  then a grid with the ships will be generated for me and the computer
 */

/*
 * todo: Given that the game is being played
 *  when I look at my screen
 *  then I see my board with my ships and the shots of the computer
 */

/*
 * todo: Given that the game is being played
 *  when I look at my screen
 *  then I see the board of the computer with my shots but without the ships
 */

/*
 * todo: Given that the game is being played
 *  when I enter a coordinate
 *  then the computer tells me whether it's a hit or a miss
 */

/*
 * todo: Given that the game is being played
 *  when not all ships of one player have been sunk
 *  then I can keep guessing coordinates
 */

/*
 * todo: Given that the game is being played
 *  if its the computer's turn
 *  then the computer gives a not yet tried coordinate
 */

/*
 * todo: Given that the game is being played
 *  when I sink a ship of the computer
 *  then I'll be informed of that
 */

/*
 * todo: Given that the game has ended
 *  when I look at my screen
 *  then I see an appropriate message
 */

/*
 * todo: Given that the game starts
 *  when the grid is generates
 *  I can choose where to place my ships
 */

/*
 * todo: Given that the game is being played
 *  when the computer chooses a coordinate
 *  then it does so intelligently based on previous tries
 */