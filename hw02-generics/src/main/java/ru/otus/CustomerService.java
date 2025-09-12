package ru.otus;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {
    private final NavigableMap<Customer, String> customers;

    public CustomerService() {
        customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> smallestCustomer = customers.firstEntry();
        if (smallestCustomer == null) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(copyCustomer(smallestCustomer.getKey()), smallestCustomer.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> nextCustomer = customers.higherEntry(customer);
        if (nextCustomer == null) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(copyCustomer(nextCustomer.getKey()), nextCustomer.getValue());
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    private Customer copyCustomer(Customer customer) {
        return new Customer(customer.getId(), customer.getName(), customer.getScores());
    }
}
