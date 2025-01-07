package com.example.blockproject;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private String productName;
    private String transactionType;
    private int quantity;
    private LocalDateTime transactionDate;
    private String notes;

    public Transaction(int id, String productName, String transactionType,
                       int quantity, LocalDateTime transactionDate, String notes) {
        this.id = id;
        this.productName = productName;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.notes = notes;
    }

    // Getters
    public int getId() { return id; }
    public String getProductName() { return productName; }
    public String getTransactionType() { return transactionType; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public String getNotes() { return notes; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public void setNotes(String notes) { this.notes = notes; }
}