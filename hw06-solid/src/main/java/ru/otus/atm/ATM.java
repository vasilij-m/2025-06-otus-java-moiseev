package ru.otus.atm;

import java.util.List;
import ru.otus.cash.Banknote;
import ru.otus.exception.InsufficientFundsException;
import ru.otus.storage.BanknoteStorage;

public class ATM implements ATMService {

    private final BanknoteStorage storage;

    public ATM() {
        this.storage = new BanknoteStorage();
    }

    @Override
    public void deposit(List<Banknote> banknotes) {
        if (banknotes.isEmpty()) {
            throw new IllegalArgumentException("Список банкнот не может быть пустым");
        }

        storage.deposit(banknotes);
    }

    @Override
    public List<Banknote> withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }

        int totalAmount = storage.getTotalAmount();

        if (amount > totalAmount) {
            throw new InsufficientFundsException("Запрошенная сумма превышает доступный баланс в банкомате");
        }

        return storage.withdraw(amount);
    }

    @Override
    public int getBalance() {
        return storage.getTotalAmount();
    }
}
