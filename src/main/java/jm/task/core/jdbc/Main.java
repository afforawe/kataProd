package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Alex", "Bordun", (byte) 21);
        userDao.saveUser("Tom", "Criz", (byte) 22);
        userDao.saveUser("John", "Delly", (byte) 23);
        userDao.saveUser("Steve", "Erys", (byte) 24);

        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }

}