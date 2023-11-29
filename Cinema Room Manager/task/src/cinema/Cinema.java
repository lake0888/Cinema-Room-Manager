package cinema;

import java.util.Scanner;

public class Cinema {
    final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Write your code here
        //INIT CINEMA
        boolean[][] cinema = initCinema();
        //INIT MENU
        initMenu(cinema);
    }

    public static boolean[][] initCinema() {
        System.out.println("Enter the number of rows: ");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int seats = scanner.nextInt();

        boolean[][] matrix = new boolean[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                matrix[i][j] = false;
            }
        }
        return matrix;
    }

    public static void showMenu() {
        String menu = """
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                """;
        System.out.print(menu);
    }

    public static void initMenu(boolean[][] cinema) {
        //Statistics
        int numberOfPurchasedTickets = 0;
        int income = 0;

        int option = -1;
        while (option != 0) {
            showMenu();
            try {
                option = Integer.parseInt(scanner.next());
                switch (option) {
                    case 1 -> printCinema(cinema);
                    case 2 -> {
                        income += buyTicket(cinema);
                        numberOfPurchasedTickets++;
                    }
                    case 3 -> showStatistics(cinema, numberOfPurchasedTickets, income);
                    default -> {
                        continue;
                    }
                }
            } catch (NumberFormatException ex) {
                continue;
            }
        }
    }

    public static void printCinema(boolean[][] cinema) {
        System.out.println("Cinema:");
        for (int i = 0; i < cinema.length + 1; i++) {
            for (int j = 0; j < cinema[0].length + 1; j++) {
                if (i == 0 && j == 0) System.out.print("  ");
                else if (i == 0) System.out.printf("%d ", j);
                else if (j == 0) System.out.printf("%d ", i);
                else System.out.printf("%c ", printSeat(cinema, i - 1, j - 1));
            }
            System.out.println();
        }
        System.out.println();
    }

    public static char printSeat(boolean[][] cinema, int row, int seat) {
        return cinema[row][seat] ? 'B' : 'S';
    }

    public static int calculateProfit(boolean[][] cinema) {
        int rows = cinema.length, seatsInRow = cinema[0].length;

        int seats = rows * seatsInRow;
        int profit = 0;
        if (seats <= 60) {
            profit = seats * 10;
        } else {
            int frontHalf = rows / 2;
            int backHalf = rows - frontHalf;
            profit = (frontHalf * seatsInRow) * 10 + (backHalf * seatsInRow) * 8;
        }
        return profit;
    }

    public static int buyTicket(boolean[][] cinema) {
        boolean flag = true;
        int price = 0;
        int rows = cinema.length;
        int seatInRow = cinema[0].length;
        while (flag) {
            System.out.println("Enter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat = scanner.nextInt();

            if (row > rows || seat > seatInRow) {
                System.out.println("Wrong input!\n");
            } else if (cinema[row - 1][seat - 1]) {
                System.out.println("That ticket has already been purchased!\n");
            } else {
                cinema[row -1][seat - 1] = true;
                price = calculateTicket(cinema, row);
                System.out.printf("Ticket price: $%d%n", price);
                flag = false;
            }
        }
        return price;
    }

    public static int calculateTicket(boolean[][] cinema, int row) {
        int rows = cinema.length;
        int seatInRow = cinema[0].length;

        int totalSeats = rows * seatInRow;
        if (totalSeats <= 60) return 10;

        int frontHalf = rows / 2;
        if (row <= frontHalf) return 10;
        return 8;
    }

    public static void showStatistics(boolean[][] cinema, int tickets, int income) {
        System.out.printf("Number of purchased tickets: %d%n", tickets);

        int totalSeats = cinema.length * cinema[0].length;
        float percentage = (float)tickets/totalSeats * 100;
        System.out.printf("Percentage: %.2f%%%n", percentage);

        System.out.printf("Current income: $%d%n", income);
        System.out.printf("Total income: $%d%n%n", calculateProfit(cinema));
    }
}