package org.risf.service;

import org.risf.model.Product;
import org.risf.model.ProductTaxIncluded;
import org.risf.model.ProductType;

public class DefaultTaxStrategy implements TaxCalculationStrategy {

    public static final double INITIAL_TAX_RATE = 0.0;
    private static final double ROUNDING_FACTOR = 20.0;
    private static final double BASIC_TAX_RATE = 0.10;
    public static final double IMPORT_DUTY = 0.05;

    @Override
    public ProductTaxIncluded addTaxesOnProduct(Product product) {
        return new ProductTaxIncluded(product, calculateTax(product));

    }

    private double calculateTax(Product product) {
        double taxRate = INITIAL_TAX_RATE;

        taxRate = addBasicTaxIfExists(product, taxRate);
        taxRate = addImportDutyIfExists(product, taxRate);

        double tax = calculateItemTax(product, taxRate);
        return roundToNearest(tax);
    }

    private static double calculateItemTax(Product product, double taxRate) {
        return taxRate * product.price();
    }

    private static double addBasicTaxIfExists(Product product, double taxRate) {
        if (product.type() == ProductType.OTHER) {
            taxRate += BASIC_TAX_RATE;
        }
        return taxRate;
    }

    private static double addImportDutyIfExists(Product product, double taxRate) {
        if (product.isImported()) {
            taxRate += IMPORT_DUTY;
        }
        return taxRate;
    }

    private double roundToNearest(double value) {
        return Math.ceil(value * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }
}
