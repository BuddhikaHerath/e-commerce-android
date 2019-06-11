package com.example.my_store.dto;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersDTO {
    @SerializedName("id")
    private long id;
    @SerializedName("orderStatus")
    private String orderStatus;
    @SerializedName("userId")
    private long userId;
    @SerializedName("orderProductDTO")
    List<OrderProductDTO> orderProductDTO;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<OrderProductDTO> getOrderProductDTO() {
        return orderProductDTO;
    }

    public void setOrderProductDTO(List<OrderProductDTO> orderProductDTO) {
        this.orderProductDTO = orderProductDTO;
    }
}
