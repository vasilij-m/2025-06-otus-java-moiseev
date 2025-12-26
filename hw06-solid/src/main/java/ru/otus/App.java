package ru.otus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.atm.ATM;
import ru.otus.cash.Banknote;
import ru.otus.cash.Denomination;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        ATM atm = new ATM();

        // Проверка начального состояния банкомата
        log.info("1. Начальное состояние банкомата:");
        log.info("   Баланс: {}", atm.getBalance()); // 0

        // Загрузка банкнот в банкомат
        log.info("2. Загрузка банкнот в банкомат...");

        atm.deposit(createBanknotes(Denomination.FIVE_THOUSAND, 2)); // 2 x 5000 = 10000
        atm.deposit(createBanknotes(Denomination.ONE_THOUSAND, 5)); // 5 x 1000 = 5000
        atm.deposit(createBanknotes(Denomination.FIVE_HUNDRED, 10)); // 10 x 500 = 5000
        atm.deposit(createBanknotes(Denomination.ONE_HUNDRED, 8)); // 8 x 100 = 800

        log.info("   Баланс после загрузки: {}", atm.getBalance()); // 20800

        // Тест 1: Успешная выдача
        log.info("3. Тест 1: Успешная выдача 7800");
        testWithdrawal(atm, 7800);
        log.info("   Баланс после выдачи 7800: {}", atm.getBalance()); // 13000

        // Тест 2: Выдача невозможной суммы
        log.info("4. Тест 2: Попытка выдать 125 (невозможно)");
        testWithdrawal(atm, 125);
        log.info("   Баланс после попытки выдачи 125: {}", atm.getBalance()); // 13000

        // Тест 3: Успешная выдача
        log.info("5. Тест 3: Успешная выдача 800");
        testWithdrawal(atm, 800);
        log.info("   Баланс после выдачи 800: {}", atm.getBalance()); // 12200

        // Тест 4: Выдача невозможной суммы
        log.info("6. Тест 4: Попытка выдать 300 (невозможно)");
        testWithdrawal(atm, 300);
        log.info("   Баланс после попытки выдачи 300: {}", atm.getBalance()); // 12200

        // Тест 5: Выдача всей доступной суммы
        log.info("7. Тест 5: Выдача оставшейся суммы");
        int remainingBalance = atm.getBalance();
        testWithdrawal(atm, remainingBalance);
        log.info("   Баланс после попытки выдачи всех оставшихся денег: {}", atm.getBalance()); // 0
    }

    private static List<Banknote> createBanknotes(Denomination denomination, int count) {
        List<Banknote> banknotes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            banknotes.add(new Banknote(denomination));
        }
        return banknotes;
    }

    private static void testWithdrawal(ATM atm, int amount) {
        log.info("   Запрос: {}", amount);

        try {
            List<Banknote> withdrawnBanknotes = atm.withdraw(amount);
            log.info("   Успех: Выдано {} банкнот:", withdrawnBanknotes.size());
            String banknotesValue = withdrawnBanknotes.stream()
                    .map(Banknote::getValue)
                    .map(String::valueOf)
                    .collect(Collectors.joining("; "));
            log.info("   {}", banknotesValue);
        } catch (Exception e) {
            log.info("   Ошибка: {}", e.getMessage());
        }
    }
}
