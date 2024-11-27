package org.risf.service;

import org.risf.model.OrderItem;
import org.risf.model.Receipt;

import java.util.List;

public class OrderProcessor {
    private final TaxCalculationStrategy taxCalculationStrategy;

    public OrderProcessor(TaxCalculationStrategy taxCalculationStrategy) {
        this.taxCalculationStrategy = taxCalculationStrategy;
    }

    public Receipt processOrder(List<OrderItem> orderItems) {
        Receipt receipt = new Receipt();
        for (OrderItem orderItem : orderItems) {
            addItemToReceipt(receipt, orderItem);
        }
        return receipt;
    }

    private void addItemToReceipt(Receipt receipt, OrderItem item) {
        double tax = taxCalculationStrategy.calculateTax(item);
        double finalPrice = item.product().price() + tax;
        receipt.addItem(item, tax, finalPrice);
    }
}
