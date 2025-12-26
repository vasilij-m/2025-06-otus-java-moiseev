package ru.otus.atm;

import java.util.List;
import ru.otus.cash.Banknote;

/**
 * Интерфейс для работы с банкоматом
 */
public interface ATMService {

    /**
     * Внести банкноты
     */
    void deposit(List<Banknote> banknotes);

    /**
     * Снять деньги
     */
    List<Banknote> withdraw(int amount);

    /**
     * Получить доступную для снятия сумму денег в банкомате
     */
    int getBalance();
}
