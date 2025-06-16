package com.example.expensemanager.controller;

import com.example.expensemanager.model.DashboardSummary;
import com.example.expensemanager.model.Expense;
import com.example.expensemanager.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // 🔹 Add Expense
    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense saved = expenseService.addExpense(expense);
        return ResponseEntity.ok(saved);
    }

    // 🔹 Get All Expenses
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    // 🔹 Get Only Anomalies
    @GetMapping("/anomalies")
    public ResponseEntity<List<Expense>> getAnomalies() {
        return ResponseEntity.ok(expenseService.getAnomalies());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardSummary> getDashboardSummary() {
        DashboardSummary summary = expenseService.getDashboardSummary();
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            expenseService.saveExpensesFromCSV(file);
            return ResponseEntity.ok("CSV upload successful.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CSV upload failed: " + e.getMessage());
        }
    }


}
