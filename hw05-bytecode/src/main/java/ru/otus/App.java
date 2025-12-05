package ru.otus;

public class App {

    public static void main(String[] args) {
        CalculationService loggedCalculator = Ioc.createLoggedCalculator();

        loggedCalculator.calculation(1);
        loggedCalculator.calculation(2, 3);
        loggedCalculator.calculation(4, "five");
        loggedCalculator.calculation(6, 7, "eight");
        loggedCalculator.calculation(9, "ten", "eleven");
    }
}
