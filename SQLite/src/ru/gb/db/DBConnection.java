package ru.gb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    Connection conn = null;

    public static Connection ConnectionBD() {
        try {
            Class.forName("org.sqlite.JDBC");
            String jdbcUrl = "jdbc:sqlite:StudentsDB.db";
            Connection conn = DriverManager.getConnection(jdbcUrl);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
