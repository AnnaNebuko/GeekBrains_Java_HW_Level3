package ru.gb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Authorization {
    Connection con = DBConnection.ConnectionBD();

    public Optional<Student> login(String login, String password) {
        try {
            String sql = "select * from students where login like ? AND " +
                    "password like ?; ";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        new Student(
                                resultSet.getString("login"),
                                resultSet.getString("password")
                        )
                );
            }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return Optional.empty();
    }
    private void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
