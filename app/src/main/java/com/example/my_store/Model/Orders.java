package com.example.my_store.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Orders {
    @SerializedName("id")
    private long id;
    @SerializedName("orderStatus")
    private String orderStatus;
    @SerializedName("user")
    private User user;
    @SerializedName("orderProducts")
    List<OrderProducts> cartOrders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }



    public List<OrderProducts> getCartOrders() {
        return cartOrders;
    }

    public void setCartOrders(List<OrderProducts> cartOrders) {
        this.cartOrders = cartOrders;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
