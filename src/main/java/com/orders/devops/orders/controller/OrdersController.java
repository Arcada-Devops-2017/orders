package com.orders.devops.orders.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.orders.devops.orders.model.OrderData;
import com.orders.devops.orders.model.OrderResponseData;
import com.orders.devops.orders.model.Orders;
import com.orders.devops.orders.repository.OrdersJpaRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private OrdersJpaRepository ordersJpaRespository;


    private Logger logger = Logger.getLogger(OrdersController.class.getName());


    @CrossOrigin(origins ="*")
    @PostMapping(value = "/PostData")
    public OrdersResponseDTO PostData (@RequestBody final OrdersRequestDTO requestDTO){
        //System.out.println(requestDTO.getAuthToken());
        if(!requestDTO.getAuthToken().equals(null)){
            String[] AuthCheck = checkAuth(requestDTO.getAuthToken());
           // System.out.println(AuthCheck[2]);
            if(AuthCheck[2] != "401"){

                if(!requestDTO.getProductId().equals(null) && !requestDTO.getStoreId().equals(null) && requestDTO.getProductAmount() != 0) {

                    String productCheck = checkProducts(requestDTO.getProductId(), requestDTO.getAuthToken());
               // System.out.println(productCheck);
                    if(productCheck != "401" || Double.parseDouble(productCheck) >= 0){

                        int storesCheck = checkStores(Integer.parseInt(requestDTO.getStoreId()), Integer.parseInt(requestDTO.getProductId()), requestDTO.getAuthToken());

                        if(storesCheck == 1){

                            if(requestDTO.getAuthToken().equals("orderRobotTestAuth")){
                                Orders order = new Orders();
                                order.setAuthToken("orderRobotTestAuth");
                                order.setUserName(AuthCheck[2]);
                                order.setProductId(Long.parseLong(requestDTO.getProductId()));
                                order.setProductAmount(requestDTO.getProductAmount());
                                order.setStoreId(Long.parseLong(requestDTO.getStoreId()));
                                order.setUserConfirmed(true);
                                order.setOrderDate();
                                ordersJpaRespository.save(order);

                                return new OrdersResponseDTO(ResponseCode.OK, "Username: " + AuthCheck[2]+ ", ProductId: " + Long.parseLong(requestDTO.getProductId()) + ", ProductAmount: " + requestDTO.getProductAmount() + ", StoreId: " + Long.parseLong(requestDTO.getStoreId()) + ", ProductPrice: " +Double.parseDouble(productCheck));

                            }else{
                                Orders order = new Orders();
                                order.setUserName(AuthCheck[2]);
                                order.setProductId(Long.parseLong(requestDTO.getProductId()));
                                order.setProductAmount(requestDTO.getProductAmount());
                                order.setStoreId(Long.parseLong(requestDTO.getStoreId()));
                                order.setUserConfirmed(true);
                                order.setOrderDate();
                                ordersJpaRespository.save(order);


                                return new OrdersResponseDTO(ResponseCode.OK, "Username: " + AuthCheck[2]+ ", ProductId: " + Long.parseLong(requestDTO.getProductId()) + ", ProductAmount: " + requestDTO.getProductAmount() + ", StoreId: " + Long.parseLong(requestDTO.getStoreId()) + ", ProductPrice: " +Double.parseDouble(productCheck));

                            }

                        }else{
                            return new OrdersResponseDTO(ResponseCode.BAD_REQUEST, "That product can't be found in that store!");
                        }

                    }else{
                        return new OrdersResponseDTO(ResponseCode.BAD_REQUEST, "Price was not found");
                    }

                }else{
                    return new OrdersResponseDTO(ResponseCode.BAD_REQUEST, "ID, StoreID or Amount was not set");

                }

            }else{
                return new OrdersResponseDTO(ResponseCode.BAD_REQUEST, "Auth token was not recognised");
            }

        }
        else{
            return new OrdersResponseDTO(ResponseCode.BAD_REQUEST,"No Auth token");

        }
    }

    public String[] checkAuth(String authToken){
        //The url where to get json from
        String uri;

        if(authToken.equals("orderRobotTestAuth")){
            //System.out.println("hello");
            uri = "https://people.arcada.fi/~santanej/test/test/auth.json";
        }else{
            uri = "auth.arcada.nitor.zone/userinfo.php";
            //uri = "https://people.arcada.fi/~santanej/test/test/auth.json";
        }

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
    public String checkProducts(String productId, String authToken){

        String uri;

        if(authToken.equals("orderRobotTestAuth")){
            //System.out.println("World");
            uri = "https://people.arcada.fi/~santanej/test/test/products.json";
        }else{
            uri = "http://product.arcada.nitor.zone/api/products.php?id=" + productId;
            //uri = "https://people.arcada.fi/~santanej/test/test/products.json";
        }


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


    public int checkStores(int storeId, int productId, String authToken){
        String uri;

        if(authToken.equals("orderRobotTestAuth")){
            //System.out.println("!!!!");
            uri = "https://people.arcada.fi/~santanej/test/test/stores.json";
        }else{
            uri = "http://stores.arcada.nitor.zone/api/stores_with_product.php?product=" + productId;
            //uri = "https://people.arcada.fi/~santanej/test/test/stores.json";
        }


        String input = "";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(input, headers);

        ResponseEntity<StoresResponseDTO> response = restTemplate.exchange(uri, HttpMethod.POST, entity, StoresResponseDTO.class);

        int stores;


        for(int i = 0; i < response.getBody().stores.size(); i++){
            stores = response.getBody().stores.get(i).id;

            if(stores == storeId){
                System.out.println("The store has that product");
                return 1;
            }
        }

        //stores = response.getBody().stores.get(storeId).id;
        //System.out.println(stores);

        return 0;

    }


    //This section is for sending data

    //Search from database information
    @CrossOrigin(origins = "*")
    @GetMapping(value= "/FetchAll", produces = "application/json")
    public String findByUserName(@RequestParam ("authToken") String authToken){

        if(!authToken.equals(null)) {

            String[] AuthCheck = checkAuth(authToken);


            if(AuthCheck[2] != "401"){

                //Orders orders = ordersJpaRespository.findByUserName(AuthCheck[2]);
                //System.out.println(orders);

               // return new OrdersResponseDTO(new OrderResponseData());
                //new OrderData(12, "01.02.2017", 99, 2.0, 10, 88);

                String message = "";
                JSONObject json = new JSONObject();
                JSONArray array = new JSONArray();
                JSONObject item = new JSONObject();
                JSONObject product = new JSONObject();

                try {
                    item.put("orderId", 2);
                    item.put("orderDate", "12.12.2017");


                    product.put("id", 15);
                    product.put("storeId", 99);
                    product.put("amount", 900);
                    product.put("price", 20);
                    item.put("product", product);

                    array.put(item);

                    json.put("orderData", array);
                    message = json.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return message;
                //return new FrontEndResponseDTO(ResponseCode.OK, message , "hello World!!");

            }else{
                return "Could not find the username";
                //return new FrontEndResponseDTO(ResponseCode.BAD_REQUEST,"No Auth token", null);
            }

        }else{
            return "No auth token";
            //return new FrontEndResponseDTO(ResponseCode.BAD_REQUEST,"No Auth token", null);
        }

        //return ordersJpaRespository.findByAuthToken(authToken);
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
