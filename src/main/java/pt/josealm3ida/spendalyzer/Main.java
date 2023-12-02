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

        List<Expense> allExpenses = expenseRepository.findAll();
        List<Expense> groceryExpenses = expenseRepository.findByType("Groceries");
        List<Expense> rentExpenses = expenseRepository.findByType("Rent");
        List<Expense> tuitionExpenses = expenseRepository.findByType("Tuition");
        List<Expense> techExpenses = expenseRepository.findByType("Tech");
        List<Expense> transportExpenses = expenseRepository.findByType("Transport");
        List<Expense> clothesExpenses = expenseRepository.findByType("Clothes");
        List<Expense> dinnerExpenses = expenseRepository.findByType("Dinner");
        List<Expense> lunchExpenses = expenseRepository.findByType("Lunch");
        List<Expense> snackExpenses = expenseRepository.findByType("Snack");

        expenseRepository.close();

        allExpenses.stream().forEach(e -> System.out.println(e));

        double totalValue = allExpenses.stream().mapToDouble(Expense::getValue).sum();

        System.out.println("\nTotal Money Spent (since " + allExpenses.get(0).getTimestampAsDate() + " until " + allExpenses.get(allExpenses.size() - 1).getTimestampAsDate() + "): " + Utils.round(totalValue, 2) + "€");

        System.out.println("Groceries: " + groceryExpenses.stream().mapToDouble(Expense::getValue).sum());
        System.out.println("Rent: " + rentExpenses.stream().mapToDouble(Expense::getValue).sum());
        System.out.println("Tuition: " + tuitionExpenses.stream().mapToDouble(Expense::getValue).sum());
        System.out.println("Tech: " + techExpenses.stream().mapToDouble(Expense::getValue).sum());
        System.out.println("Transport: " + transportExpenses.stream().mapToDouble(Expense::getValue).sum());
        System.out.println("Clothes: " + clothesExpenses.stream().mapToDouble(Expense::getValue).sum());
        System.out.println("Dinner: " + dinnerExpenses.stream().mapToDouble(Expense::getValue).sum());
        System.out.println("Lunch: " + lunchExpenses.stream().mapToDouble(Expense::getValue).sum());
        System.out.println("Snack: " + snackExpenses.stream().mapToDouble(Expense::getValue).sum());


    }
}
