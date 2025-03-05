package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userDao = new UserServiceImpl();

        userDao.createUsersTable();

        userDao.saveUser("Alex", "Bordun", (byte) 21);
        userDao.saveUser("Tom", "Criz", (byte) 22);
        userDao.saveUser("John", "Delly", (byte) 23);
        userDao.saveUser("Steve", "Erys", (byte) 24);

        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

        Util.closeConnection();
    }

}