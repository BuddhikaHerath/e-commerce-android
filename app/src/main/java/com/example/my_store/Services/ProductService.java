package com.example.my_store.Services;

import com.example.my_store.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET("products")
    Call<List<Product>> getProducts();

}
