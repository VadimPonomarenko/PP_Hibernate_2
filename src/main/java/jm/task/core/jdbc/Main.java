package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan","Beliy", (byte) 26);
        userService.saveUser("Vadim","Ponomarenko", (byte) 35);
        userService.saveUser("Petr","Antonov", (byte) 45);
        userService.saveUser("Artem","Novak", (byte) 15);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();

        try {
            Util.closeSessionFactory(UserDaoHibernateImpl.getSessionFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
