package pt.josealm3ida.spendalyzer.json;

import com.google.gson.Gson;
import pt.josealm3ida.spendalyzer.database.Expense;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class JsonParser {

    public static List<Expense> parseExpenses(String jsonLoc) throws IOException {
        String fileContents = new String(Files.readAllBytes(Paths.get(jsonLoc)));
        JsonExpense[] jsonExpenses = new Gson().fromJson(fileContents, JsonExpense[].class);
        return Arrays.stream(jsonExpenses).map(JsonExpense::toExpense).toList();
    }

    private JsonParser() {}
}
