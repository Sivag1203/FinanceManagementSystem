package com.sivaganesh.finance;

import com.sivaganesh.finance.dao.FinanceRepositoryImpl;
import com.sivaganesh.finance.dao.IFinanceRepository;
import com.sivaganesh.finance.entity.Expense;
import com.sivaganesh.finance.entity.User;
import com.sivaganesh.finance.exception.ExpenseNotFoundException;
import com.sivaganesh.finance.exception.UserNotFoundException;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FinanceRepositoryTest {

    private static IFinanceRepository repository;
    private static final int testUserId = 5001;
    private static final int testCategoryId = 1; // Ensure this category exists
    private static final int testExpenseId = 7001;

    @BeforeAll
    public static void setup() {
        repository = new FinanceRepositoryImpl();
    }

    @Test
    @Order(1)
    public void testCreateUser() {
        User user = new User(testUserId, "testuser", "testpass", "testuser5001@example.com");
        boolean result = repository.createUser(user);
        Assertions.assertTrue(result, "User creation failed");
        System.out.println("testCreateUser passed");
    }

    @Test
    @Order(2)
    public void testCreateExpense() {
        Expense expense = new Expense(testExpenseId, testUserId, 999.99, testCategoryId,
                Date.valueOf(LocalDate.now()), "Test Expense");
        boolean result = repository.createExpense(expense);
        Assertions.assertTrue(result, "Expense creation failed");
        System.out.println("testCreateExpense passed");
    }

    @Test
    @Order(3)
    public void testSearchExpenseByUserId() throws UserNotFoundException {
        List<Expense> expenses = repository.getAllExpenses(testUserId);
        Assertions.assertFalse(expenses.isEmpty(), "No expenses found for user");
        Assertions.assertTrue(expenses.stream().anyMatch(e -> e.getExpenseId() == testExpenseId));
        System.out.println("testSearchExpenseByUserId passed");
    }

    @Test
    @Order(4)
    public void testExceptionCases() {
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            repository.getAllExpenses(9999);
        });
        Assertions.assertThrows(ExpenseNotFoundException.class, () -> {
            repository.deleteExpense(9999);
        });
        System.out.println("testExceptionCases passed");
    }
}

