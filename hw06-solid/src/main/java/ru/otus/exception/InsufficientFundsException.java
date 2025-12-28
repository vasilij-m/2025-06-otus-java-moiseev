package ru.otus.exception;

/**
 * Исключение, выбрасываемое при невозможности выдать запрошенную сумму или при недостаточном балансе
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
