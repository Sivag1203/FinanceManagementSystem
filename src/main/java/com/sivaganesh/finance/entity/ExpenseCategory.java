package com.sivaganesh.finance.entity;

/**
 * Represents a category for expenses (e.g., Food, Travel, Utilities).
 */
public class ExpenseCategory {
    private int categoryId;
    private String categoryName;

    /** Default constructor */
    public ExpenseCategory() {}

    /** Constructor with ID and name */
    public ExpenseCategory(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        setCategoryName(categoryName);
    }

    /** Constructor without ID (for auto-generation use cases) */
    public ExpenseCategory(String categoryName) {
        setCategoryName(categoryName);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        if (categoryId <= 0) throw new IllegalArgumentException("Category ID must be positive.");
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }
        this.categoryName = categoryName.trim();
    }

    @Override
    public String toString() {
        return "ExpenseCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
