package battleship.IO;

import battleship.ships.Coordinates;

import java.util.HashMap;
import java.util.Scanner;

public class IOUtil {

    // prints out the welcome message
    public static void printStartScreen() {
        System.out.print("""
                 _           _   _   _           _     _
                | |         | | | | | |         | |   (_)
                | |__   __ _| |_| |_| | ___  ___| |__  _ _ __
                | '_ \\ / _` | __| __| |/ _ \\/ __| '_ \\| | '_ \\
                | |_) | (_| | |_| |_| |  __/\\__ \\ | | | | |_) |
                |_.__/ \\__,_|\\__|\\__|_|\\___||___/_| |_|_| .__/
                                                        | |
                                                        |_|""");
        System.out.print("\n");
        System.out.println("Welcome to Battleship!");
        System.out.println("\n");
    }

    // prints out the question and returns the input
    public static String askInput(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(question);

        return scanner.nextLine();
    }

    // prints out the provided message and an empty line
    public static void printExclamation(String exclamation) {
        System.out.println(exclamation);
        System.out.print("\n");
    }

    // prints out the given grid for visual
    public static void printGrid(HashMap<Coordinates, String> grid) {
        System.out.println("    A  B  C  D  E  F  G  H  I  J");
        for (int y = 1; y <= 10; y++) {
            printLine(grid, y);
            System.out.println();
        }
    }

    // prints out 2 grids side to side
    public static void printDisplays(HashMap<Coordinates, String> grid1, HashMap<Coordinates, String> grid2) {
        System.out.println("             Your sea                         Your opponents sea    ");
        System.out.println("    A  B  C  D  E  F  G  H  I  J        A  B  C  D  E  F  G  H  I  J");
        for (int y = 1; y <= 10; y++) {
            // print out line y of player's own sea
            printLine(grid1, y);
            System.out.print("    ");
            // print out line y of player's radar
            printLine(grid2, y);
            System.out.println(Color.RESET.code);
        }
    }

    // prints out one line of the provided Hashmap
    private static void printLine(HashMap<Coordinates, String> grid, int y) {
        System.out.printf("%02d", y);
        for (int x = 1; x <= 10; x++) {
            Color color = getColor(grid.get(new Coordinates(x,y)));
            colorPrint(grid.get(new Coordinates(x,y)), color);
            System.out.print(Color.RESET.code);
        }
    }

    // returns the corresponding color of the provided value
    private static Color getColor(String value) {
        return switch (value) {
            case "X" -> Color.RED;
            case "+" -> Color.WHITE;
            case "~" -> Color.BLUE;
            case "^","v","=","║","<",">" -> Color.GREEN;
            default -> Color.RESET;
        };
    }

    // wraps the value in the color code and prints it to the console
    private static void colorPrint(String value, Color color) {
        System.out.printf("%s  %s%s",
                color.code,
                value,
                color.code
        );
    }

    // formats the given coordinates and prints it to the console
    public static void printComputerCoordinates(Coordinates coordinates) {
        String letters = "ABCDEFGHIJ";
        System.out.printf("The Computer tries: %c%d%n", letters.charAt(coordinates.x -1), coordinates.y);
    }
}
