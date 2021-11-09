package pt.josealm3ida.spendalyser;

import pt.josealm3ida.spendalyser.database.DatabaseController;
import pt.josealm3ida.spendalyser.database.Expense;

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

            Instant instant = LocalDateTime.parse("09/11/2021 03:33", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).atZone(ZoneId.systemDefault()).toInstant();
            dbController.insertExpense(new Expense("Type1", 69.69, Timestamp.from(instant), "Berliner Tor 3", "description 1"));

            instant = LocalDateTime.parse("10/11/2021 13:22", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).atZone(ZoneId.systemDefault()).toInstant();
            dbController.insertExpense(new Expense("Type2", 69.69, Timestamp.from(instant), "Quinta da Nora", "description 2"));

            List<Expense> expenses = dbController.getAllExpenses();
            for (Expense expense : expenses) System.out.println(expense.toString());
        } catch(SQLException e) {
            // if the error message is "out of memory", it probably means no database file is found
            System.err.println(e.getMessage());
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
