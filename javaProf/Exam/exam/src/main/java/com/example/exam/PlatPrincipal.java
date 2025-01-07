package com.example.exam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlatPrincipal {
    private int id;
    private String nom;
    private String description;
    private BigDecimal prixBase;
    private boolean disponible;
    private Map<Ingredient, BigDecimal> ingredients;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor (required for frameworks like Hibernate)
    public PlatPrincipal() {
        this.ingredients = new HashMap<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Parameterized constructor
    public PlatPrincipal(String nom, String description, BigDecimal prixBase) {
        this.nom = Objects.requireNonNull(nom, "Nom cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.prixBase = Objects.requireNonNull(prixBase, "PrixBase cannot be null");
        this.disponible = true;
        this.ingredients = new HashMap<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal calculatePrix() {
        BigDecimal total = prixBase;
        for (Map.Entry<Ingredient, BigDecimal> entry : ingredients.entrySet()) {
            Ingredient ingredient = entry.getKey();
            BigDecimal quantite = entry.getValue();
            total = total.add(ingredient.getPrixUnitaire().multiply(quantite));
        }
        return total;
    }

    public void addIngredient(Ingredient ingredient, BigDecimal quantite) {
        if (ingredient != null && quantite != null && quantite.compareTo(BigDecimal.ZERO) > 0) {
            ingredients.put(ingredient, quantite);
            updatedAt = LocalDateTime.now();
        }
    }

    public void removeIngredient(Ingredient ingredient) {
        if (ingredient != null) {
            ingredients.remove(ingredient);
            updatedAt = LocalDateTime.now();
        }
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = Objects.requireNonNull(nom, "Nom cannot be null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, "Description cannot be null");
    }

    public BigDecimal getPrixBase() {
        return prixBase;
    }

    public void setPrixBase(BigDecimal prixBase) {
        this.prixBase = Objects.requireNonNull(prixBase, "PrixBase cannot be null");
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Map<Ingredient, BigDecimal> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, BigDecimal> ingredients) {
        this.ingredients = Objects.requireNonNull(ingredients, "Ingredients cannot be null");
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt cannot be null");
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = Objects.requireNonNull(updatedAt, "UpdatedAt cannot be null");
    }

    @Override
    public String toString() {
        return "PlatPrincipal{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prixBase=" + prixBase +
                ", disponible=" + disponible +
                ", ingredients=" + ingredients +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}