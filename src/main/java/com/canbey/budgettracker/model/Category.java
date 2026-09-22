package com.canbey.budgettracker.model;

public enum Category {
    SALARY("Salary", true, false),
    FREELANCE("Freelance", true, false),
    INVESTMENT("Investment", true, false),
    GIFT("Gift", true, false),

    FOOD("Food", false, true),
    TRANSPORT("Transport", false, true),
    RENT("Rent", false, true),
    BILLS("Bills", false, true),
    ENTERTAINMENT("Entertainment", false, true),
    HEALTH("Health", false, true),
    EDUCATION("Education", false, true),
    SHOPPING("Shopping", false, true),

    OTHER("Other", true, true);

    private final String displayName;
    private final boolean incomeCategory;
    private final boolean expenseCategory;

    Category(String displayName, boolean incomeCategory, boolean expenseCategory) {
        this.displayName = displayName;
        this.incomeCategory = incomeCategory;
        this.expenseCategory = expenseCategory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isAvailableFor(TransactionType type) {
        if (type == TransactionType.INCOME) {
            return incomeCategory;
        }
        return expenseCategory;
    }
}