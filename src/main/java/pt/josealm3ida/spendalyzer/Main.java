package pt.josealm3ida.spendalyzer;

import pt.josealm3ida.spendalyzer.database.DatabaseController;
import pt.josealm3ida.spendalyzer.database.Expense;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        DatabaseController dbController = null;
        try {
            dbController = DatabaseController.getInstance();
            dbController.insertExpenseFile(Constants.EXAMPLE_JSON_LOC);
            List<Expense> expenses = dbController.getAllExpenses();

            double totalValue = 0;
            String firstExpenseDate = null;
            for (Expense expense : expenses) {
                if (firstExpenseDate == null) firstExpenseDate = expense.getTimestampAsDate();
                totalValue += expense.value();
                System.out.println(expense);
            }

            if (firstExpenseDate != null)
                System.out.println("\nTotal Money Spent (since " + firstExpenseDate + "): " + Utils.round(totalValue, 2) + "€");
        } catch(SQLException | IOException e) {
            // if the error message is "out of memory", it probably means no database file is found
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                DatabaseController.closeConnection();
            } catch(SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}
