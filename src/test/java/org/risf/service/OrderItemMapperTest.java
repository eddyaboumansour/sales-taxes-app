package org.risf.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.risf.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemMapperTest {
    @Mock
    private TaxCalculationStrategy taxCalculationStrategy;

    @InjectMocks
    private OrderItemMapper orderItemMapper;

    @Test
    void testMapValidInputItems() {
        // Given
        Product product1 = new Product("Test Product 1", ProductType.OTHER, 100.0, false);
        ProductTaxIncluded productTaxIncluded1 = new ProductTaxIncluded(product1, 10.0);
        when(taxCalculationStrategy.addTaxesOnProduct(product1)).thenReturn(productTaxIncluded1);
        InputItem inputItem1 = new InputItem(product1, 2);

        Product product2 = new Product("Test Product 2", ProductType.OTHER, 100.0, false);
        ProductTaxIncluded productTaxIncluded2 = new ProductTaxIncluded(product2, 10.0);
        when(taxCalculationStrategy.addTaxesOnProduct(product2)).thenReturn(productTaxIncluded2);
        InputItem inputItem2 = new InputItem(product2, 1);

        // When
        List<OrderItem> result = orderItemMapper.map(List.of(inputItem1, inputItem2));

        // Then
        assertEquals(2, result.size());
        verifyProduct(result, 0, 2, productTaxIncluded1, product1);
        verifyProduct(result, 1, 1, productTaxIncluded2, product2);
    }

    private void verifyProduct(List<OrderItem> result, int index, int index1, ProductTaxIncluded productTaxIncluded2, Product product2) {
        OrderItem orderItem2 = result.get(index);
        assertEquals(productTaxIncluded2, orderItem2.productTaxIncluded());
        assertEquals(index1, orderItem2.quantity());
        verify(taxCalculationStrategy, times(1)).addTaxesOnProduct(product2);
    }

    @Test
    void testMapEmptyInputItems() {
        // Given
        List<OrderItem> result = orderItemMapper.map(List.of());

        // When Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testMapNullInputItem() {
        // Given
        List<InputItem> inputItems = new ArrayList<>();
        inputItems.add(null);

        // When Then
        assertThrows(NullPointerException.class, () -> orderItemMapper.map(inputItems));
    }

    @Test
    void testMap_TaxCalculationStrategyReturnsNull() {
        // Given
        Product product = new Product("Test Product", ProductType.OTHER, 100.0, false);
        InputItem inputItem = new InputItem(product, 2);
        when(taxCalculationStrategy.addTaxesOnProduct(product)).thenReturn(null);

        // When Then
        assertThrows(IllegalArgumentException.class, () -> orderItemMapper.map(List.of(inputItem)));
    }
}