package org.example.repository.impl;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.varia.NullAppender;
import org.example.JdbcConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public abstract class RepositoryTest {
    private static PostgreSQLContainer<?> postgreSQLContainer;
    public static Connection connection;

    static {
        BasicConfigurator.configure(new NullAppender());
    }

    @BeforeEach
    public void initDatabase() {
        try {
            executeSQL("db_init.sql");
            executeSQL("db_data.sql");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @BeforeAll
    public static void setUp() throws SQLException {
        try (InputStream inputProp = JdbcConnection.class.getClassLoader().getResourceAsStream("databasetest.properties")) {
            Properties prop = new Properties();
            prop.load(inputProp);

            postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName(prop.getProperty("db.name"))
                    .withUsername(prop.getProperty("db.user"))
                    .withPassword(prop.getProperty("db.password"))
                    .withCommand("postgres", "-c", "client_encoding=UTF8");
            postgreSQLContainer.start();

            connection = DriverManager.getConnection(
                    postgreSQLContainer.getJdbcUrl() + "?characterEncoding=UTF-8&useUnicode=true",
                    postgreSQLContainer.getUsername(),
                    postgreSQLContainer.getPassword()
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        executeSQL("db_init.sql");
        executeSQL("db_data.sql");

        if (connection != null) {
            connection.close();
        }
        if (postgreSQLContainer != null) {
            postgreSQLContainer.stop();
            postgreSQLContainer.close();
        }

    }

    private static void executeSQL(String sql) throws SQLException {
        String script = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(RepositoryTest.class.getClassLoader().getResourceAsStream(sql)))).lines().collect(Collectors.joining("\n"));

        try (Statement statement = connection.createStatement()) {
            statement.execute(script);
        }
    }

}
