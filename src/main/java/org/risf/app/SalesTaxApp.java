package org.risf.app;

import org.risf.model.OrderItem;
import org.risf.model.Product;
import org.risf.model.ProductType;
import org.risf.model.Receipt;
import org.risf.service.*;

import java.util.List;

public class SalesTaxApp {
    public static void main(String[] args) {
        TaxCalculationStrategy defaultStrategy = new DefaultTaxStrategy();
        OrderProcessor defaultProcessor = new OrderProcessor(defaultStrategy);
        ReceiptPrinter receiptTextPrinter = new ReceiptTextPrinter();

        Receipt receipt1 = defaultProcessor.processOrder(getInput1());
        receiptTextPrinter.print(receipt1);

        Receipt receipt2 = defaultProcessor.processOrder(getInput2());
        receiptTextPrinter.print(receipt2);

        Receipt receipt3 = defaultProcessor.processOrder(getInput3());
        receiptTextPrinter.print(receipt3);
    }

    private static List<OrderItem> getInput1() {
        return List.of(
                new OrderItem(new Product("book", ProductType.BOOK, 12.49, false), 1),
                new OrderItem(new Product("music CD", ProductType.OTHER, 14.99, false), 1),
                new OrderItem(new Product("chocolate bar", ProductType.FOOD, 0.85, false), 1));
    }

    private static List<OrderItem> getInput2() {
        return List.of(
                new OrderItem(new Product("imported box of chocolates", ProductType.FOOD, 10.00, true), 1),
                new OrderItem(new Product("imported bottle of perfume", ProductType.OTHER, 47.50, true), 1));
    }

    private static List<OrderItem> getInput3() {
        return List.of(
                new OrderItem(new Product("imported bottle of perfume", ProductType.OTHER, 27.99, true), 1),
                new OrderItem(new Product("bottle of perfume", ProductType.OTHER, 18.99, false), 1),
                new OrderItem(new Product("packet of headache pills", ProductType.MEDICAL, 9.75, false), 1),
                new OrderItem(new Product("box of imported chocolates", ProductType.FOOD, 11.25, true), 1));
    }
}
