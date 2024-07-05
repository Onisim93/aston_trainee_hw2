package org.example;

import org.example.exception.DataAccessException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class JdbcConnection {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    private static Connection connection;

    private JdbcConnection() {}

    static {
        try (InputStream inputProp = JdbcConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            prop.load(inputProp);
            URL = prop.getProperty("db.url");
            USER = prop.getProperty("db.user");
            PASSWORD = prop.getProperty("db.password");
        } catch (IOException e) {
            throw new DataAccessException(e);
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                executeSql("db_init.sql");
                executeSql("db_data.sql");
            }
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DataAccessException(e);
        }
    }

    private static void executeSql(String sql) throws SQLException {
        String script = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(JdbcConnection.class.getClassLoader().getResourceAsStream(sql)), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        try (Statement statement = connection.createStatement()) {
            statement.execute(script);
        }
    }
}
