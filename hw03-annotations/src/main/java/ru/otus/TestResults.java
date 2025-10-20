package ru.otus;

public class TestResults {
    private int testsPassed;
    private int testsFailed;

    public TestResults() {
        this.testsPassed = 0;
        this.testsFailed = 0;
    }

    public int getTestsPassed() {
        return testsPassed;
    }

    public int getTestsFailed() {
        return testsFailed;
    }

    public void incrementPassed() {
        this.testsPassed++;
    }

    public void incrementFailed() {
        this.testsFailed++;
    }

    public int getTestsTotal() {
        return testsPassed + testsFailed;
    }
}
