package ru.gb.reflection;

public class Main {

    public static void main(String[] args) {
	    NeedToTest needToTest = new NeedToTest();
	    TestRunner.start(needToTest.getClass());
    }
}
