package com.sivaganesh.finance.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {
    private static Connection connection;

    static {
        initializeConnection();
    }

    private static void initializeConnection() {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {

            if (input == null) {
                System.out.println("❌ Could not find db.properties file in resources.");
                return;
            }

            Properties props = new Properties();
            props.load(input);

            String driver = props.getProperty("db.driver");
            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            // Load database driver
            Class.forName(driver);

            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Database connected successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver class not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Failed to establish DB connection!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ Error loading DB properties or connecting to DB!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                initializeConnection(); // Reconnect if connection is closed
            }
        } catch (SQLException e) {
            System.out.println("❌ Error checking DB connection state!");
            e.printStackTrace();
        }
        return connection;
    }
}
