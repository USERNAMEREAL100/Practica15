import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicTacToeFullGame {
    static int boardSize = 3;
    static String playerXName = "Гравець X";
    static String playerOName = "Гравець O";
    static final String CONFIG_FILE = "config.txt";
    static final String STATS_FILE = "stats.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        loadConfig();

        while (true) {
            int choice = showMainMenu(scanner);
            if (choice == 4 && confirmExit(scanner)) break;

            switch (choice) {
                case 1:
                    playGame(scanner, random);
                    break;
                case 2:
                    configureSettings(scanner);
                    break;
                case 3:
                    showStatistics();
                    break;
            }
        }
        scanner.close();
    }

    public static int showMainMenu(Scanner scanner) {
        System.out.println("\nГоловне меню:");
        System.out.println("1. Грати(Нова гра)");
        System.out.println("2. Налаштування");
        System.out.println("3. Переглянути статистику");
        System.out.println("4. Вихід");
        System.out.print("Оберіть опцію: ");
        return scanner.nextInt();
    }

    public static boolean confirmExit(Scanner scanner) {
        System.out.print("Ви впевнені, що хочете вийти? (1 - Так, 2 - Ні): ");
        return scanner.nextInt() == 1;
    }

    public static void configureSettings(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Введіть ім'я гравця X: ");
        playerXName = scanner.nextLine();
        System.out.print("Введіть ім'я гравця O: ");
        playerOName = scanner.nextLine();
        System.out.print("Виберіть розмір поля (3, 5, 7, 9): ");
        boardSize = scanner.nextInt();
        saveConfig();
    }

    public static void loadConfig() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE));
            playerXName = reader.readLine();
            playerOName = reader.readLine();
            boardSize = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (Exception e) {
        }
    }

    public static void saveConfig() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE));
            writer.write(playerXName + "\n");
            writer.write(playerOName + "\n");
            writer.write(boardSize + "\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Помилка збереження конфігурації");
        }
    }

    public static void showStatistics() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(STATS_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Немає статистики.");
        }
    }

    public static void saveStatistics(String winner, char playerChar) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(STATS_FILE, true));
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Дата: " + timestamp + ", Переможець: " + winner + " (" + playerChar + "), Розмір поля: " + boardSize + "\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Помилка збереження статистики");
        }
    }

    public static void playGame(Scanner scanner, Random random) {
        char[][] board = new char[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                board[i][j] = ' ';

        char player = 'X';
        int moves = 0;

        while (true) {
            System.out.println("\nХРЕСТИКИ НУЛИКИ:");
            printBoard(board);

            int row, col;
            while (true) {
                System.out.print((player == 'X' ? playerXName : playerOName) + ", введіть координати (рядок стовпець): ");
                row = scanner.nextInt() - 1;
                col = scanner.nextInt() - 1;
                if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && board[row][col] == ' ') break;
                System.out.println("Некоректний хід.");
            }

            board[row][col] = player;
            moves++;

            if (checkWin(board, player)) {
                printBoard(board);
                String winner = player == 'X' ? playerXName : playerOName;
                System.out.println("Переміг: " + winner);
                saveStatistics(winner, player);
                break;
            }

            if (moves == boardSize * boardSize) {
                printBoard(board);
                System.out.println("Нічия.");
                saveStatistics("Нічия", '-');
                break;
            }
            player = (player == 'X') ? 'O' : 'X';
        }
    }

    public static void printBoard(char[][] board) {
        System.out.print("    ");
        for (int i = 1; i <= boardSize; i++) System.out.print(i + "   ");
        System.out.println();

        for (int i = 0; i < boardSize; i++) {
            System.out.print((i + 1) + "  ");
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j]);
                if (j < boardSize - 1) System.out.print("  | ");
            }
            System.out.println();
            if (i < boardSize - 1) {
                System.out.print("   ");
                for (int k = 0; k < boardSize; k++) {
                    System.out.print("---");
                    if (k < boardSize - 1) System.out.print("+");
                }
                System.out.println();
            }
        }
    }

    public static boolean checkWin(char[][] board, char player) {
        for (int i = 0; i < boardSize; i++) {
            boolean row = true, col = true;
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] != player) row = false;
                if (board[j][i] != player) col = false;
            }
            if (row || col) return true;
        }
        boolean mainDiag = true, antiDiag = true;
        for (int i = 0; i < boardSize; i++) {
            if (board[i][i] != player) mainDiag = false;
            if (board[i][boardSize - i - 1] != player) antiDiag = false;
        }
        return mainDiag || antiDiag;
    }
}