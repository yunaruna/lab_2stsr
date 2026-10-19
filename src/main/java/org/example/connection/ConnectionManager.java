package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/jdbc_lab";

    private static final String USER = "postgres";

    private static final String PASSWORD = "4105";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
