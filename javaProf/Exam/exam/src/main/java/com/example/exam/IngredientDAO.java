package com.example.exam;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    public void save(Ingredient ingredient) throws SQLException {
        String query = "INSERT INTO ingredient (nom, unite, prix_unitaire, disponible, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ingredient.getNom());
            ps.setString(2, ingredient.getUnite());
            ps.setBigDecimal(3, ingredient.getPrixUnitaire());
            ps.setBoolean(4, ingredient.isDisponible());
            ps.setTimestamp(5, Timestamp.valueOf(ingredient.getCreatedAt()));
            ps.setTimestamp(6, Timestamp.valueOf(ingredient.getUpdatedAt()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ingredient.setId(rs.getInt(1));
            }
        }
    }

    public Ingredient findById(int id) throws SQLException {
        String query = "SELECT * FROM ingredient WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ingredient ingredient = new Ingredient(
                        rs.getString("nom"),
                        rs.getString("unite"),
                        rs.getBigDecimal("prix_unitaire")
                );
                ingredient.setId(rs.getInt("id"));
                ingredient.setDisponible(rs.getBoolean("disponible"));
                return ingredient;
            }
        }
        return null;
    }

    public List<Ingredient> findAll() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredient";
        try (Connection conn = SingletonConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Ingredient ingredient = new Ingredient(
                        rs.getString("nom"),
                        rs.getString("unite"),
                        rs.getBigDecimal("prix_unitaire")
                );
                ingredient.setId(rs.getInt("id"));
                ingredient.setDisponible(rs.getBoolean("disponible"));
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }

    public void update(Ingredient ingredient) throws SQLException {
        String query = "UPDATE ingredient SET nom = ?, unite = ?, prix_unitaire = ?, disponible = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ingredient.getNom());
            ps.setString(2, ingredient.getUnite());
            ps.setBigDecimal(3, ingredient.getPrixUnitaire());
            ps.setBoolean(4, ingredient.isDisponible());
            ps.setTimestamp(5, Timestamp.valueOf(ingredient.getUpdatedAt()));
            ps.setInt(6, ingredient.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM ingredient WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}