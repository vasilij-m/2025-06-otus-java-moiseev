package ru.otus;

public class App {
    public static void main(String[] args) throws ClassNotFoundException {
        TestRunner testRunner = new TestRunner();
        testRunner.runTests("ru.otus.CalculatorTest");
    }
}
