package org.risf.service;

import org.risf.model.InputItem;
import org.risf.model.OrderItem;
import org.risf.model.ProductTaxIncluded;

import java.util.ArrayList;
import java.util.List;

public class OrderItemMapper {

    private final TaxCalculationStrategy taxCalculationStrategy;

    public OrderItemMapper(TaxCalculationStrategy taxCalculationStrategy) {
        this.taxCalculationStrategy = taxCalculationStrategy;
    }

    public List<OrderItem> map(List<InputItem> inputItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (InputItem inputItem : inputItems) {
            ProductTaxIncluded productTaxIncluded = taxCalculationStrategy.addTaxesOnProduct(inputItem.product());
            orderItems.add(new OrderItem(productTaxIncluded, inputItem.quantity()));
        }
        return orderItems;
    }
}
