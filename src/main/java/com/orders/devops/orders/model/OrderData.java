package com.orders.devops.orders.model;

import java.util.List;

public class OrderData {

        public long orderId;
        public String orderDate;
        public long id;
        public double price;
        public int amount;
        public long storeId;

        //public List<Product> product;
        Product products;

        public OrderData(long orderId, String orderDate, long id, double price, int amount, long storeId){
                //product.add(id, storeId, amount, price);
                //products = new Product(id, price, amount, storeId);

                this.orderId = orderId;
                this.orderDate = orderDate;
                this.id = id;
                this.price = price;
                this.amount = amount;
                this.storeId = storeId;

        }


        public Product getProducts() {
                return products;
        }




}
