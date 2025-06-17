package com.example.expensemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummary {

    private Map<String, BigDecimal> monthlyCategoryTotals;
    private List<String> topVendors;
    private long anomalyCount;
    private List<Expense> anomalies;

    // Constructors, Getters and Setters


}

