package com.orders.devops.orders.repository;

import com.orders.devops.orders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrdersJpaRepository extends JpaRepository<Orders, Long> {
    Orders findByAuthToken(String authToken);
    //Orders setStoreId (String storeId);
    //Orders setProductId (String productId);
    //Orders setProductAmount (int productAmount);
}