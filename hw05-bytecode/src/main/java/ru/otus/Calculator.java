package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.Log;

public class Calculator implements CalculationService {
    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    @Log
    @Override
    public void calculation(int param1) {
        log.info("Вызван логируемый метод calculation(int param1)");
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        log.info("Вызван логируемый метод calculation(int param1, int param2)");
    }

    @Override
    public void calculation(int param1, String param2) {
        log.info("Вызван НЕлогируемый метод calculation(int param1, String param2)");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        log.info("Вызван логируемый метод calculation(int param1, int param2, String param3)");
    }

    @Override
    public void calculation(int param1, String param2, String param3) {
        log.info("Вызван НЕлогируемый метод calculation(int param1, String param2, String param3)");
    }
}
