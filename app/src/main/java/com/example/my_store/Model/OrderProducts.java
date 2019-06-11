package com.example.my_store.Model;

import com.google.gson.annotations.SerializedName;

public class OrderProducts {
    @SerializedName("id")
    private long id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("orders")
    private Orders orders;
    @SerializedName("product")
    private Product product;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
