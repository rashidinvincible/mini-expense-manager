package com.example.expensemanager.model;



import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity

public class Expense {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private BigDecimal amount;
    private String vendorName;
    private String description;
    private String category;
    private boolean isAnomaly;


    public Expense() {
    }

    public Expense(Long id, LocalDate date, BigDecimal amount, String vendorName, String description, String category, boolean isAnomaly) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.vendorName = vendorName;
        this.description = description;
        this.category = category;
        this.isAnomaly = isAnomaly;
    }



    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAnomaly() {
        return isAnomaly;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAnomaly(boolean anomaly) {
        isAnomaly = anomaly;
    }
}
