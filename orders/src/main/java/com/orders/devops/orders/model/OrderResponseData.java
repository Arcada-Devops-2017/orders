package com.orders.devops.orders.model;

import java.io.Serializable;
import java.util.List;

public class OrderResponseData implements Serializable {

    public List<OrderData> orderData;

    public OrderResponseData(String orderId) {

    }
}
