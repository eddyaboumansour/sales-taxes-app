package org.risf.service;

import org.risf.model.OrderItem;

public interface TaxCalculationStrategy {
    double calculateTax(OrderItem item);
}
