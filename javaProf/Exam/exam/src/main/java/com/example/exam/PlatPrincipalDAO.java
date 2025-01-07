package com.example.exam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatPrincipalDAO {
    public void save(PlatPrincipal plat) throws SQLException {
        String query = "INSERT INTO platprincipal (nom, description, prix_base, disponible, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, plat.getNom());
            ps.setString(2, plat.getDescription());
            ps.setBigDecimal(3, plat.getPrixBase());
            ps.setBoolean(4, plat.isDisponible());
            ps.setTimestamp(5, Timestamp.valueOf(plat.getCreatedAt()));
            ps.setTimestamp(6, Timestamp.valueOf(plat.getUpdatedAt()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                plat.setId(rs.getInt(1));
            }
        }
    }

    public PlatPrincipal findById(int id) throws SQLException {
        String query = "SELECT * FROM platprincipal WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PlatPrincipal plat = new PlatPrincipal(
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getBigDecimal("prix_base")
                );
                plat.setId(rs.getInt("id"));
                plat.setDisponible(rs.getBoolean("disponible"));
                plat.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                plat.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                return plat;
            }
        }
        return null;
    }

    public List<PlatPrincipal> findAll() throws SQLException {
        List<PlatPrincipal> plats = new ArrayList<>();
        String query = "SELECT * FROM platprincipal";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PlatPrincipal plat = new PlatPrincipal(
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getBigDecimal("prix_base")
                );
                plat.setId(rs.getInt("id"));
                plat.setDisponible(rs.getBoolean("disponible"));
                plat.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                plat.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                plats.add(plat);
            }
        }
        return plats;
    }

    public void update(PlatPrincipal plat) throws SQLException {
        String query = "UPDATE platprincipal SET nom = ?, description = ?, prix_base = ?, disponible = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, plat.getNom());
            ps.setString(2, plat.getDescription());
            ps.setBigDecimal(3, plat.getPrixBase());
            ps.setBoolean(4, plat.isDisponible());
            ps.setTimestamp(5, Timestamp.valueOf(plat.getUpdatedAt()));
            ps.setInt(6, plat.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM platprincipal WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}