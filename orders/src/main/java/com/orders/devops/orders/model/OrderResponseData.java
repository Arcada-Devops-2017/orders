package com.orders.devops.orders.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderResponseData{

    //public ArrayList<OrderData> orderData;
    public long orderId;
    public String orderDate;
    public long id;

    public double price;
    public int amount;
    public long storeId;

    //OrderData orderData;

    public OrderResponseData(long orderId, String orderDate, long id, double price, int amount, long storeId) {

        //orderData = new OrderData(orderId, orderDate, id, price, amount, storeId);

    }


    public String getOrderData() {
        return " hello";
    }



}
