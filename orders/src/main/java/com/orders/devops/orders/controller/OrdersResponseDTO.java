package com.orders.devops.orders.controller;

public class OrdersResponseDTO {
    public ResponseCode code;
    public String message;

    public OrdersResponseDTO(ResponseCode code){
        this.code = code;
    }

    public OrdersResponseDTO(ResponseCode code, String message){
        this.code = code;
        this.message = message;
    }
}
