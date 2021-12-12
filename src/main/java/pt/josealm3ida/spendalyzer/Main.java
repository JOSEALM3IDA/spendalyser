package pt.josealm3ida.spendalyzer;

import pt.josealm3ida.spendalyzer.database.ExpenseRepository;
import pt.josealm3ida.spendalyzer.database.Expense;
import pt.josealm3ida.spendalyzer.json.JsonParser;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ExpenseRepository expenseRepository = new ExpenseRepository();

        try {
            expenseRepository.add(JsonParser.parseExpenses(Constants.JSON_LOC));
        } catch (IOException ioe) {
            expenseRepository.close();
            ioe.printStackTrace();
            return;
        }

        List<Expense> expenses = expenseRepository.findAll();
        expenseRepository.close();

        double totalValue = 0;
        String firstExpenseDate = null;
        for (Expense expense : expenses) {
            if (firstExpenseDate == null) firstExpenseDate = expense.getTimestampAsDate();
            totalValue += expense.getValue();
            System.out.println(expense);
        }

        if (firstExpenseDate != null)
            System.out.println("\nTotal Money Spent (since " + firstExpenseDate + "): " + Utils.round(totalValue, 2) + "€");
    }
}
