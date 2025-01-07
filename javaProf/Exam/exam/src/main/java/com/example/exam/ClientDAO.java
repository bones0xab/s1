package com.example.exam;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public void save(Client client) throws SQLException {
        String query = "INSERT INTO Client (nom, prenom, email, telephone, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getTelephone());
            ps.setTimestamp(5, Timestamp.valueOf(client.getCreatedAt()));
            ps.setTimestamp(6, Timestamp.valueOf(client.getUpdatedAt()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                client.setId(rs.getInt(1));
            }
        }
    }

    public Client findById(int id) throws SQLException {
        String query = "SELECT * FROM Client WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                );
                client.setId(rs.getInt("id"));
                client.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                client.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                return client;
            }
        }
        return null;
    }

    public List<Client> findAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM Client";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                );
                client.setId(rs.getInt("id"));
                client.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                client.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                clients.add(client);
            }
        }
        return clients;
    }

    public void update(Client client) throws SQLException {
        String query = "UPDATE Client SET nom = ?, prenom = ?, email = ?, telephone = ?, updated_at = ? WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getTelephone());
            ps.setTimestamp(5, Timestamp.valueOf(client.getUpdatedAt()));
            ps.setInt(6, client.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Client WHERE id = ?";
        try (Connection conn = SingletonConnexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}