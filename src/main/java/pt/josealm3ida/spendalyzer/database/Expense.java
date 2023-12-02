package pt.josealm3ida.spendalyzer.database;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "expense")
@NamedQuery(name = Expense.QUERY_FIND_ALL, query = "SELECT s FROM Expense s ORDER BY " + Expense.PARAM_TIMESTAMP)
@NamedQuery(name = Expense.QUERY_FIND_BY_ID, query = "SELECT s FROM Expense s WHERE s.id = :id")
@NamedQuery(name = Expense.QUERY_FIND_BY_TYPE, query = "SELECT s FROM Expense s WHERE s.type = :type")
public class Expense {

    public static final String QUERY_FIND_ALL = "Expense.findAll";
    public static final String QUERY_FIND_BY_ID = "Expense.findById";
    public static final String QUERY_FIND_BY_TYPE = "Expense.findByType";

    public static final String PARAM_ID = "id";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_VALUE = "value";
    public static final String PARAM_TIMESTAMP = "timestamp";
    public static final String PARAM_LOCATION = "location";
    public static final String PARAM_DESCRIPTION = "description";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    protected Expense() {}

    public Expense(String type, double value, Timestamp timestamp, String location, String description) {
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
        this.location = location;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestampAsDate() {
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "Type: " + type + "\tValue: " + value + "\tTimestamp: " + timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                "\tLocation: " + location + "\tDescription: " + description;
    }
}
