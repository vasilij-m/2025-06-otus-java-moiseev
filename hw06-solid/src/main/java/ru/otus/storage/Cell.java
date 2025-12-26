package ru.otus.storage;

import java.util.ArrayList;
import java.util.List;
import ru.otus.cash.Banknote;
import ru.otus.cash.Denomination;

/**
 * Ячейка банкомата для хранения банкнот определённого номинала
 */
public class Cell {

    private final Denomination denomination;
    private int banknoteCount;
    private final List<Banknote> banknotes;

    public Cell(Denomination denomination) {
        this.denomination = denomination;
        this.banknoteCount = 0;
        this.banknotes = new ArrayList<>();
    }

    public int getBanknoteCount() {
        return banknoteCount;
    }

    /**
     * Получить общую сумму денег в ячейке
     */
    public int getTotalAmount() {
        return banknoteCount * denomination.getValue();
    }

    /**
     * Добавить банкноты в ячейку
     */
    public void addBanknotes(List<Banknote> banknotesToAdd) {
        if (banknotesToAdd.isEmpty()) {
            return;
        }

        for (Banknote banknote : banknotesToAdd) {
            if (banknote.getDenomination() != this.denomination) {
                throw new IllegalArgumentException("Невозможно добавить банкноту номиналом "
                        + banknote.getDenomination() + " в ячейку для номинала " + this.denomination);
            }
            banknotes.add(banknote);
        }

        this.banknoteCount += banknotesToAdd.size();
    }

    /**
     * Извлечь указанное количество банкнот из ячейки
     */
    public List<Banknote> withdrawBanknotes(int countToWithdraw) {
        if (countToWithdraw < 0) {
            throw new IllegalArgumentException("Количество банкнот не может быть отрицательным");
        }

        if (!canWithdraw(countToWithdraw)) {
            throw new IllegalArgumentException("Запрошено " + countToWithdraw + " банкнот, но в ячейке для номинала "
                    + this.denomination + " только " + banknoteCount + " банкнот");
        }

        List<Banknote> withdrawnBanknotes = new ArrayList<>();
        for (int i = 0; i < countToWithdraw; i++) {
            if (!banknotes.isEmpty()) {
                withdrawnBanknotes.add(banknotes.removeLast());
            }
        }

        this.banknoteCount -= countToWithdraw;
        return withdrawnBanknotes;
    }

    /**
     * Проверить возможность извлечения указанного количества банкнот
     */
    public boolean canWithdraw(int requestedCount) {
        return requestedCount >= 0 && requestedCount <= banknoteCount;
    }
}
