package org.risf.service;

import org.risf.model.OrderItem;
import org.risf.model.Receipt;

import java.util.List;

public class OrderProcessor {
    public Receipt processOrder(List<OrderItem> orderItems) {
        Receipt receipt = new Receipt();
        for (OrderItem orderItem : orderItems) {
            addItemToReceipt(receipt, orderItem);
        }
        return receipt;
    }

    private void addItemToReceipt(Receipt receipt, OrderItem item) {
        receipt.addItem(item);
    }
}
