package com.example.exam;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Supplement {
    private int id;
    private String nom;
    private BigDecimal prix;
    private boolean disponible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Supplement(String nom, BigDecimal prix) {
        this.nom = nom;
        this.prix = prix;
        this.disponible = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
