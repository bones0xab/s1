package com.example.exam;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplementDAO {
    public void save(Supplement supplement) throws SQLException {
        String query = "INSERT INTO supplement (nom, prix, disponible, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, supplement.getNom());
            ps.setBigDecimal(2, supplement.getPrix());
            ps.setBoolean(3, supplement.isDisponible());
            ps.setTimestamp(4, Timestamp.valueOf(supplement.getCreatedAt()));
            ps.setTimestamp(5, Timestamp.valueOf(supplement.getUpdatedAt()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                supplement.setId(rs.getInt(1));
            }
        }
    }

    public Supplement findById(int id) throws SQLException {
        String query = "SELECT * FROM supplement WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Supplement supplement = new Supplement(
                        rs.getString("nom"),
                        rs.getBigDecimal("prix")
                );
                supplement.setId(rs.getInt("id"));
                supplement.setDisponible(rs.getBoolean("disponible"));
                return supplement;
            }
        }
        return null;
    }

    public List<Supplement> findAll() throws SQLException {
        List<Supplement> supplements = new ArrayList<>();
        String query = "SELECT * FROM supplement";
        try (Connection conn = SingletonConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Supplement supplement = new Supplement(
                        rs.getString("nom"),
                        rs.getBigDecimal("prix")
                );
                supplement.setId(rs.getInt("id"));
                supplement.setDisponible(rs.getBoolean("disponible"));
                supplements.add(supplement);
            }
        }
        return supplements;
    }

    public void update(Supplement supplement) throws SQLException {
        String query = "UPDATE supplement SET nom = ?, prix = ?, disponible = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, supplement.getNom());
            ps.setBigDecimal(2, supplement.getPrix());
            ps.setBoolean(3, supplement.isDisponible());
            ps.setTimestamp(4, Timestamp.valueOf(supplement.getUpdatedAt()));
            ps.setInt(5, supplement.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM supplement WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
