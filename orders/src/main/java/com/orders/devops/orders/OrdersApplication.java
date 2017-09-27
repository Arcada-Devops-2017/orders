package com.orders.devops.orders;

import com.orders.devops.orders.model.Orders;
import com.orders.devops.orders.repository.OrdersJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OrdersApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =  SpringApplication.run(OrdersApplication.class, args);

        OrdersJpaRepository repository = ctx.getBean(OrdersJpaRepository.class);

        Orders order = new Orders();

        order.setProductId("1");
        order.setProductAmount(1);
        order.setAuthToken("foobar");


        repository.save(order);

    }
}
