package com.example.my_store.dto;


import com.google.gson.annotations.SerializedName;

public class OrderProductDTO {
    @SerializedName("id")
     private long id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("ordersDTO")
    OrdersDTO ordersDTO;
    @SerializedName("productDTO")
    ProductDTO productDTO;

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

    public OrdersDTO getOrdersDTO() {
        return ordersDTO;
    }

    public void setOrdersDTO(OrdersDTO ordersDTO) {
        this.ordersDTO = ordersDTO;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }
}
