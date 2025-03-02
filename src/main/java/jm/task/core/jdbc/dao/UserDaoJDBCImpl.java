package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String INSERT_NEW = "INSERT INTO users (name, last_name, age) VALUES (name, lastName, age)";
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String DROP_USER = "DROP TABLE users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

    }

    public void dropUsersTable() {

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(INSERT_NEW);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении пользователя: " + e.getMessage(), e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement(DELETE_USER)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя: " + e.getMessage(), e);
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = Util.getConnection().createStatement()) {
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
            throw new RuntimeException("Ошибка при получении списка пользователей: " + e.getMessage(), e);
        }
    }

    public void cleanUsersTable() {

    }
}
