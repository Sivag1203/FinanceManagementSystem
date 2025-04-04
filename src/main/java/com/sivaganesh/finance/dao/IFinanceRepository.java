package com.sivaganesh.finance.dao;

import com.sivaganesh.finance.entity.User;
import com.sivaganesh.finance.entity.Expense;
import com.sivaganesh.finance.entity.ExpenseCategory;

import java.util.List;

public interface IFinanceRepository {
    // User operations
    void addUser(User user);
    User getUserById(int userId);

    // Expense operations
    void addExpense(Expense expense);
    List<Expense> getExpensesByUserId(int userId);
    void updateExpense(Expense expense);
    void deleteExpense(int expenseId);
    void updateUser(User user);
    void updateExpenseCategory(ExpenseCategory category);
    void deleteExpenseCategory(int categoryId);
    void deleteUser(int userId);

    // Category operations
    void addExpenseCategory(ExpenseCategory category);
    ExpenseCategory getCategoryById(int categoryId);
}
