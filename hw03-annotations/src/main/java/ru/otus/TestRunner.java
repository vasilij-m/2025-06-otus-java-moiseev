package ru.otus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestRunner {
    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    public void runTests(String testClass) throws ClassNotFoundException {
        Class<?> testClazz = Class.forName(testClass);
        TestResults results = new TestResults();

        log.info("Running tests for: {}", testClazz.getSimpleName());
        log.info("=".repeat(50));

        try {
            List<Method> testMethods = new ArrayList<>();
            List<Method> beforeMethods = new ArrayList<>();
            List<Method> afterMethods = new ArrayList<>();

            for (Method method : testClazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    testMethods.add(method);
                } else if (method.isAnnotationPresent(Before.class)) {
                    beforeMethods.add(method);
                } else if (method.isAnnotationPresent(After.class)) {
                    afterMethods.add(method);
                }
            }

            for (Method testMethod : testMethods) {
                runSingleTest(testClazz, testMethod, beforeMethods, afterMethods, results);
            }

        } catch (Exception e) {
            log.info("❌ Error running tests: {}", e.getMessage());
            e.printStackTrace();
        }

        printResults(results);
    }

    private void runSingleTest(
            Class<?> testClazz,
            Method testMethod,
            List<Method> beforeMethods,
            List<Method> afterMethods,
            TestResults testResults)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Object testInstance = testClazz.getDeclaredConstructor().newInstance();
        String testName = testMethod.getName();

        try {
            for (Method beforeMethod : beforeMethods) {
                String beforeMethodName = beforeMethod.getName();
                log.info("Run before method: {}", beforeMethodName);
                beforeMethod.invoke(testInstance);
            }

            testMethod.invoke(testInstance);
            log.info("✅ PASSED: {}", testName);
            testResults.incrementPassed();

        } catch (Exception e) {
            log.error("❌ FAILED in {}: {}", testName, e.getCause().getMessage());
            testResults.incrementFailed();

        } finally {
            try {
                for (Method afterMethod : afterMethods) {
                    afterMethod.invoke(testInstance);
                }
            } catch (Exception e) {
                log.error("ERROR in @After method: {}", e.getMessage());
            }
        }
    }

    private void printResults(TestResults results) {
        log.info("=".repeat(50));
        log.info("TEST RESULTS:");
        log.info("✅ Passed: {}", results.getTestsPassed());
        log.info("❌ Failed: {}", results.getTestsFailed());
        log.info("  Total: {}", results.getTestsTotal());
    }
}
