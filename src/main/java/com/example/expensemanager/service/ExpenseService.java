package com.example.expensemanager.service;

import com.example.expensemanager.model.DashboardSummary;
import com.example.expensemanager.model.Expense;
import com.example.expensemanager.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    // Hardcoded vendor-to-category mapping (can be moved to DB later)
    private static final Map<String, String> vendorCategoryMap = new HashMap<>();

    static {
        vendorCategoryMap.put("Swiggy", "Food");
        vendorCategoryMap.put("Zomato", "Food");
        vendorCategoryMap.put("Uber", "Transport");
        vendorCategoryMap.put("Amazon", "Shopping");
        vendorCategoryMap.put("Flipkart", "Shopping");
        // Add more if needed
    }

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense) {
        try {
            // Assign category from vendor
            String vendor = expense.getVendorName();
            if (vendorCategoryMap.containsKey(vendor)) {
                expense.setCategory(vendorCategoryMap.get(vendor));
            } else {
                expense.setCategory("Other");
            }

            // Check for anomaly
            boolean isAnomaly = checkIfAnomaly(expense);
            expense.setAnomaly(isAnomaly);

            return expenseRepository.save(expense);
        } catch (Exception e) {
            System.out.println("ERROR while adding expense: " + e.getMessage());
            e.printStackTrace(); // shows full details in terminal
            throw e; // rethrow to trigger 500
        }
    }


    private boolean checkIfAnomaly(Expense newExpense) {
        List<Expense> categoryExpenses = expenseRepository.findByCategory(newExpense.getCategory());

        // Remove the new expense if it's already in the list (during testing)
        categoryExpenses = categoryExpenses.stream()
                .filter(e -> !e.getId().equals(newExpense.getId()))
                .toList();

        // Exclude null or zero amounts (just in case)
        categoryExpenses = categoryExpenses.stream()
                .filter(e -> e.getAmount() != null && e.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .toList();

        if (categoryExpenses.isEmpty()) return false;

        BigDecimal total = BigDecimal.ZERO;
        for (Expense e : categoryExpenses) {
            total = total.add(e.getAmount());
        }

        BigDecimal average = total.divide(BigDecimal.valueOf(categoryExpenses.size()), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal threshold = average.multiply(BigDecimal.valueOf(3));

        return newExpense.getAmount().compareTo(threshold) > 0;
    }


    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getAnomalies() {
        return expenseRepository.findAll().stream()
                .filter(Expense::isAnomaly)
                .toList();
    }

    public DashboardSummary getDashboardSummary() {
        List<Expense> allExpenses = expenseRepository.findAll();

        // 1. Monthly totals per category (using year-month + category)
        Map<String, BigDecimal> categoryMonthlyTotals = new HashMap<>();
        for (Expense e : allExpenses) {
            String key = e.getDate().getYear() + "-" + e.getDate().getMonthValue() + " : " + e.getCategory();
            categoryMonthlyTotals.put(key,
                    categoryMonthlyTotals.getOrDefault(key, BigDecimal.ZERO).add(e.getAmount()));
        }

        // 2. Top 5 vendors by total spend
        Map<String, BigDecimal> vendorTotals = new HashMap<>();
        for (Expense e : allExpenses) {
            vendorTotals.put(e.getVendorName(),
                    vendorTotals.getOrDefault(e.getVendorName(), BigDecimal.ZERO).add(e.getAmount()));
        }

        List<String> topVendors = vendorTotals.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();

        // 3. Anomalies
        List<Expense> anomalies = getAnomalies();
        long anomalyCount = anomalies.size();

        return new DashboardSummary(categoryMonthlyTotals, topVendors, anomalyCount, anomalies);
    }

    public void saveExpensesFromCSV(MultipartFile file) throws Exception {
        List<Expense> expenses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            for (CSVRecord record : csvParser) {
                Expense expense = new Expense();

                // Parse and set fields
                expense.setDate(LocalDate.parse(record.get("date")));
                expense.setAmount(new BigDecimal(record.get("amount")));
                expense.setVendorName(record.get("vendorName"));
                expense.setDescription(record.get("description"));

                // Set category using existing map
                String vendor = expense.getVendorName();
                expense.setCategory(vendorCategoryMap.getOrDefault(vendor, "Other"));

                // Check for anomaly
                expense.setAnomaly(checkIfAnomaly(expense));

                expenses.add(expense);
            }

            expenseRepository.saveAll(expenses);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV: " + e.getMessage());
        }
    }

}

