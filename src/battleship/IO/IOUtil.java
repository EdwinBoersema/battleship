package battleship.IO;

import battleship.ships.Coordinates;

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
        System.out.print("\n");
        System.out.println("Welcome to Battleship!");
        System.out.println("\n");
    }

    public static String askInput(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(question);

        return scanner.nextLine();
    }

    public static void printExclamation(String exclamation) {
        System.out.println(exclamation);
        System.out.print("\n");
    }

    public static void printGrid(HashMap<Coordinates, String> grid) {
        System.out.println("    A  B  C  D  E  F  G  H  I  J");
        for (int y = 1; y <= 10; y++) {
            System.out.printf("%02d", y);
            for (int x = 1; x <= 10; x++) {
                System.out.print("  " + grid.get(new Coordinates(x,y)));
            }
            System.out.println();
        }
    }

    public static void printDisplays(HashMap<Coordinates, String> grid1, HashMap<Coordinates, String> grid2) {
        System.out.println("             Your sea                         Your opponents sea    ");
        System.out.println("    A  B  C  D  E  F  G  H  I  J        A  B  C  D  E  F  G  H  I  J");
        for (int y = 1; y <= 10; y++) {
            System.out.printf("%02d", y);
            for (int x = 1; x <= 10; x++) {
                System.out.print("  " + grid1.get(new Coordinates(x,y)));
            }
            System.out.print("    ");
            System.out.printf("%02d", y);
            for (int x = 1; x <= 10; x++) {
                System.out.print("  " + grid2.get(new Coordinates(x,y)));
            }
            System.out.println();
        }
    }
}
