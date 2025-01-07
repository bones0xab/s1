package com.example.blockproject;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private BigDecimal price;

    public Product(int id, String name, String description, int quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }


        // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(BigDecimal price) { this.price = price; }
}