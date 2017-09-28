package com.orders.devops.orders.controller;

import com.orders.devops.orders.model.Orders;
import com.orders.devops.orders.repository.OrdersJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private OrdersJpaRepository ordersJpaRespository;


    private Logger logger = Logger.getLogger(OrdersController.class.getName());

    @PostMapping(value = "/PostData")
    public OrdersResponseDTO PostData (@RequestBody final OrdersRequestDTO requestDTO){
        //System.out.println(requestDTO.getAuthToken());
        if(!requestDTO.getAuthToken().equals(null)){
            String[] AuthCheck = checkAuth(requestDTO.getAuthToken());
            System.out.println(AuthCheck[2]);
            String productCheck = checkProducts(requestDTO.getProductId());
            System.out.println(productCheck);


            if(!requestDTO.getProductId().equals(null) && !requestDTO.getStoreId().equals(null) && requestDTO.getProductAmount() != 0) {
                ordersJpaRespository.save(new Orders(AuthCheck[2],Long.parseLong(requestDTO.getProductId()),requestDTO.getProductAmount(),Long.parseLong(requestDTO.getStoreId()),Double.parseDouble(productCheck)));

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

    public String[] checkAuth(String authToken){
        //String uri = "auth.arcada.nitor.zone/userinfo.php";
        //The url where to get json from
        String uri = "https://people.arcada.fi/~santanej/test/test/auth.json";
        //What json is sent to the url
        String input = "{\"token\":\""+ authToken +"\"}";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(input, headers);

        ResponseEntity<AuthResponseDTO> response = restTemplate.exchange(uri, HttpMethod.POST, entity, AuthResponseDTO.class);

        //If request was successful
        if (response.getStatusCode().equals(HttpStatus.OK)){
            String[] returnArray = {response.getBody().getFirst_name(),response.getBody().getLast_name(),response.getBody().getUsername()};

            return returnArray;
        }else{
            //Something went wrong.... handle it
            String[] returnError = {"401"};
            return returnError;
        }

    }//checkAuth Ends.

    //ToDo test if json data fetching is correct
    public String checkProducts(String productId){
        //String uri = "http://product.arcada.nitor.zone/api/products.php?id=" + productId;

        String uri = "https://people.arcada.fi/~santanej/test/test/products.json";


        String input = "";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(input, headers);

        ResponseEntity<ProductResponseDTO> response = restTemplate.exchange(uri, HttpMethod.POST, entity, ProductResponseDTO.class);



        ///gets price from json
        String price = response.getBody().products.get(0).price;
        //System.out.println(response);
        if(Double.parseDouble(price) > 0){
            System.out.println("Found price for product");
            return String.valueOf(price);
        }else{
            return "401";

        }

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
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(input, headers);

        ResponseEntity<ProductResponseDTO> response = restTemplate.exchange(uri, HttpMethod.POST, entity, ProductResponseDTO.class);
    }*/
}
