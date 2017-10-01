package com.orders.devops.orders.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;
    private long productId;
    private int productAmount;
    private long storeId;
    private String authToken;
    private double orderPrice;
    private boolean userConfirmed;
    private String userName;
    private String orderDate;




    protected Orders(){}

    public Orders(String userName,long productId, int productAmount,long storeId,double orderPrice){

    }


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
