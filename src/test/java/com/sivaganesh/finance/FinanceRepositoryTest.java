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
    private static final int testCategoryId = 1; // Make sure this exists in your DB
    private static int testExpenseId;

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
        System.out.println("✅ testCreateUser passed");
    }

    @Test
    @Order(2)
    public void testCreateExpense() {
        Expense expense = new Expense(0, testUserId, 999.99, testCategoryId,
                Date.valueOf(LocalDate.now()), "Test Expense");
        boolean result = repository.createExpense(expense);
        Assertions.assertTrue(result, "Expense creation failed");
        System.out.println("✅ testCreateExpense passed");
    }

    @Test
    @Order(3)
    public void testGetAllExpenses() throws UserNotFoundException {
        List<Expense> expenses = repository.getAllExpenses(testUserId);
        Assertions.assertFalse(expenses.isEmpty(), "No expenses found for test user");
        testExpenseId = expenses.get(0).getExpenseId(); // Save for update/delete tests
        System.out.println("✅ testGetAllExpenses passed");
    }

    @Test
    @Order(4)
    public void testUpdateExpense() throws ExpenseNotFoundException {
        Expense updatedExpense = new Expense(testExpenseId, testUserId, 1499.99, testCategoryId,
                Date.valueOf(LocalDate.now()), "Updated Expense Description");
        boolean result = repository.updateExpense(testUserId, updatedExpense);
        Assertions.assertTrue(result, "Expense update failed");
        System.out.println("✅ testUpdateExpense passed");
    }

    @Test
    @Order(5)
    public void testGenerateReport() throws UserNotFoundException {
        Date from = Date.valueOf(LocalDate.now().minusDays(1));
        Date to = Date.valueOf(LocalDate.now().plusDays(1));
        List<Expense> report = repository.getExpensesByDateRange(testUserId, from, to);
        Assertions.assertFalse(report.isEmpty(), "No report data found");
        System.out.println("✅ testGenerateReport passed");
    }

    @Test
    @Order(6)
    public void testDeleteExpense() throws ExpenseNotFoundException {
        boolean result = repository.deleteExpense(testExpenseId);
        Assertions.assertTrue(result, "Expense deletion failed");
        System.out.println("✅ testDeleteExpense passed");
    }

    @Test
    @Order(7)
    public void testDeleteUser() throws UserNotFoundException {
        boolean result = repository.deleteUser(testUserId);
        Assertions.assertTrue(result, "User deletion failed");
        System.out.println("✅ testDeleteUser passed");
    }
}
