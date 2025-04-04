package com.sivaganesh.finance;

import com.sivaganesh.finance.dao.FinanceRepositoryImpl;
import com.sivaganesh.finance.dao.IFinanceRepository;
import com.sivaganesh.finance.entity.User;
import com.sivaganesh.finance.entity.Expense;
import com.sivaganesh.finance.entity.ExpenseCategory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FinanceRepositoryTest {

    private static IFinanceRepository repository;

    @BeforeAll
    public static void setup() {
        repository = new FinanceRepositoryImpl();
    }

    @Test
    @Order(1)
    public void testAddUser() {
        User user = new User(100, "testuser", "testpass", "testuser@example.com");
        repository.addUser(user);
        System.out.println("✅ testAddUser passed");
    }

    @Test
    @Order(2)
    public void testGetUserById() {
        User user = repository.getUserById(100);
        Assertions.assertNotNull(user, "User should exist");
        System.out.println("✅ testGetUserById passed");
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        User updatedUser = new User(100, "updatedUser", "updatedPass", "updated@example.com");
        repository.updateUser(updatedUser);
        User user = repository.getUserById(100);
        Assertions.assertEquals("updatedUser", user.getUsername());
        System.out.println("✅ testUpdateUser passed");
    }

    @Test
    @Order(4)
    public void testAddExpenseCategory() {
        ExpenseCategory category = new ExpenseCategory(100, "TestCategory");
        repository.addExpenseCategory(category);
        System.out.println("✅ testAddExpenseCategory passed");
    }

    @Test
    @Order(5)
    public void testGetCategoryById() {
        ExpenseCategory category = repository.getCategoryById(100);
        Assertions.assertNotNull(category, "Category should exist");
        System.out.println("✅ testGetCategoryById passed");
    }

    @Test
    @Order(6)
    public void testUpdateExpenseCategory() {
        ExpenseCategory updatedCategory = new ExpenseCategory(100, "UpdatedCategory");
        repository.updateExpenseCategory(updatedCategory);
        ExpenseCategory category = repository.getCategoryById(100);
        Assertions.assertEquals("UpdatedCategory", category.getCategoryName());
        System.out.println("✅ testUpdateExpenseCategory passed");
    }

    @Test
    @Order(7)
    public void testAddExpense() {
        Expense expense = new Expense(200, 100, 999.99, 100, LocalDate.now(), "Test Expense Entry");
        repository.addExpense(expense);
        System.out.println("✅ testAddExpense passed");
    }

    @Test
    @Order(8)
    public void testGetExpensesByUserId() {
        List<Expense> expenses = repository.getExpensesByUserId(100);
        Assertions.assertFalse(expenses.isEmpty(), "Expenses should not be empty for user ID 100");
        System.out.println("✅ testGetExpensesByUserId passed");
    }

    @Test
    @Order(9)
    public void testUpdateExpense() {
        Expense updatedExpense = new Expense(200, 100, 1999.99, 100, LocalDate.now(), "Updated Expense Entry");
        repository.updateExpense(updatedExpense);
        List<Expense> expenses = repository.getExpensesByUserId(100);
        Assertions.assertTrue(expenses.stream().anyMatch(e -> e.getAmount() == 1999.99));
        System.out.println("✅ testUpdateExpense passed");
    }

    @Test
    @Order(10)
    public void testDeleteExpense() {
        repository.deleteExpense(200);
        List<Expense> expenses = repository.getExpensesByUserId(100);
        Assertions.assertTrue(expenses.stream().noneMatch(e -> e.getExpenseId() == 200));
        System.out.println("✅ testDeleteExpense passed");
    }

    @Test
    @Order(11)
    public void testDeleteExpenseCategory() {
        repository.deleteExpenseCategory(100);
        ExpenseCategory category = repository.getCategoryById(100);
        Assertions.assertNull(category, "Category should be deleted");
        System.out.println("✅ testDeleteExpenseCategory passed");
    }

    @Test
    @Order(12)
    public void testDeleteUser() {
        repository.deleteUser(100);
        User user = repository.getUserById(100);
        Assertions.assertNull(user, "User should be deleted");
        System.out.println("✅ testDeleteUser passed");
    }
}
