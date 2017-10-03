package com.orders.devops.orders.model;



import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Orders {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long orderId;
    @Column
    private long productId;
    @Column
    private int productAmount;
    @Column
    private long storeId;
    @Column
    private String authToken;
    @Column
    private double orderPrice;
    @Column
    private boolean userConfirmed;
    @Column
    private String userName;
    @Column
    private String orderDate;


    public long getOrderId() { return orderId; }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public boolean isUserConfirmed() {
        return userConfirmed;
    }

    public void setUserConfirmed(boolean userConfirmed) {
        this.userConfirmed = userConfirmed;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setOrderDate(){
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.orderDate =  sdf.format(date);
    };
    public String getOrderDate(){return orderDate;}

}
