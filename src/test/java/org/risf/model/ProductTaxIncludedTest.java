package org.risf.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTaxIncludedTest {

    @Test
    void testValidProductTaxIncluded() {
        ProductTaxIncluded productTaxIncluded = new ProductTaxIncluded(getProduct(), 1);

        assertNotNull(productTaxIncluded);
        assertEquals(13.49, productTaxIncluded.getPriceWithTax());
    }

    @Test
    void testProductTaxIncludedWithNegativeTax() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ProductTaxIncluded(getProduct(), -1.0);
        });

        assertEquals("Tax cannot be negative", exception.getMessage());
    }

    @Test
    void testProductTaxIncludedWithNullProduct() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ProductTaxIncluded(null, 1);
        });

        assertEquals("Product cannot be null", exception.getMessage());
    }

    private static Product getProduct() {
        return new Product("book", ProductType.BOOK, 12.49, false);
    }
}