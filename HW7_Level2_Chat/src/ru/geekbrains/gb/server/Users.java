package ru.geekbrains.gb.server;

public class Users {

    private String login;
    private String password;

    public Users(String login, String password) {
        this.login = login;
        this.password = password;
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
}
