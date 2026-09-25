package com.canbey.budgettracker.ui;

import com.canbey.budgettracker.model.Category;
import com.canbey.budgettracker.model.Transaction;
import com.canbey.budgettracker.model.TransactionType;
import com.canbey.budgettracker.service.BudgetService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final BudgetService budgetService;
    private final Scanner scanner;

    public ConsoleMenu(BudgetService budgetService) {
        this.budgetService = budgetService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        System.out.println("=== Personal Budget Tracker V1 ===");
        System.out.println("Welcome, " + budgetService.getUserName() + "!");

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addIncome();
                case 2 -> addExpense();
                case 3 -> showBalance();
                case 4 -> listAllTransactions();
                case 5 -> listTransactionsByCategory();
                case 0 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Add income");
        System.out.println("2. Add expense");
        System.out.println("3. Show balance");
        System.out.println("4. List all transactions");
        System.out.println("5. List transactions by category");
        System.out.println("0. Exit");
        System.out.println();
    }

    private void addIncome() {
        try {
            BigDecimal amount = readBigDecimal("Enter income amount: ");
            Category category = chooseCategoryForType(TransactionType.INCOME);
            String description = readText("Enter description: ");

            budgetService.addIncome(amount, category, description);
            System.out.println("Income added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addExpense() {
        try {
            BigDecimal amount = readBigDecimal("Enter expense amount: ");
            Category category = chooseCategoryForType(TransactionType.EXPENSE);
            String description = readText("Enter description: ");

            budgetService.addExpense(amount, category, description);
            System.out.println("Expense added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showBalance() {
        System.out.println("Total income : " + budgetService.getTotalIncome());
        System.out.println("Total expense: " + budgetService.getTotalExpense());
        System.out.println("Balance      : " + budgetService.getBalance());
    }

    private void listAllTransactions() {
        List<Transaction> transactions = budgetService.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("=== All Transactions ===");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void listTransactionsByCategory() {
        Category category = chooseCategoryForFilter();
        List<Transaction> transactions = budgetService.getTransactionsByCategory(category);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found for category: " + category.getDisplayName());
            return;
        }

        System.out.println("=== Transactions for " + category.getDisplayName() + " ===");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private Category chooseCategoryForType(TransactionType type) {
        List<Category> filteredCategories = new ArrayList<>();

        System.out.println("Select a category:");
        int index = 1;
        for (Category category : Category.values()) {
            if (category.isAvailableFor(type)) {
                filteredCategories.add(category);
                System.out.println(index + ". " + category.getDisplayName());
                index++;
            }
        }

        int choice;
        while (true) {
            choice = readInt("Choose category: ");
            if (choice >= 1 && choice <= filteredCategories.size()) {
                return filteredCategories.get(choice - 1);
            }
            System.out.println("Invalid category. Please try again.");
        }
    }

    private Category chooseCategoryForFilter() {
        Category[] categories = Category.values();

        System.out.println("Select a category:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i].getDisplayName());
        }

        int choice;
        while (true) {
            choice = readInt("Choose category: ");
            if (choice >= 1 && choice <= categories.length) {
                return categories[choice - 1];
            }
            System.out.println("Invalid category. Please try again.");
        }
    }

    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private BigDecimal readBigDecimal(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                BigDecimal amount = new BigDecimal(input);
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Amount must be greater than zero.");
                    continue;
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private String readText(String message) {
        System.out.print(message);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return "No description";
        }

        return input;
    }
}