package com.canbey.budgettracker;

import com.canbey.budgettracker.model.User;
import com.canbey.budgettracker.service.BudgetService;
import com.canbey.budgettracker.ui.ConsoleMenu;

public class main {
    public static void main(String[] args) {
        User user = new User("Default User");
        BudgetService budgetService = new BudgetService(user);
        ConsoleMenu consoleMenu = new ConsoleMenu(budgetService);

        consoleMenu.start();
    }
}