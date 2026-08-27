package org.example;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    Expense expense = new Expense();

    ObjectMapper mapper = new ObjectMapper();

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int option = 0;

        do {
            expenseMenu();
            option = sc.nextInt();

            switch (option) {
                case 1:
                    sc.nextLine();
                    addExpense();
                    break;
                case 2:
                    System.out.println("Update an expense: ");
                    break;
                case 3:
                    System.out.println("Delete an expense: ");
                    break;
                case 4:
                    System.out.println("View all expenses: ");
                    break;
                case 5:
                    System.out.println("View summary of all expenses: ");
                    break;
                case 6:
                    System.out.println("View summary of expense for an especific month (of year): ");
                    break;
            }

        } while (option != 7);
        sc.close();
    }

    public static void expenseMenu() {
        System.out.println("-----------------");
        System.out.println("Expense Tracker - WELCOME");
        System.out.println("1.Add an expense");
        System.out.println("2.Update an expense");
        System.out.println("3.Delete an expense");
        System.out.println("4.View all expenses");
        System.out.println("5.View summary of all expenses");
        System.out.println("6.View summary of expense for an especific month (of year)");
        System.out.println("7.Quit...");
        System.out.print("Choose an option: ");
    }

    public static void addExpense() {
        System.out.print("--description: ");
        String description = sc.next();
        System.out.print("--amount: ");
        double amount = sc.nextDouble();

        if(!description.isEmpty() && amount > 0) {
            System.out.println("Expense successfully created!!!");
            System.out.println("Description: " + description);
            System.out.println("Amount: $" + String.format("%.2f", amount));
        }

        System.out.print("\nPress enter to continue...");
        sc.nextLine();
        sc.nextLine();
    }

}