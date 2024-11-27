package org.risf.service;

import org.risf.model.Product;
import org.risf.model.ProductTaxIncluded;

public interface TaxCalculationStrategy {
     ProductTaxIncluded addTaxesOnProduct(Product product);
}
