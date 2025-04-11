package com.sivaganesh.finance.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sivaganesh.finance.entity.Expense;
import com.sivaganesh.finance.entity.User;
import com.sivaganesh.finance.exception.ExpenseNotFoundException;
import com.sivaganesh.finance.exception.UserNotFoundException;
import com.sivaganesh.finance.util.DBConnection;

public class FinanceRepositoryImpl implements IFinanceRepository {

    private final Connection conn;

    public FinanceRepositoryImpl() {
        conn = DBConnection.getConnection();
    }

    @Override
    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (user_id, username, password, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while creating user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createExpense(Expense expense) {
        String sql = "INSERT INTO Expenses (expense_id, user_id, amount, category_id, date, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, expense.getExpenseId());
            stmt.setInt(2, expense.getUserId());
            stmt.setDouble(3, expense.getAmount());
            stmt.setInt(4, expense.getCategoryId());
            stmt.setDate(5, expense.getDate());
            stmt.setString(6, expense.getDescription());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while creating expense: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId) throws UserNotFoundException {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new UserNotFoundException("User not found with ID: " + userId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error while deleting user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteExpense(int expenseId) throws ExpenseNotFoundException {
        String sql = "DELETE FROM Expenses WHERE expense_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, expenseId);
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new ExpenseNotFoundException("Expense not found with ID: " + expenseId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error while deleting expense: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Expense> getAllExpenses(int userId) throws UserNotFoundException {
        String sql = "SELECT * FROM Expenses WHERE user_id = ?";
        List<Expense> expenses = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                Expense exp = new Expense(
                        rs.getInt("expense_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getInt("category_id"),
                        rs.getDate("date"),
                        rs.getString("description")
                );
                expenses.add(exp);
            }
            if (!found) throw new UserNotFoundException("No expenses found for user ID: " + userId);
        } catch (SQLException e) {
            System.out.println("Error while fetching expenses: " + e.getMessage());
        }
        return expenses;
    }

    @Override
    public boolean updateExpense(int userId, Expense expense) throws ExpenseNotFoundException {
        String sql = "UPDATE Expenses SET amount = ?, category_id = ?, date = ?, description = ? WHERE expense_id = ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, expense.getAmount());
            stmt.setInt(2, expense.getCategoryId());
            stmt.setDate(3, expense.getDate());
            stmt.setString(4, expense.getDescription());
            stmt.setInt(5, expense.getExpenseId());
            stmt.setInt(6, userId);
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new ExpenseNotFoundException("No expense found for update.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error while updating expense: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Expense> getExpensesByDateRange(int userId, Date from, Date to) throws UserNotFoundException {
        String sql = "SELECT * FROM Expenses WHERE user_id = ? AND date BETWEEN ? AND ?";
        List<Expense> expenses = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, from);
            stmt.setDate(3, to);
            ResultSet rs = stmt.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                Expense exp = new Expense(
                        rs.getInt("expense_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getInt("category_id"),
                        rs.getDate("date"),
                        rs.getString("description")
                );
                expenses.add(exp);
            }
            if (!found) throw new UserNotFoundException("No expenses found for user ID " + userId + " in the given date range.");
        } catch (SQLException e) {
            System.out.println("Error while generating report: " + e.getMessage());
        }
        return expenses;
    }
}
