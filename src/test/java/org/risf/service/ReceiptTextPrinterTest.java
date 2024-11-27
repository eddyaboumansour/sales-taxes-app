package org.risf.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.risf.model.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceiptTextPrinterTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalSystemOut;
    @Mock
    private Receipt receipt;
    @InjectMocks
    private ReceiptTextPrinter receiptTextPrinter;

    @BeforeEach
    void setUp() {
        originalSystemOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testEmptyReceipt() {
        // Given
        when(receipt.getOrderItems()).thenReturn(List.of());
        when(receipt.getTotalTaxes()).thenReturn(0.0);
        when(receipt.getTotalPrice()).thenReturn(0.0);

        // When
        receiptTextPrinter.print(receipt);

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("Sales Taxes: 0.00"));
        assertTrue(output.contains("Total: 0.00"));
    }

    @Test
    void testPrintReceipt() {
        // Given
        OrderItem item1 = new OrderItem(new ProductTaxIncluded(new Product("book", ProductType.BOOK, 12.49, false), 0), 1);
        OrderItem item2 = new OrderItem(new ProductTaxIncluded(new Product("music CD", ProductType.OTHER, 14.99, false), 0), 2);
        when(receipt.getOrderItems()).thenReturn(List.of(item1, item2));
        when(receipt.getTotalTaxes()).thenReturn(1.50);
        when(receipt.getTotalPrice()).thenReturn(29.83);

        // When
        receiptTextPrinter.print(receipt);

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("1 book: 12.49"));
        assertTrue(output.contains("2 music CD: 14.99"));
        assertTrue(output.contains("Sales Taxes: 1.50"));
        assertTrue(output.contains("Total: 29.83"));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalSystemOut);
    }

}