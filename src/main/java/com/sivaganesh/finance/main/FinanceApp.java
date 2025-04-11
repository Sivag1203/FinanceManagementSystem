package com.sivaganesh.finance.main;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.sivaganesh.finance.dao.FinanceRepositoryImpl;
import com.sivaganesh.finance.dao.IFinanceRepository;
import com.sivaganesh.finance.entity.Expense;
import com.sivaganesh.finance.entity.User;
import com.sivaganesh.finance.exception.ExpenseNotFoundException;
import com.sivaganesh.finance.exception.UserNotFoundException;

public class FinanceApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final IFinanceRepository repo = new FinanceRepositoryImpl();

    public static void main(String[] args) {
        System.out.println("====== Welcome to Finance Management System ======");

        boolean exit = false;

        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add User");
            System.out.println("2. Add Expense");
            System.out.println("3. Delete User");
            System.out.println("4. Delete Expense");
            System.out.println("5. Update Expense");
            System.out.println("6. View All Expenses by User ID");
            System.out.println("7. Generate Report by Date Range");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    addExpense();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    deleteExpense();
                    break;
                case 5:
                    updateExpense();
                    break;
                case 6:
                    viewExpenses();
                    break;
                case 7:
                    generateReport();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

        scanner.close();
    }

    private static void addUser() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        User user = new User(userId, username, password, email);
        if (repo.createUser(user)) {
            System.out.println("User added successfully.");
        } else {
            System.out.println("Failed to add user.");
        }
    }

    private static void addExpense() {
        System.out.print("Enter Expense ID: ");
        int expenseId = scanner.nextInt();

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();

        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();

        System.out.print("Enter Category ID: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Date (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        Expense expense = new Expense(expenseId, userId, amount, categoryId, Date.valueOf(dateStr), description);
        if (repo.createExpense(expense)) {
            System.out.println("Expense added successfully.");
        } else {
            System.out.println("Failed to add expense.");
        }
    }

    private static void deleteUser() {
        System.out.print("Enter User ID to delete: ");
        int userId = scanner.nextInt();
        try {
            if (repo.deleteUser(userId)) {
                System.out.println("User deleted successfully.");
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteExpense() {
        System.out.print("Enter Expense ID to delete: ");
        int expenseId = scanner.nextInt();
        try {
            if (repo.deleteExpense(expenseId)) {
                System.out.println("Expense deleted successfully.");
            }
        } catch (ExpenseNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateExpense() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();

        System.out.print("Enter Expense ID to update: ");
        int expenseId = scanner.nextInt();

        System.out.print("Enter New Amount: ");
        double amount = scanner.nextDouble();

        System.out.print("Enter New Category ID: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter New Date (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();

        System.out.print("Enter New Description: ");
        String description = scanner.nextLine();

        Expense expense = new Expense(expenseId, userId, amount, categoryId, Date.valueOf(dateStr), description);
        try {
            if (repo.updateExpense(userId, expense)) {
                System.out.println("Expense updated successfully.");
            }
        } catch (ExpenseNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewExpenses() {
        System.out.print("Enter User ID to view expenses: ");
        int userId = scanner.nextInt();
        try {
            List<Expense> expenses = repo.getAllExpenses(userId);
            System.out.println("Expenses for User ID " + userId + ":");
            for (Expense e : expenses) {
                System.out.println("ID: " + e.getExpenseId() + ", Amount: " + e.getAmount() +
                        ", Category ID: " + e.getCategoryId() + ", Date: " + e.getDate() +
                        ", Description: " + e.getDescription());
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateReport() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Start Date (yyyy-mm-dd): ");
        String startDateStr = scanner.nextLine();

        System.out.print("Enter End Date (yyyy-mm-dd): ");
        String endDateStr = scanner.nextLine();

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);

        try {
            List<Expense> report = repo.getExpensesByDateRange(userId, startDate, endDate);
            System.out.println("\n--- Expense Report ---");
            for (Expense e : report) {
                System.out.println("ID: " + e.getExpenseId() + ", Amount: " + e.getAmount() +
                        ", Category ID: " + e.getCategoryId() + ", Date: " + e.getDate() +
                        ", Description: " + e.getDescription());
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
