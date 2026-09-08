package org.example;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Main {

    public static List<Expense> expenses = new ArrayList<>();

    public static ObjectMapper mapper = new ObjectMapper();

    public static Scanner sc = new Scanner(System.in);

    public static final String DATA_FILE = "expenses.json";

    public static void main(String[] args) {

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        loadExpenses();

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
                    sc.nextLine();
                    System.out.println("Update an expense: ");
                    break;
                case 3:
                    sc.nextLine();
                    System.out.println("Delete an expense: ");
                    deleteExpense();
                    break;
                case 4:
                    sc.nextLine();
                    System.out.println("View all expenses: ");
                    viewAllExpenses();
                    break;
                case 5:
                    sc.nextLine();
                    System.out.println("View summary of all expenses: ");
                    break;
                case 6:
                    sc.nextLine();
                    System.out.println("View summary of expense for an especific month (of year): ");
                    break;
                case 7:
                    sc.nextLine();
                    System.out.println("Quitting...");
                    saveExpenses();
                    break;
                default:
                    System.out.println("Invalid option!");
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
        String description = sc.nextLine();
        System.out.print("--amount: ");
        double amount = sc.nextDouble();

        if(!description.isEmpty() && amount > 0) {
            Expense expense = new Expense(description, amount);
            expenses.add(expense);
            saveExpenses();
            System.out.println("Expense successfully created!!!");
            System.out.println("ID: " + expense.getId());
            System.out.println("Date: " + expense.getDate());
            System.out.println("Description: " + description);
            System.out.println("Amount: $" + String.format("%.2f", amount));
        }

        System.out.print("\nPress enter to continue...");
        sc.nextLine();
        sc.nextLine();
    }

    public static void viewAllExpenses() {
        try {
            Expense[] expensesArray = mapper.readValue(new File(DATA_FILE), Expense[].class);

            Arrays.sort(expensesArray, (e1, e2) -> Integer.compare(e2.getId(), e1.getId()));

            System.out.println("--- Expenses ---");

            for (Expense expense : expensesArray) {
                System.out.println("\nID:" + expense.getId()
                        + " Date: " + expense.getDate()
                        + " Description: " + expense.getDescription()
                        + " Amount:$"
                        + String.format("%.2f", expense.getAmount()));
            }

            System.out.print("\nPress enter to continue...");
            sc.nextLine();
            sc.nextLine();

        } catch (Exception e) {
            System.err.println(e.getMessage());;
        }
    }

    public static void deleteExpense() {
        try {
            loadExpenses();

            expenses.sort((e1, e2) -> Integer.compare(e2.getId(), e1.getId()));

            System.out.println("--- Expenses ---");

            if (expenses.isEmpty()) {
                System.out.println("No expenses to delete!");
                System.out.print("\nPress enter to continue...");
                sc.nextLine();
                return;
            }

            for (Expense expense : expenses) {
                System.out.println("\nID:" + expense.getId()
                        + " Date: " + expense.getDate()
                        + " Description: " + expense.getDescription()
                        + " Amount:$"
                        + String.format("%.2f", expense.getAmount()));
            }

            System.out.print("\nSelect an expense ID to delete: ");
            int idSelected = sc.nextInt();
            sc.nextLine();

            System.out.print("Are you sure you want to delete expense #" + idSelected + "? (y/n): ");
            String confirmation = sc.nextLine();

            if (!confirmation.equalsIgnoreCase("y")) {
                System.out.println("Deletion cancelled.");
                System.out.print("\nPress enter to continue...");
                sc.nextLine();
                return;
            }

            List<Expense> updatedList = expenses.stream()
                    .filter(expense -> expense.getId() != idSelected)
                    .collect(Collectors.toList());

            if (updatedList.size() == expenses.size()) {
                System.out.println("Expense with ID " + idSelected + " not found!");
            } else {
                expenses = updatedList;
                saveExpenses();
                System.out.println("Expense with ID " + idSelected + " deleted successfully!");
            }

            System.out.print("\nPress enter to continue...");
            sc.nextLine();

        } catch (Exception e) {
            System.err.println("Error deleting expense: " + e.getMessage());
        }
    }

    private static void saveExpenses() {
        try {
            mapper.writeValue(new File(DATA_FILE), expenses);
            // System.out.println("Expense saved!!!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadExpenses() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try {
                expenses = mapper.readValue(file, new TypeReference<List<Expense>>() {});
                System.out.println("Loaded " + expenses.size() + " expenses from file.");
                System.out.println("File: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error loading expenses: " + e.getMessage());
                expenses = new ArrayList<>();
            }
        } else {
            System.out.println("📝 No saved data found. Starting fresh!");
            System.out.println("📁 New file will be created at: " + file.getAbsolutePath());
            expenses = new ArrayList<>();
        }
    }

}