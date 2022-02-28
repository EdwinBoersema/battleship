package battleship.IO;

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

    public static void printGrid(String[][] grid) {
        printHorizontalIdentifiers();
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%02d", i);
            for (int j = 1; j <= 10; j++) {
                System.out.print("  " + grid[i-1][j-1]);
            }
            System.out.println();
        }
    }

    private static void printHorizontalIdentifiers() {
        System.out.println("    a  b  c  d  e  f  g  h  i  j");
    }
}
