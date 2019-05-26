package com.example.my_store.Model;

import com.google.gson.annotations.SerializedName;


public class Product {

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

    @SerializedName("company")
    private String company;


    public Product(String description, String title) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.company = company;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
