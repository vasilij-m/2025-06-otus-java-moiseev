package ru.otus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class CalculatorTest {
    private static final Logger log = LoggerFactory.getLogger(CalculatorTest.class);
    private Calculator calculator;

    @Before
    public void setUp() {
        calculator = new Calculator();
        log.info("Setting up test environment with instance {}", calculator);
    }

    @After
    public void tearDown() {
        log.info("Cleaning up after test...");
        calculator = null;
    }

    @Test
    public void testAddition() {
        int result = calculator.add(2, 3);
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void testSubtraction() {
        int result = calculator.subtract(5, 3);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testMultiplication() {
        int result = calculator.multiply(4, 3);
        assertThat(result).isEqualTo(11);
    }

    @Test
    public void testDivision() {
        int result = calculator.divide(10, 2);
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void testDivisionByZero() {
        try {
            calculator.divide(10, 0);
            log.info("Should have thrown exception for division by zero");
        } catch (IllegalArgumentException e) {
            log.info("✓ testDivisionByZero passed - exception caught: {}", e.getMessage());
        }
    }
}
