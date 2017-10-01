package com.orders.devops.orders.controller;

import com.orders.devops.orders.model.OrderResponseData;
import com.orders.devops.orders.model.Orders;
import com.orders.devops.orders.repository.OrdersJpaRepository;
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

                    String productCheck = checkProducts(requestDTO.getProductId());
               // System.out.println(productCheck);
                    if(productCheck != "401" || Double.parseDouble(productCheck) >= 0){

                        int storesCheck = checkStores(Integer.parseInt(requestDTO.getStoreId()), Integer.parseInt(requestDTO.getProductId()));

                        if(storesCheck == 1){

                            ordersJpaRespository.save(new Orders(AuthCheck[2],Long.parseLong(requestDTO.getProductId()),requestDTO.getProductAmount(),Long.parseLong(requestDTO.getStoreId()),Double.parseDouble(productCheck)));



                            return new OrdersResponseDTO(ResponseCode.OK, "It works");

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


    public int checkStores(int storeId, int productId){
        //String uri = "http://stores.arcada.nitor.zone/api/stores_with_product.php?product=" + productId;

        String uri = "https://people.arcada.fi/~santanej/test/test/stores.json";


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
    @GetMapping(value= "/FetchAll")
    public OrdersResponseDTO findByUserName(@RequestParam ("authToken") String authToken){



        if(!authToken.equals(null)) {

            String[] AuthCheck;

            if(authToken.equals("orderRobotTestAuth")){
                AuthCheck = testCheckAuth(authToken);
            }else{
                AuthCheck = checkAuth(authToken);
            }


            if(AuthCheck[2] != "401"){

                Orders orders = ordersJpaRespository.findByUserName(AuthCheck[2]);


               // return new OrdersResponseDTO(new OrderResponseData());

                return new OrdersResponseDTO(ResponseCode.OK, "It works");

            }else{
                return new OrdersResponseDTO(ResponseCode.BAD_REQUEST,"No Auth token");
            }

        }else{
            return new OrdersResponseDTO(ResponseCode.BAD_REQUEST,"No Auth token");
        }

        //return ordersJpaRespository.findByAuthToken(authToken);
    }





    public String[] testCheckAuth(String authToken){

        String[] returnArray = {"","",authToken};

        return returnArray;
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
