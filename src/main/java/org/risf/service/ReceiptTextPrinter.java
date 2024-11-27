package org.risf.service;

import org.risf.model.OrderItem;
import org.risf.model.Receipt;

public class ReceiptTextPrinter implements ReceiptPrinter {
    private static final String LINE_FORMAT = "%d %s: %.2f";

    @Override
    public void print(Receipt receipt) {
        receipt.getOrderItems().forEach(ReceiptTextPrinter::printOrderItemLine);
        System.out.println("Sales Taxes: " + String.format("%.2f", receipt.getTotalTaxes()));
        System.out.println("Total: " + String.format("%.2f", receipt.getTotalPrice()));
        System.out.println();
    }

    private static void printOrderItemLine(OrderItem orderItem) {
        System.out.printf((LINE_FORMAT) + "%n", orderItem.quantity(), orderItem.product().name(), orderItem.product().price());
    }
}
