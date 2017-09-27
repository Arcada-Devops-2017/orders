package com.orders.devops.orders.controller;

public class OrdersRequestDTO {

    public String authToken;
    public String storeId;
    public String productId;
    public int productAmount;


    public String getAuthToken() {
        return authToken;
    }

    public String getStoreId() {
        return storeId;
    }
    public String getProductId() {
        return productId;
    }
    public int getProductAmount() {
        return productAmount;
    }


}
