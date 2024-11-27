package org.risf.model;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void testProductWithEmptyName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("", ProductType.BOOK, 2.85, false);
        });

        assertEquals("Product name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testProductWithNegativePrice() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("book", ProductType.BOOK, -2.85, false);
        });

        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    void testProductWithNullType() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("book", null, 2.85, false);
        });

        assertEquals("Product type cannot be null", exception.getMessage());
    }

}