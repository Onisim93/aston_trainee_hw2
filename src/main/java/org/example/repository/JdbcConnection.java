package org.example.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnection {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    private static Connection connection;

    static {
        try (InputStream inputProp = JdbcConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            prop.load(inputProp);
            URL = prop.getProperty("db.url");
            USER = prop.getProperty("db.user");
            PASSWORD = prop.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection()  {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
