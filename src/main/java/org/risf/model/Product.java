package org.risf.model;

public record Product(String name, ProductType type, double price, boolean isImported) {
    public Product {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Product type cannot be null");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}
