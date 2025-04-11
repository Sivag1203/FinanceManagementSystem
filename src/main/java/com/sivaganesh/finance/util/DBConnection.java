package com.sivaganesh.finance.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = PropertyUtil.loadProperties("db.properties");
                if (props == null) return null;

                String driver = props.getProperty("db.driver");
                String url = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
            } catch (Exception e) {
                System.out.println("Error establishing database connection: " + e.getMessage());
            }
        }
        return connection;
    }
}
