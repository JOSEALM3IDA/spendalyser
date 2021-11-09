package pt.josealm3ida.spendalyzer.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static pt.josealm3ida.spendalyzer.Constants.*;

public class DatabaseController {

    private static DatabaseController instance = null;
    private static Connection connection = null;

    public static DatabaseController getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader
            DatabaseController.connection = DriverManager.getConnection("jdbc:sqlite:" + EXPENSES_DB);
            instance = new DatabaseController();
            instance.createTable(true);
        }

        return instance;
    }

    public static void closeConnection() throws SQLException {
        instance = null;
        if (connection != null) connection.close();
    }

    private DatabaseController() {}

    public List<Expense> getAllExpenses() throws SQLException {
        ResultSet rs = getStatement().executeQuery("SELECT * FROM " + TABLE_NAME_EXPENSE + " ORDER BY timestamp");
        List<Expense> expenses = new ArrayList<>();
        while (rs.next()) expenses.add(Expense.getFromResultSet(rs));
        return expenses;
    }

    public void insertExpense(Expense expense) throws SQLException {
        getStatement().executeUpdate("INSERT INTO expense values(NULL, " +
                "'" + expense.type() + "', " + expense.value() + ", '" + expense.timestamp().toInstant().toEpochMilli() + "', " +
                "'" + expense.location() + "', '" + expense.description() + "')");
    }

    public void deleteTable(String tableName) throws SQLException {
        getStatement().executeUpdate("DROP TABLE IF EXISTS " + tableName);
    }

    public void createTable() throws SQLException {
        createTable(false);
    }

    public void createTable(boolean forceCreate) throws SQLException {
        if (forceCreate) deleteTable(TABLE_NAME_EXPENSE);
        getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_EXPENSE + " (_id INTEGER PRIMARY KEY," +
                "type STRING, value DOUBLE, timestamp TIMESTAMP, location STRING, description STRING)");
    }

    private Statement getStatement() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(QUERY_TIMEOUT_SECONDS);
        return statement;
    }
}
