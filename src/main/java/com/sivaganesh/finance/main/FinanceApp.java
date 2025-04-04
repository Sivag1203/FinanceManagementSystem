package com.sivaganesh.finance.main;

import com.sivaganesh.finance.dao.FinanceRepositoryImpl;
import com.sivaganesh.finance.dao.IFinanceRepository;
import com.sivaganesh.finance.entity.User;
import com.sivaganesh.finance.entity.Expense;
import com.sivaganesh.finance.entity.ExpenseCategory;

import java.time.LocalDate;
import java.util.List;

public class FinanceApp {
    public static void main(String[] args) {

        IFinanceRepository repo = new FinanceRepositoryImpl();

        // Add a User
        User user1 = new User(1, "sivaganesh", "password123", "siva@example.com");
        repo.addUser(user1);
        System.out.println("✅ User added successfully!");

        // Update User
        user1.setUsername("siva_updated");
        user1.setPassword("newpass123");
        user1.setEmail("siva_updated@example.com");
        repo.updateUser(user1);
        System.out.println("✅ User updated successfully!");

        // Add Expense Categories
        ExpenseCategory foodCategory = new ExpenseCategory(1, "Food");
        ExpenseCategory travelCategory = new ExpenseCategory(2, "Travel");
        repo.addExpenseCategory(foodCategory);
        repo.addExpenseCategory(travelCategory);
        System.out.println("✅ Categories added successfully!");

        // Update Category
        foodCategory.setCategoryName("Updated Food");
        repo.updateExpenseCategory(foodCategory);
        System.out.println("✅ Food category updated!");

        // Add Expenses
        Expense expense1 = new Expense(1, 1, 500.0, 1, LocalDate.of(2025, 4, 1), "Lunch at a restaurant");
        Expense expense2 = new Expense(2, 1, 150.0, 2, LocalDate.of(2025, 4, 2), "Bus fare");
        repo.addExpense(expense1);
        repo.addExpense(expense2);
        System.out.println("✅ Expenses added successfully!");

        // Update Expense
        expense1.setAmount(600.0);
        expense1.setDescription("Updated lunch expense");
        repo.updateExpense(expense1);
        System.out.println("✅ Expense updated successfully!");

        // Fetch and display user expenses
        List<Expense> userExpenses = repo.getExpensesByUserId(1);
        System.out.println("\n📋 Expenses for user ID 1:");
        for (Expense e : userExpenses) {
            System.out.println("ID: " + e.getExpenseId() +
                    ", Amount: ₹" + e.getAmount() +
                    ", Category ID: " + e.getCategoryId() +
                    ", Date: " + e.getDate() +
                    ", Description: " + e.getDescription());
        }

        // Delete an expense
        repo.deleteExpense(2);
        System.out.println("🗑️ Expense ID 2 deleted!");

        // Delete category
        repo.deleteExpenseCategory(2);
        System.out.println("🗑️ Travel category deleted!");

        // Delete user
        repo.deleteUser(1);
        System.out.println("🗑️ User deleted!");
    }
}
