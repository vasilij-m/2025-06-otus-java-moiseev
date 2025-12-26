package ru.otus.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import ru.otus.cash.Banknote;
import ru.otus.cash.Denomination;
import ru.otus.exception.InsufficientFundsException;

/**
 * Хранилище банкомата, управляющее всеми ячейками для банкнот
 */
public class BanknoteStorage implements StorageService {

    private final Map<Denomination, Cell> cells;

    public BanknoteStorage() {
        this.cells = new EnumMap<>(Denomination.class);
        initializeStorage();
    }

    private void initializeStorage() {
        for (Denomination denomination : Denomination.values()) {
            cells.put(denomination, new Cell(denomination));
        }
    }

    @Override
    public void deposit(List<Banknote> banknotes) {
        Map<Denomination, List<Banknote>> banknotesByDenomination = groupBanknotesByDenomination(banknotes);

        for (Map.Entry<Denomination, List<Banknote>> entry : banknotesByDenomination.entrySet()) {
            Denomination denomination = entry.getKey();
            Cell cell = cells.get(denomination);
            cell.addBanknotes(entry.getValue());
        }
    }

    @Override
    public List<Banknote> withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма для выдачи должна быть положительной");
        }

        if (!canWithdrawAmount(amount)) {
            throw new InsufficientFundsException("Невозможно выдать запрошенную сумму доступными банкнотами");
        }

        List<Banknote> result = new ArrayList<>();
        int remainingAmount = amount;

        for (Denomination denomination : getDenominationsDesc()) {
            Cell cell = cells.get(denomination);
            int denominationValue = denomination.getValue();

            if (denominationValue > remainingAmount) {
                continue;
            }

            int banknoteCountNeeded = remainingAmount / denominationValue;
            int availableBanknoteCount = cell.getBanknoteCount();
            int banknoteCountToWithdraw = Math.min(banknoteCountNeeded, availableBanknoteCount);

            if (banknoteCountToWithdraw > 0) {
                List<Banknote> banknotesFromCell = cell.withdrawBanknotes(banknoteCountToWithdraw);
                result.addAll(banknotesFromCell);
                remainingAmount -= banknoteCountToWithdraw * denominationValue;

                if (remainingAmount == 0) {
                    return result;
                }
            }
        }

        throw new IllegalStateException("Произошла ошибка при выдаче банкнот");
    }

    @Override
    public int getTotalAmount() {
        return cells.values().stream().mapToInt(Cell::getTotalAmount).sum();
    }

    /**
     * Сгруппировать банкноты по номиналу перед их отправкой в хранилище
     */
    private Map<Denomination, List<Banknote>> groupBanknotesByDenomination(List<Banknote> banknotes) {
        Map<Denomination, List<Banknote>> groupedBanknotesByDenomination = new EnumMap<>(Denomination.class);

        for (Banknote banknote : banknotes) {
            Denomination denomination = banknote.getDenomination();

            groupedBanknotesByDenomination
                    .computeIfAbsent(denomination, k -> new ArrayList<>())
                    .add(banknote);
        }

        return groupedBanknotesByDenomination;
    }

    /**
     * Проверить, можно ли выдать запрошенную сумму доступными в ячейках хранилища банкнотами
     */
    private boolean canWithdrawAmount(int amount) {
        int remainingAmount = amount;

        for (Denomination denomination : getDenominationsDesc()) {
            Cell cell = cells.get(denomination);
            int denominationValue = denomination.getValue();

            if (denominationValue > remainingAmount) {
                continue;
            }

            int banknoteCountNeeded = remainingAmount / denominationValue;
            int availableBanknoteCount = cell.getBanknoteCount();
            int banknoteCountToWithdraw = Math.min(banknoteCountNeeded, availableBanknoteCount);

            if (banknoteCountToWithdraw > 0) {
                remainingAmount -= banknoteCountToWithdraw * denominationValue;

                if (remainingAmount == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Получить номиналы в порядке убывания (от большего к меньшему)
     */
    private List<Denomination> getDenominationsDesc() {
        return Arrays.stream(Denomination.values())
                .sorted(Comparator.comparingInt(Denomination::getValue).reversed())
                .toList();
    }
}
