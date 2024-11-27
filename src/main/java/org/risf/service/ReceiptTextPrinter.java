package org.risf.service;

import org.risf.model.OrderItem;
import org.risf.model.Product;
import org.risf.model.ProductTaxIncluded;
import org.risf.model.Receipt;

public class ReceiptTextPrinter implements ReceiptPrinter {
    private static final String LINE_FORMAT = "%d %s: %.2f";

    @Override
    public void print(Receipt receipt) {
        receipt.getOrderItems().forEach(ReceiptTextPrinter::printOrderItemLine);
        printTotalPriceAndTaxes(receipt);
    }

    private static void printTotalPriceAndTaxes(Receipt receipt) {
        System.out.print("Sales Taxes: " + String.format("%.2f", receipt.getTotalTaxes()));
        System.out.println(" Total: " + String.format("%.2f", receipt.getTotalPrice()));
        System.out.println();
    }

    private static void printOrderItemLine(OrderItem orderItem) {
        ProductTaxIncluded productTaxIncluded = orderItem.productTaxIncluded();
        Product product = productTaxIncluded.product();
        System.out.printf((LINE_FORMAT) + "%n", orderItem.quantity(), product.name(), productTaxIncluded.getPriceWithTax());
    }
}
