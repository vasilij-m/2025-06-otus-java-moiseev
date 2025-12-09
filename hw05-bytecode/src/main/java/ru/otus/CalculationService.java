package ru.otus;

public interface CalculationService {
    void calculation(int param1);

    void calculation(int param1, int param2);

    void calculation(int param1, String param2);

    void calculation(int param1, int param2, String param3);

    void calculation(int param1, String param2, String param3);
}
