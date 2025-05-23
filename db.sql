CREATE DATABASE FinanceDB;
USE FinanceDB;

-- Users Table
CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- ExpenseCategories Table
CREATE TABLE ExpenseCategories (
    category_id INT PRIMARY KEY ,
    category_name VARCHAR(50) NOT NULL UNIQUE
);

-- Expenses Table
CREATE TABLE Expenses (
    expense_id INT PRIMARY KEY ,
    user_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    category_id INT,
    date DATE NOT NULL,
    description TEXT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES ExpenseCategories(category_id) ON DELETE SET NULL
);

-- testing 
select * from users;
SELECT * FROM expensecategories;
SELECT * FROM expenses;

INSERT INTO ExpenseCategories (category_id, category_name) VALUES (1, 'Food');
INSERT INTO ExpenseCategories (category_id, category_name) VALUES 
(2, 'Transport'),
(3, 'Shopping'),
(4, 'Medical'),
(5, 'Rent');
 SET SQL_SAFE_UPDATES = 0;

DELETE FROM users;
DELETE FROM expenses;
drop database financedb;