package ru.gb.reflection;

import ru.gb.reflection.annotations.AfterSuite;
import ru.gb.reflection.annotations.BeforeSuite;
import ru.gb.reflection.annotations.Test;

public class NeedToTest {

    @Test(priority = 2)
    public void testMethod1() {
        System.out.println("Test method priority = 2");
    }

    @Test(priority = 1)
    public void testMethod2() {
        System.out.println("Test method priority = 1");
    }

    @Test(priority = 3)
    public void testMethod3() {
        System.out.println("Test method priority = 3");
    }

    @AfterSuite
    public void afterMethod() {
        System.out.println("AfterSuite method");
    }

    @BeforeSuite
    public void beforeSuiteMethod() {
        System.out.println("BeforeSuite method");
    }

}