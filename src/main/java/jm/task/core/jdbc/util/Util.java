package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private static Connection connection = null;

    private Util() {
    }

    public static Connection getConnection() {

        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при открытии соединения: "
                    + e.getMessage(), e);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (!connection.isClosed() || connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при закрытии соединения: "
                    + e.getMessage(), e);
        }
    }
}
