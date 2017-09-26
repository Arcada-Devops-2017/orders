package com.orders.devops.orders.controller;

import com.orders.devops.orders.model.Orders;
import com.orders.devops.orders.repository.OrdersJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersJpaRepository ordersJpaRespository;

    @GetMapping(value ="/all")
    public List<Orders> findAll(){
        return ordersJpaRespository.findAll();
    }

    //Search from database information
    @GetMapping(value= "/{authToken}")
    public Orders findByAuthToken(@PathVariable final String authToken){

        return ordersJpaRespository.findByAuthToken(authToken);
    }
    @PostMapping(value = "/load")
    public Orders load(@RequestBody final Orders orders){
        ordersJpaRespository.save(orders);
        return ordersJpaRespository.findByAuthToken(orders.getAuthToken());

    }
    @RequestMapping(path = "/place", method = RequestMethod.POST)
    public OrdersResponseDTO ordersApi(@RequestBody OrdersRequestDTO request){

        if(request.getAuthToken() == null){
            return new OrdersResponseDTO(ResponseCode.BAD_REQUEST, "Invalid authToken");
        }

        return new OrdersResponseDTO(ResponseCode.OK);
    }
}
