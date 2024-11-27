package org.risf.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.risf.model.OrderItem;
import org.risf.model.Product;
import org.risf.model.ProductType;
import org.risf.model.Receipt;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderProcessorTest {

    @Mock
    private TaxCalculationStrategy taxCalculationStrategy;

    @InjectMocks
    private OrderProcessor orderProcessor;

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
        OrderItem chocolateBar = new OrderItem(new Product("chocolate bar", ProductType.FOOD, 0.85, false), 1);
        List<OrderItem> orderItems = List.of(chocolateBar);

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
        OrderItem chocolateBar = new OrderItem(new Product("chocolate bar", ProductType.FOOD, 0.85, false), 1);
        List<OrderItem> orderItems = List.of(chocolateBar);
        when(taxCalculationStrategy.calculateTax(chocolateBar)).thenReturn(1.5);

        // When
        Receipt receipt = orderProcessor.processOrder(orderItems);

        // Then
        assertEquals(1, receipt.getOrderItems().size(), "Receipt should have 1 item.");
        assertEquals(1.5, receipt.getTotalTaxes());
        assertEquals(2.35, receipt.getTotalPrice());
    }

    @Test
    void testTwoOrderItemsWithAndWithoutTaxAdded() {
        // Given
        OrderItem book = new OrderItem(new Product("book", ProductType.BOOK, 12.49, false), 1);
        OrderItem musicCd = new OrderItem(new Product("music CD", ProductType.OTHER, 14.99, false), 1);
        List<OrderItem> orderItems = List.of(book, musicCd);
        when(taxCalculationStrategy.calculateTax(book)).thenReturn(1.5);
        when(taxCalculationStrategy.calculateTax(musicCd)).thenReturn(0.0);

        // When
        Receipt receipt = orderProcessor.processOrder(orderItems);

        // Then
        assertEquals(2, receipt.getOrderItems().size(), "Receipt should have 2 items.");
        assertEquals(1.5, receipt.getTotalTaxes());
        assertEquals(28.98, receipt.getTotalPrice());
    }
}