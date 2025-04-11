package com.sivaganesh.finance.dao;

import java.sql.Date;
import java.util.List;

import com.sivaganesh.finance.entity.User;
import com.sivaganesh.finance.entity.Expense;
import com.sivaganesh.finance.exception.UserNotFoundException;
import com.sivaganesh.finance.exception.ExpenseNotFoundException;

public interface IFinanceRepository {
    boolean createUser(User user);
    boolean createExpense(Expense expense);
    boolean deleteUser(int userId) throws UserNotFoundException;
    boolean deleteExpense(int expenseId) throws ExpenseNotFoundException;
    List<Expense> getAllExpenses(int userId) throws UserNotFoundException;
    boolean updateExpense(int userId, Expense expense) throws ExpenseNotFoundException;
    List<Expense> getExpensesByDateRange(int userId, Date from, Date to) throws UserNotFoundException;
}
