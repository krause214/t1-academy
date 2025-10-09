package ru.academy.course.spirngcontext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public User createUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
        statement.setObject(1, user.getUsername());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        User created = null;
        while (resultSet.next()) {
            created = new User();
            created.setId(resultSet.getLong("id"));
            created.setUsername(resultSet.getString("username"));
        }
        resultSet.close();
        statement.close();
        return created;
    }

    public void updateUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET username = ? WHERE id = ?");
        statement.setString(1, user.getUsername());
        statement.setLong(2, user.getId());
        statement.executeUpdate();
        statement.close();
    }

    public User readUser(Long id) throws SQLException {
        User user = null;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users where id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
        }
        resultSet.close();
        statement.close();
        return user;
    }

    public void deleteUser(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users where id = ?");
        statement.setLong(1, id);
        if (statement.executeUpdate() == 0) {
            throw new IllegalArgumentException("    Нет значений с идентификатором " + id);
        }
        statement.close();
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
            userList.add(user);
        }
        resultSet.close();
        statement.close();
        return userList;
    }
}
