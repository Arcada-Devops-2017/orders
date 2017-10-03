package com.orders.devops.orders.model;

public class Product {
        public long productId;
        public long storeId;
        public int productAmount;
        public double orderPrice;


        Product(long productId, double price, int productAmount, long storeId){
                this.productId = productId;
                this.orderPrice = price;
                this.productAmount = productAmount;
                this.storeId = storeId;
        }


        public long getProductId() {
                return productId;
        }

        public long getStoreId() {
                return storeId;
        }

        public int getProductAmount() {
                return productAmount;
        }

        public double getOrderPrice() {
                return orderPrice;
        }

}
