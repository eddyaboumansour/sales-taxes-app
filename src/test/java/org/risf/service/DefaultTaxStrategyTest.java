package org.risf.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.risf.model.OrderItem;
import org.risf.model.Product;
import org.risf.model.ProductType;

class DefaultTaxStrategyTest {
    private TaxCalculationStrategy taxCalculationStrategy;

    @BeforeEach
    void setUp() {
        taxCalculationStrategy = new DefaultTaxStrategy();
    }

    @ParameterizedTest
    @CsvSource({
            "'book', 'BOOK', 12.49, 0.00",
            "'chocolate bar', 'FOOD', 0.85, 0.00",
            "'headache pills', 'MEDICAL', 9.75, 0.00"
    })
    void testExemptNonImportedItems(String productName, String productType, double price, double expectedTax) {
        // Given
        ProductType type = ProductType.valueOf(productType);
        Product product = new Product(productName, type, price, false);
        OrderItem orderItem = new OrderItem(product, 1);

        // When
        double tax = taxCalculationStrategy.calculateTax(orderItem);

        // Then
        Assertions.assertEquals(expectedTax, tax);
    }

    @ParameterizedTest
    @CsvSource({
            "'music CD', 14.99, 1.50",
            "'bottle of perfume', 18.99, 1.90"
    })
    void testTaxableNonImportedItems(String productName, double price, double expectedTax) {
        // Given
        Product product = new Product(productName, ProductType.OTHER, price, false);
        OrderItem orderItem = new OrderItem(product, 1);

        // When
        double tax = taxCalculationStrategy.calculateTax(orderItem);

        // Then
        Assertions.assertEquals(expectedTax, tax);
    }

    @ParameterizedTest
    @CsvSource({
            "'imported bottle of perfume', 47.50, 2.4",
            "'imported music CD', 14.99, 0.75"
    })
    void testExemptImportedItems(String productName, double price, double expectedTax) {
        // Given
        Product product = new Product(productName, ProductType.FOOD, price, true);
        OrderItem orderItem = new OrderItem(product, 1);

        // When
        double tax = taxCalculationStrategy.calculateTax(orderItem);

        // Then
        Assertions.assertEquals(expectedTax, tax, 0.01);
    }

    @ParameterizedTest
    @CsvSource({
            "'imported book',12.49, 1.90",
            "'imported chocolate bar', 0.85, 0.15"
    })
    void testTaxableImportedItems(String productName, double price, double expectedTax) {
        // Given
        Product product = new Product(productName, ProductType.OTHER, price, true);
        OrderItem orderItem = new OrderItem(product, 1);

        // when
        double tax = taxCalculationStrategy.calculateTax(orderItem);

        // Then
        Assertions.assertEquals(expectedTax, tax);
    }
}