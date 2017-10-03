package com.orders.devops.orders.controller;

import com.orders.devops.orders.model.OrderData;
import com.orders.devops.orders.model.OrderResponseData;

import java.util.ArrayList;
import java.util.List;

public class FrontEndResponseDTO {

    public ResponseCode code;
    public String message;
    public String s1;
    //public String s2 = "<<<<<<<<";
    //public List<OrderData> orderData;
    //List<String> l1;

    public ArrayList<OrderData> orderData;


    public FrontEndResponseDTO(ResponseCode code, String message, String s1){
        this.code = code;
        this.message = message;
        //this.hello = s1.getOrderData();
        this.s1 = s1;
        orderData.add(0,new OrderData(12,"1.1.2017",12,12,12,12));

        //addData();

    }


}
