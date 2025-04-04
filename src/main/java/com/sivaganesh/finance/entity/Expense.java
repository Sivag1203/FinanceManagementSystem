package com.sivaganesh.finance.entity;

import java.time.LocalDate;

/**
 * Represents an expense entry made by a user.
 */
public class Expense {
    private int expenseId;
    private int userId;
    private double amount;
    private int categoryId;
    private LocalDate date;
    private String description;

    /** Default constructor */
    public Expense() {}

    /** Constructor with all fields */
    public Expense(int expenseId, int userId, double amount, int categoryId, LocalDate date, String description) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.date = date;
        this.description = description;
    }

    /** Overloaded constructor without expenseId (e.g. for auto-generation in DB) */
    public Expense(int userId, double amount, int categoryId, LocalDate date, String description) {
        this.userId = userId;
        this.amount = amount;
        this.categoryId = categoryId;
        this.date = date;
        this.description = description;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) throw new IllegalArgumentException("User ID must be positive.");
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        if (categoryId <= 0) throw new IllegalArgumentException("Category ID must be positive.");
        this.categoryId = categoryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null.");
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty())
            throw new IllegalArgumentException("Description cannot be empty.");
        this.description = description.trim();
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", categoryId=" + categoryId +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
