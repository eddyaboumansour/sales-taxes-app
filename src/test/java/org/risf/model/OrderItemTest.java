package org.risf.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void testValidOrderItem() {
        OrderItem orderItem = new OrderItem(getProductTaxIncluded(), 2);

        assertNotNull(orderItem);
        assertEquals(2, orderItem.getOrderItemTotalTax());
        assertEquals(26.98, orderItem.getOrderItemTotalPriceTaxIncluded());
    }

    @Test
    void testOrderItemWithZeroQuantity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderItem(getProductTaxIncluded(), 0);
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

    private static ProductTaxIncluded getProductTaxIncluded() {
        return new ProductTaxIncluded(new Product("book", ProductType.BOOK, 12.49, false), 1.0);
    }
}