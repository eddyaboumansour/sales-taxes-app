package org.risf.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void testValidOrderItem() {
        Product product = new Product("book", ProductType.BOOK, 12.49,false);
        OrderItem orderItem = new OrderItem(product, 1);

        assertNotNull(orderItem);
    }

    @Test
    void testOrderItemWithZeroQuantity() {
        Product product = new Product("book", ProductType.BOOK, 12.49,false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem(product, 0);
        });

        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    @Test
    void testOrderItemWithNullProduct() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem(null, 1);
        });

        assertEquals("Product cannot be null", exception.getMessage());
    }
}