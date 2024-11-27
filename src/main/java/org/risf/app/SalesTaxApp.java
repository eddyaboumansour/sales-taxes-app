package org.risf.app;

import org.risf.model.*;
import org.risf.service.*;

import java.util.List;

public class SalesTaxApp {
    public static void main(String[] args) {
        OrderItemMapper orderItemMapper = new OrderItemMapper(new DefaultTaxStrategy());
        OrderProcessor orderProcessor = new OrderProcessor();
        ReceiptPrinter receiptTextPrinter = new ReceiptTextPrinter();


        List<OrderItem> orderItems1 = orderItemMapper.map(getInput1());
        Receipt receipt1 = orderProcessor.processOrder(orderItems1);
        receiptTextPrinter.print(receipt1);

        List<OrderItem> orderItems2 = orderItemMapper.map(getInput2());
        Receipt receipt2 = orderProcessor.processOrder(orderItems2);
        receiptTextPrinter.print(receipt2);

        List<OrderItem> orderItems3 = orderItemMapper.map(getInput3());
        Receipt receipt3 = orderProcessor.processOrder(orderItems3);
        receiptTextPrinter.print(receipt3);
    }

    private static List<InputItem> getInput1() {
        return List.of(
                new InputItem(new Product("book", ProductType.BOOK, 12.49, false), 1),
                new InputItem(new Product("music CD", ProductType.OTHER, 14.99, false), 1),
                new InputItem(new Product("chocolate bar", ProductType.FOOD, 0.85, false), 1));
    }

    private static List<InputItem> getInput2() {
        return List.of(
                new InputItem(new Product("imported box of chocolates", ProductType.FOOD, 10.00, true), 1),
                new InputItem(new Product("imported bottle of perfume", ProductType.OTHER, 47.50, true), 1));
    }

    private static List<InputItem> getInput3() {
        return List.of(
                new InputItem(new Product("imported bottle of perfume", ProductType.OTHER, 27.99, true), 1),
                new InputItem(new Product("bottle of perfume", ProductType.OTHER, 18.99, false), 1),
                new InputItem(new Product("packet of headache pills", ProductType.MEDICAL, 9.75, false), 1),
                new InputItem(new Product("box of imported chocolates", ProductType.FOOD, 11.25, true), 1));
    }
}
