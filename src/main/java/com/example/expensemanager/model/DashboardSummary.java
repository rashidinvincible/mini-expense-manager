package com.example.expensemanager.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardSummary {

    private Map<String, BigDecimal> monthlyCategoryTotals;
    private List<String> topVendors;
    private long anomalyCount;
    private List<Expense> anomalies;

    // Constructors, Getters and Setters

    public DashboardSummary() {}

    public DashboardSummary(Map<String, BigDecimal> monthlyCategoryTotals,
                            List<String> topVendors,
                            long anomalyCount,
                            List<Expense> anomalies) {
        this.monthlyCategoryTotals = monthlyCategoryTotals;
        this.topVendors = topVendors;
        this.anomalyCount = anomalyCount;
        this.anomalies = anomalies;
    }

    public Map<String, BigDecimal> getMonthlyCategoryTotals() {
        return monthlyCategoryTotals;
    }

    public void setMonthlyCategoryTotals(Map<String, BigDecimal> monthlyCategoryTotals) {
        this.monthlyCategoryTotals = monthlyCategoryTotals;
    }

    public List<String> getTopVendors() {
        return topVendors;
    }

    public void setTopVendors(List<String> topVendors) {
        this.topVendors = topVendors;
    }

    public long getAnomalyCount() {
        return anomalyCount;
    }

    public void setAnomalyCount(long anomalyCount) {
        this.anomalyCount = anomalyCount;
    }

    public List<Expense> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Expense> anomalies) {
        this.anomalies = anomalies;
    }
}

