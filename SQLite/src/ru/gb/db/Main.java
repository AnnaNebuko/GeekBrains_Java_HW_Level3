package ru.gb.db;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Authorization authorization = new Authorization();

        Optional<Student> mayBeStudent1 = authorization.login("L1", "P1");
        check(mayBeStudent1);
    }

    private static void check(Optional<Student> mayBeStudent) {
        if(mayBeStudent.isPresent()) System.out.println("Hello, student!");
        else System.out.println("Go away!");
    }
}
