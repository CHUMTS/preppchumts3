package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Vova", "Vovikov", (byte) 20);
        userService.saveUser("Vasya", "Vasikov", (byte) 25);
        userService.saveUser("Petya", "Petikov", (byte) 31);
        userService.saveUser("Kolya", "Kolikov", (byte) 38);

        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
