package battleship.IO;

import battleship.ships.Coordinate;

import java.util.HashMap;
import java.util.Scanner;

public class IOUtil {

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
        System.out.println();
        System.out.println("Welcome to Battleship!");
    }

    public static String askInput(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(question);

        return scanner.nextLine();
    }

    public static void printExclamation(String exclamation) {
        System.out.println(exclamation);
    }

    public static void printGrid(HashMap<Coordinate, String> grid) {
        printHorizontalIdentifiers();
        for (int y = 1; y <= 10; y++) {
            System.out.printf("%02d", y);
            for (int x = 1; x <= 10; x++) {
                System.out.print("  " + grid.get(new Coordinate(x,y)));
            }
            System.out.println();
        }
    }

    private static void printHorizontalIdentifiers() {
        System.out.println("    A  B  C  D  E  F  G  H  I  J");
    }
}
