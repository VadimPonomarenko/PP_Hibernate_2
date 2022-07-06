package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getCon();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            String sql = """
                    CREATE TABLE IF NOT EXISTS `user`.`users` (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastName` VARCHAR(45) NOT NULL,
                      `age` INT NOT NULL,
                      PRIMARY KEY (`id`));""";
            connection.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            connection.createStatement().execute("drop table if exists `user`.`users`");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into `user`.`users`(name, lastName, age) values(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from `user`.`users` where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> items = new ArrayList<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT id, name, lastName, age FROM `user`.`users`");
            while (rs.next()) {
                User item = new User();
                item.setId(rs.getLong(1));
                item.setName(rs.getString(2));
                item.setLastName(rs.getString(3));
                item.setAge(rs.getByte(4));
                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return items;
    }

    public void cleanUsersTable() {
        try {
            connection.createStatement().execute("TRUNCATE TABLE `user`.`users`");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
