package com.example.exam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Commande {
    private int id;
    private Client client;
    private LocalDateTime dateCommande;
    private String statut;
    private double total;
    private List<Repas> repas;
    private LocalDateTime createdAt;

    public Commande(Client client) {
        this.client = client;
        this.dateCommande = LocalDateTime.now();
        this.statut = "en_attente"; // Default status
        this.repas = new ArrayList<>();
        this.total = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void addRepas(Repas repas) {
        if (repas != null) {
            this.repas.add(repas);
            calculateTotal();
        }
    }

    public void removeRepas(Repas repas) {
        if (repas != null) {
            this.repas.remove(repas);
            calculateTotal();
        }
    }

    public void calculateTotal() {
        // Initialize total to 0
        total = 0;

        // Check if the repas list is null or empty
        if (repas == null || repas.isEmpty()) {
            return; // If the list is null or empty, total remains 0
        }

        // Iterate through the repas list and sum the totals
        for (Repas r : repas) {
            if (r != null && r.getTotal() != null) { // Ensure Repas and its total are not null
                total += r.getTotal().doubleValue(); // Add the Repas total to the commande total
            }
        }
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        // Validate statut to ensure it is one of the allowed values
        if (statut != null && (statut.equals("en_attente") || statut.equals("en_preparation") ||
                statut.equals("pret") || statut.equals("livre") || statut.equals("annule"))) {
            this.statut = statut;
        } else {
            throw new IllegalArgumentException("Invalid statut value");
        }
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Repas> getRepas() {
        return repas;
    }

    public void setRepas(List<Repas> repas) {
        this.repas = repas;
        calculateTotal(); // Recalculate total when repas list is updated
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}