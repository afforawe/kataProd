package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();
    private static final String INSERT_NEW = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS users";
    private static final String CLEAR_TABLE = "TRUNCATE TABLE users";
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE IF NOT EXISTS users ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "last_name VARCHAR(50) NOT NULL, "
                    + "age TINYINT NOT NULL" + ")";


    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_USER);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании таблицы users: "
                    + e.getMessage(), e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE_USER);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы users: "
                    + e.getMessage(), e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection
                .prepareStatement(INSERT_NEW)) {
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("Выполнен rollback в saveUser");
            } catch (SQLException ex) {
                System.err.println("Ошибка при вызове rollback в saveUser: "
                        + ex.getMessage());
            }
            throw new RuntimeException("Ошибка при добавлении пользователя: "
                    + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Ошибка при возвращении auto-commit в saveUser: "
                        + ex.getMessage());
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("Выполнен rollback в removeUserById");
            } catch (SQLException ex) {
                System.err.println("Ошибка при вызове rollback в removeUserById: "
                        + ex.getMessage());
            }
            throw new RuntimeException("Ошибка при удалении пользователя: "
                    + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Ошибка при возвращении auto-commit в removeUserById: "
                        + ex.getMessage());
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Statement statement = connection.createStatement()) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка пользователей: "
                    + e.getMessage(), e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(CLEAR_TABLE)) {
            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println("Выполнен rollback в cleanUsersTable");
            } catch (SQLException ex) {
                System.err.println("Ошибка при вызове rollback в cleanUsersTable: "
                        + ex.getMessage());
            }
            throw new RuntimeException("Ошибка при очистке таблицы users: "
                    + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Ошибка при возвращении auto-commit в cleanUsersTable: "
                        + ex.getMessage());
            }
        }
    }
}
