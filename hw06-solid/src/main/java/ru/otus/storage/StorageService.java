package ru.otus.storage;

import java.util.List;
import ru.otus.cash.Banknote;

/**
 * Интерфейс для работы с хранилищем банкнот
 */
public interface StorageService {

    /**
     * Положить банкноты в хранилище
     */
    void deposit(List<Banknote> banknotes);

    /**
     * Выдает запрошенную сумму банкнотами
     */
    List<Banknote> withdraw(int amount);

    /**
     * Получить общую сумму денег в хранилище
     */
    int getTotalAmount();
}
