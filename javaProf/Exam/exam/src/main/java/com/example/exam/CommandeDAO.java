package com.example.exam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {
    private ClientDAO clientDAO = new ClientDAO();
    private RepasDAO repasDAO = new RepasDAO();

    public void save(Commande commande) throws SQLException {
        String query = "INSERT INTO Commande (client_id, date_commande, statut, total, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, commande.getClient().getId());
            ps.setTimestamp(2, Timestamp.valueOf(commande.getDateCommande()));
            ps.setString(3, commande.getStatut());
            ps.setDouble(4, commande.getTotal());
            ps.setTimestamp(5, Timestamp.valueOf(commande.getCreatedAt()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                commande.setId(rs.getInt(1));
            }

            // Save repas
            for (Repas repas : commande.getRepas()) {
                repas.setCommandeId(commande.getId()); // Set the commande_id in Repas
                repasDAO.save(repas); // Save the Repas object
            }
        }
    }

    public Commande findById(int id) throws SQLException {
        String query = "SELECT * FROM Commande WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Client client = clientDAO.findById(rs.getInt("client_id"));
                Commande commande = new Commande(client);
                commande.setId(id);
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commande.setStatut(rs.getString("statut"));
                commande.setTotal(rs.getDouble("total"));
                commande.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                commande.getRepas().addAll(findRepasByCommandeId(id));
                return commande;
            }
        }
        return null;
    }

    public List<Commande> findAll() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM Commande";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Client client = clientDAO.findById(rs.getInt("client_id"));
                Commande commande = new Commande(client);
                commande.setId(rs.getInt("id"));
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commande.setStatut(rs.getString("statut"));
                commande.setTotal(rs.getDouble("total"));
                commande.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                commande.getRepas().addAll(findRepasByCommandeId(commande.getId()));
                commandes.add(commande);
            }
        }
        return commandes;
    }

    private List<Repas> findRepasByCommandeId(int commandeId) throws SQLException {
        List<Repas> repasList = new ArrayList<>();
        String query = "SELECT * FROM Repas WHERE commande_id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, commandeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Repas repas = repasDAO.findById(rs.getInt("id")); // Fetch the full Repas object
                repasList.add(repas);
            }
        }
        return repasList;
    }

    public void delete(int id) throws SQLException {
        // Delete related repas first
        deleteRepasRelations(id);

        // Delete the commande
        String query = "DELETE FROM Commande WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private void deleteRepasRelations(int commandeId) throws SQLException {
        String query = "DELETE FROM Repas WHERE commande_id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, commandeId);
            ps.executeUpdate();
        }
    }
}