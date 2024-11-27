package org.risf.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Receipt {
    private double totalTaxes = 0.0;
    private double totalPrice = 0.0;
    private final List<OrderItem> orderItems = new ArrayList<>();

    public void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        totalTaxes += orderItem.getOrderItemTax();
        totalPrice += orderItem.getOrderItemTotalPriceTaxIncluded();
    }
}
