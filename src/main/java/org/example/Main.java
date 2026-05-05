package org.example;
import java.util.Scanner;

interface ExpenseScreen {
    void mainExpenseMenu(Scanner scan);
    void addExpense(Scanner scan);
//    void updateExpense(String expense, String desc, double amount);
//    void viewAllExpenses();
}

public class Main implements ExpenseScreen {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Main app = new Main();
        app.mainExpenseMenu(scan);
    }

    @Override
    public void mainExpenseMenu(Scanner scan) {
        while(true) {
            System.out.println("\nWelcome to the Expense Tracker CLI!!!");
            System.out.println("1. Add a new expense");
            System.out.println("2. Update a expense");
            System.out.println("3. Delete an expense");
            System.out.println("4. View all expenses");
            System.out.println("5. View summary of all expenses");
            System.out.println("6. View summary expenses for a specific month (of current year)");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");
            int option = scan.nextInt();

            switch (option) {
                case 1:
                    addExpense(scan);
                    break;
                case 7:
                    System.out.println("Quitting...");
                    return;
                default:
                    System.out.println("Invalid option!! Try Again...");
            }

        }

    }

    @Override
    public void addExpense(Scanner scan) {
        while(true) {
            System.out.print("Expense: ");
            scan.next();
            System.out.print("Description: ");
            scan.next();
            System.out.print("Amount: ");
            scan.nextDouble();
            System.out.println("New expense!!!");
            return;
        }
    }

}