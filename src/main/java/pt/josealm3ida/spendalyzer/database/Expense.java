package pt.josealm3ida.spendalyzer.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public record Expense(String type, double value, Timestamp timestamp, String location, String description) {

    public static Expense getFromResultSet(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        double value = rs.getDouble("value");
        Timestamp timestamp = rs.getTimestamp("timestamp");
        String location = rs.getString("location");
        String description = rs.getString("description");

        return new Expense(type, value, timestamp, location, description);
    }

    @Override
    public String toString() {
        return "Type: " + type() + "\tValue: " + value + "\tTimestamp: " + timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                "\tLocation: " + location + "\tDescription: " + description;
    }
}
