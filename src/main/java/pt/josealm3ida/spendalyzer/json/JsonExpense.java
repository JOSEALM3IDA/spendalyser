package pt.josealm3ida.spendalyzer.json;

import pt.josealm3ida.spendalyzer.database.Expense;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class JsonExpense {
    private String type;
    private double value;
    private String date;
    private String time;
    private String location;
    private String description;

    public JsonExpense(String type, double value, String date, String time, String location, String description) {
        this.type = type;
        this.value = value;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    public Expense toExpense() {
        String dateTime = time == null ? date + " 00:00" : date + " " + time;
        Instant instant = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).atZone(ZoneId.systemDefault()).toInstant();
        return new Expense(type, value, Timestamp.from(instant), location, description);
    }
}
