package com.example.exam;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepasDAO {
    private PlatPrincipalDAO platPrincipalDAO = new PlatPrincipalDAO();
    private SupplementDAO supplementDAO = new SupplementDAO();

    public void save(Repas repas) throws SQLException {
        String query = "INSERT INTO Repas (commande_id, plat_principal_id, total, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, repas.getCommandeId());
            ps.setInt(2, repas.getPlatPrincipal().getId());
            ps.setBigDecimal(3, repas.getTotal());
            ps.setTimestamp(4, Timestamp.valueOf(repas.getCreatedAt()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                repas.setId(rs.getInt(1));
            }

            // Save supplements
            for (Supplement supplement : repas.getSupplements()) {
                saveSupplementRelation(repas.getId(), supplement.getId());
            }
        }
    }

    private void saveSupplementRelation(int repasId, int supplementId) throws SQLException {
        String query = "INSERT INTO RepasSupplement (repas_id, supplement_id) VALUES (?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, repasId);
            ps.setInt(2, supplementId);
            ps.executeUpdate();
        }
    }

    public Repas findById(int id) throws SQLException {
        String query = "SELECT * FROM Repas WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PlatPrincipal platPrincipal = platPrincipalDAO.findById(rs.getInt("plat_principal_id"));
                Repas repas = new Repas(platPrincipal);
                repas.setId(id);
                repas.setCommandeId(rs.getInt("commande_id"));
                repas.setTotal(BigDecimal.valueOf(rs.getDouble("total")));
                repas.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                repas.getSupplements().addAll(findSupplementsByRepasId(id));
                return repas;
            }
        }
        return null;
    }

    private List<Supplement> findSupplementsByRepasId(int repasId) throws SQLException {
        List<Supplement> supplements = new ArrayList<>();
        String query = "SELECT supplement_id FROM RepasSupplement WHERE repas_id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, repasId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                supplements.add(supplementDAO.findById(rs.getInt("supplement_id")));
            }
        }
        return supplements;
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Repas WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();

            // Delete related supplements
            deleteSupplementRelations(id);
        }
    }

    private void deleteSupplementRelations(int repasId) throws SQLException {
        String query = "DELETE FROM RepasSupplement WHERE repas_id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, repasId);
            ps.executeUpdate();
        }
    }
}