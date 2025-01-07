package com.example.exam;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ingredient {
    private int id;
    private String nom;
    private String unite;
    private BigDecimal prixUnitaire;
    private boolean disponible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Ingredient(String nom, String unite, BigDecimal prixUnitaire) {
        this.nom = nom;
        this.unite = unite;
        this.prixUnitaire = prixUnitaire;
        this.disponible = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getUnite() { return unite; }
    public void setUnite(String unite) { this.unite = unite; }
    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}