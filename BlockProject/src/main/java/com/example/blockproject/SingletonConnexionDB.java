package com.example.blockproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnexionDB {
    private static final String DATABASE_NAME = "inventory_management";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/" + DATABASE_NAME + "?useSSL=false";

    // Always returns a new connection
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL Driver
            return DriverManager.getConnection(URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to the database.");
            return null;
        }
    }
}
