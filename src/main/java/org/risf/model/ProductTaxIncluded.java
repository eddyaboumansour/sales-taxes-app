package org.risf.model;

public record ProductTaxIncluded(Product product, double tax) {
    public ProductTaxIncluded {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (tax < 0) {
            throw new IllegalArgumentException("Tax cannot be negative");
        }
    }

    public double getPriceWithTax() {
        return product().price() + tax;
    }
}
