package org.risf.service;

import org.junit.jupiter.api.Test;
import org.risf.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderProcessorTest {

    private final OrderProcessor orderProcessor = new OrderProcessor();

    @Test
    void testEmptyOrder() {
        // Given
        List<OrderItem> emptyOrder = List.of();

        // When
        Receipt receipt = orderProcessor.processOrder(emptyOrder);

        // Then
        assertEquals(0, receipt.getOrderItems().size(), "Receipt should have no items.");
        assertEquals(0.0, receipt.getTotalTaxes());
        assertEquals(0.0, receipt.getTotalPrice());
    }

    @Test
    void testOneOrderItemWithoutTaxAdded() {
        // Given
        ProductTaxIncluded productTaxIncluded = new ProductTaxIncluded(new Product("chocolate bar", ProductType.FOOD, 0.85, false), 0);
        OrderItem orderItem = new OrderItem(productTaxIncluded, 1);
        List<OrderItem> orderItems = List.of(orderItem);

        // When
        Receipt receipt = orderProcessor.processOrder(orderItems);

        // Then
        assertEquals(1, receipt.getOrderItems().size(), "Receipt should have 1 item.");
        assertEquals(0.0, receipt.getTotalTaxes());
        assertEquals(0.85, receipt.getTotalPrice());
    }

    @Test
    void testOneOrderItemWithTaxAdded() {
        // Given
        ProductTaxIncluded productTaxIncluded = new ProductTaxIncluded(new Product("chocolate bar", ProductType.FOOD, 0.85, false), 1.5);
        OrderItem orderItem = new OrderItem(productTaxIncluded, 1);
        List<OrderItem> orderItems = List.of(orderItem);

        // When
        Receipt receipt = orderProcessor.processOrder(orderItems);

        // Then
        assertEquals(1, receipt.getOrderItems().size(), "Receipt should have 1 item.");
        assertEquals(1.5, receipt.getTotalTaxes());
        assertEquals(2.35, receipt.getTotalPrice());
    }

    @Test
    void testTwoOrderItemsWithTaxAdded() {
        // Given
        List<OrderItem> orderItems = getOrderItems();

        // When
        Receipt receipt = orderProcessor.processOrder(orderItems);

        // Then
        assertEquals(2, receipt.getOrderItems().size(), "Receipt should have 2 items.");
        assertEquals(2.0, receipt.getTotalTaxes());
        assertEquals(29.48, receipt.getTotalPrice());
    }

    private static List<OrderItem> getOrderItems() {
        ProductTaxIncluded productTaxIncluded1 = new ProductTaxIncluded(new Product("book", ProductType.BOOK, 12.49, false), 1.5);
        OrderItem orderItem1 = new OrderItem(productTaxIncluded1, 1);
        ProductTaxIncluded productTaxIncluded2 = new ProductTaxIncluded(new Product("music CD", ProductType.OTHER, 14.99, false), 0.5);
        OrderItem orderItem2 = new OrderItem(productTaxIncluded2, 1);
        return List.of(orderItem1, orderItem2);
    }
}