package ru.gb.db;

public class Student {
    private String login;
    private String password;

    public Student(String login, String password) {
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
