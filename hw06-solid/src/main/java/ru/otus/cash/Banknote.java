package ru.otus.cash;

/**
 * Банкнота определённого номинала
 */
public class Banknote {

    private final Denomination denomination;

    public Banknote(Denomination denomination) {
        this.denomination = denomination;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public int getValue() {
        return denomination.getValue();
    }
}
