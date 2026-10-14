package com.canbey.budgettracker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private static int nextId = 1;

    private final int id;
    private final TransactionType type;
    private final Category category;
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime dateTime;


    public Transaction(TransactionType type, Category category, BigDecimal amount, String description){
        this.id = nextId++;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.dateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", category=" + category.getDisplayName() +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime.format(formatter) +
                '}';
    }

}
