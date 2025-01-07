package com.example.exam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Repas {
    private int id;
    private PlatPrincipal platPrincipal;
    private List<Supplement> supplements;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private int commandeId; // Corrected field name

    public Repas(PlatPrincipal platPrincipal) {
        this.platPrincipal = platPrincipal;
        this.supplements = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.commandeId = 0; // Initialize to a default value
        calculateTotal();
    }

    public void addSupplement(Supplement supplement) {
        if (supplement.isDisponible()) {
            supplements.add(supplement);
            calculateTotal();
        }
    }

    public void removeSupplement(Supplement supplement) {
        supplements.remove(supplement);
        calculateTotal();
    }

    public void calculateTotal() {
        total = platPrincipal.calculatePrix();
        for (Supplement supplement : supplements) {
            total = total.add(supplement.getPrix());
        }
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlatPrincipal getPlatPrincipal() {
        return platPrincipal;
    }

    public void setPlatPrincipal(PlatPrincipal platPrincipal) {
        this.platPrincipal = platPrincipal;
        calculateTotal();
    }

    public List<Supplement> getSupplements() {
        return supplements;
    }

    public BigDecimal getTotal() { // Corrected return type
        return total;
    }

    public void setTotal(BigDecimal total) { // Added setter for total
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) { // Added setter for createdAt
        this.createdAt = createdAt;
    }

    public int getCommandeId() { // Corrected implementation
        return commandeId;
    }

    public void setCommandeId(int commandeId) { // Added setter for commandeId
        this.commandeId = commandeId;
    }
}