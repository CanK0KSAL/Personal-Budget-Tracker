package com.canbey.budgettracker.service;

import com.canbey.budgettracker.model.Category;
import com.canbey.budgettracker.model.Transaction;
import com.canbey.budgettracker.model.TransactionType;
import com.canbey.budgettracker.model.User;

import java.math.BigDecimal;
import java.util.List;

public class BudgetService {
    private final User user;

    public BudgetService(User user) {
        this.user = user;
    }

    public void addIncome(BigDecimal amount, Category category, String description) {
        validateAmount(amount);
        Transaction transaction = new Transaction(TransactionType.INCOME, category, amount, description);
        user.addTransaction(transaction);
    }

    public void addExpense(BigDecimal amount, Category category, String description) {
        validateAmount(amount);
        Transaction transaction = new Transaction(TransactionType.EXPENSE, category, amount, description);
        user.addTransaction(transaction);
    }

    public BigDecimal getBalance() {
        return user.calculateBalance();
    }

    public BigDecimal getTotalIncome() {
        return user.getTotalIncome();
    }

    public BigDecimal getTotalExpense() {
        return user.getTotalExpense();
    }

    public List<Transaction> getAllTransactions() {
        return user.getTransactions();
    }

    public List<Transaction> getTransactionsByCategory(Category category) {
        return user.getTransactionsByCategory(category);
    }

    public String getUserName() {
        return user.getName();
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }
}