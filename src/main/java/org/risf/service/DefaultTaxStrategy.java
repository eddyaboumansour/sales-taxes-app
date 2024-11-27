package org.risf.service;

import org.risf.model.OrderItem;
import org.risf.model.ProductType;

public class DefaultTaxStrategy implements TaxCalculationStrategy {

    public static final double INITIAL_TAX_RATE = 0.0;
    private static final double ROUNDING_FACTOR = 20.0;
    private static final double BASIC_TAX_RATE = 0.10;
    public static final double IMPORT_DUTY = 0.05;

    @Override
    public double calculateTax(OrderItem item) {
        double taxRate = INITIAL_TAX_RATE;

        taxRate = addBasicTaxIfExists(item, taxRate);
        taxRate = addImportDutyIfExists(item, taxRate);

        double tax = calculateItemTax(item, taxRate);
        return roundToNearest(tax);
    }

    private static double calculateItemTax(OrderItem item, double taxRate) {
        return taxRate * item.product().price();
    }

    private static double addBasicTaxIfExists(OrderItem item, double taxRate) {
        if (item.product().type() == ProductType.OTHER) {
            taxRate += BASIC_TAX_RATE;
        }
        return taxRate;
    }

    private static double addImportDutyIfExists(OrderItem item, double taxRate) {
        if (item.product().isImported()) {
            taxRate += IMPORT_DUTY;
        }
        return taxRate;
    }

    private double roundToNearest(double value) {
        return Math.ceil(value * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }
}
