package org.risf.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputItemTest {

    @Test
    void testValidOrderItem() {
        Product product = new Product("book", ProductType.BOOK, 12.49, false);
        InputItem inputItem = new InputItem(product, 1);

        assertNotNull(inputItem);
    }

    @Test
    void testOrderItemWithZeroQuantity() {
        Product product = new Product("book", ProductType.BOOK, 12.49, false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new InputItem(product, 0);
        });

        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    @Test
    void testOrderItemWithNullProduct() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new InputItem(null, 1);
        });

        assertEquals("Product cannot be null", exception.getMessage());
    }

}