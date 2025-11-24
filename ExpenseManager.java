import java.util.*;
import java.io.*;

public class ExpenseManager {
    private List<Expense> Expenses = new ArrayList<>();
    private final String fileName;

    public void addExpense(Expense e) {
        Expenses.add(e);
        saveExpenses();
    }

    public ExpenseManager(String fileName) {
        this.fileName = fileName;
        loadExpenses();
    }

    // NEW: Delete Expense by index
    public boolean deleteExpense(int index) {
        if (index >= 0 && index < Expenses.size()) {
            Expenses.remove(index);
            saveExpenses();
            return true;
        } else {
            return false;
        }
    }

    public void listExpensesWithIndex() {
        // Show numbered list for delete option
        for (int i = 0; i < Expenses.size(); i++) {
            Expense e = Expenses.get(i);
            System.out.println((i + 1) + ". " + e.getDate() + " | " + e.getAmount() + " | " + e.getCategory() + " | "
                    + e.getDescription());
        }
    }

    public void listExpenses() {
        for (Expense e : Expenses) {
            System.out.println(
                    e.getDate() + " | " + e.getAmount() + " | " + e.getCategory() + " | " + e.getDescription());
        }
    }

    public void summaryByMonth() {
        Map<String, Double> summary = new HashMap<>();
        for (Expense e : Expenses) {
            String key = e.getDate().getYear() + "-" + String.format("%02d", e.getDate().getMonthValue());
            summary.put(key, summary.getOrDefault(key, 0.0) + e.getAmount());
        }
        summary.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    public void summaryByCategory() {
        Map<String, Double> summary = new HashMap<>();
        for (Expense e : Expenses) {
            summary.put(e.getCategory(), summary.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
        }
        summary.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    private void loadExpenses() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Expenses.add(Expense.fromString(line));
            }
        } catch (IOException ignored) {
        }
    }

    private void saveExpenses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Expense e : Expenses) {
                bw.write(e.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving Expenses: " + e.getMessage());
        }
    }
}