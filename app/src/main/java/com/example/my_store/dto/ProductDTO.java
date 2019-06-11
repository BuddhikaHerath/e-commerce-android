package com.example.my_store.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDTO {
    @SerializedName("id")
    private long id;
    @SerializedName("description")
    private String description;
    @SerializedName("title")
    private String title;
    @SerializedName("quantity")
     private int quantity;
    @SerializedName("price")
    private double price;
    @SerializedName("image")
    private String image;
    @SerializedName("imagePath")
    private String imagePath;
    @SerializedName("company")
   private String company;
    @SerializedName("orderProductDTOList")
    private List<OrderProductDTO> orderProductDTOList;

    public ProductDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<OrderProductDTO> getOrderProductDTOList() {
        return orderProductDTOList;
    }

    public void setOrderProductDTOList(List<OrderProductDTO> orderProductDTOList) {
        this.orderProductDTOList = orderProductDTOList;
    }
}
