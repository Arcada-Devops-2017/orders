package com.orders.devops.orders.controller;

import com.orders.devops.orders.model.Orders;
import com.orders.devops.orders.repository.OrdersJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private OrdersJpaRepository ordersJpaRespository;

    @PostMapping(value = "/PostData")
    public OrdersResponseDTO PostData (@RequestBody final OrdersRequestDTO requestDTO){
        System.out.println(requestDTO.getAuthToken());
        if(!requestDTO.getAuthToken().equals(null)){
            checkAuth(requestDTO.getAuthToken());
            if(!requestDTO.getProductId().equals(null) && !requestDTO.getStoreId().equals(null) && requestDTO.getProductAmount() != 0) {
                //ordersJpaRespository.setProductId(requestDTO.getProductId());
                //ordersJpaRespository.setStoreId(requestDTO.getStoreId());
                //ordersJpaRespository.setProductAmount(requestDTO.getProductAmount());

                return new OrdersResponseDTO(ResponseCode.OK);
            }
            else{
                return new OrdersResponseDTO(ResponseCode.BAD_REQUEST, "ID, StoreID or Amount was not set");

            }

        }
        else{
            return new OrdersResponseDTO(ResponseCode.BAD_REQUEST);

        }

    }

    public void checkAuth(String authToken){
        //String uri = "auth.arcada.nitor.zone/userinfo.php";
        String uri = "people.arcada.fi/~santanej/test/test/auth.json";
        String input = "{\"token\":\""+ authToken +"\"}";
        HttpEntity<String> entity = new HttpEntity<String>(input, null);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        System.out.println(response);


    }


    /*@GetMapping(value ="/all")
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
    }*/
}
