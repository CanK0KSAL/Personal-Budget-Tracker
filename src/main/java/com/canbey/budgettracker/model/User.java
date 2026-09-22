package com.canbey.budgettracker.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private final String name;
    private final List<Transaction> transactions;

    public User(String name) {
        this.name = name;
        this.transactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public BigDecimal calculateBalance() {
        BigDecimal incomeTotal = getTotalIncome();
        BigDecimal expenseTotal = getTotalExpense();
        return incomeTotal.subtract(expenseTotal);
    }

    public BigDecimal getTotalIncome() {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalExpense() {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transaction> getTransactionsByCategory(Category category) {
        return transactions.stream()
                .filter(transaction -> transaction.getCategory() == category)
                .collect(Collectors.toList());
    }
}