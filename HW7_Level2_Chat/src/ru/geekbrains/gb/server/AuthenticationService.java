package ru.geekbrains.gb.server;

import java.sql.*;
import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService {

    private String login;
    private String password;
    Connection con = DBConnection.ConnectionBD();

    public AuthenticationService(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public AuthenticationService() {

    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "Student{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Optional<AuthenticationService> authorization(String _login, String _password) {

        try {
            String sql = "select * from students WHERE login like ? AND password like ?; ";

            PreparedStatement statement = con.prepareStatement(sql);

            //statement.setString(1, "\'" + _login + "\'");
            statement.setString(1, _login);
            statement.setString(2, _password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        new AuthenticationService(
                                resultSet.getString("login"),
                                resultSet.getString("password")
                        )
                );
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public Optional<AuthenticationService> findReceiver(String receiver) {
        try {
            String sql = "select * from students WHERE  login like ? ; ";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, receiver);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        new AuthenticationService(
                                resultSet.getString("login"),
                                resultSet.getString("password")
                        )
                );
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
