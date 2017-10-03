package com.orders.devops.orders.controller;

import com.orders.devops.orders.model.OrderResponseData;


public class OrdersResponseDTO  {
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
