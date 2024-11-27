package org.risf.model;

public record OrderItem(ProductTaxIncluded productTaxIncluded, int quantity) {
    public OrderItem {
        if (productTaxIncluded == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }

    public double getOrderItemTotalTax() {
        return productTaxIncluded().tax() * quantity;
    }

    public double getOrderItemTotalPriceTaxIncluded() {
        return productTaxIncluded().getPriceWithTax() * quantity;
    }
}
