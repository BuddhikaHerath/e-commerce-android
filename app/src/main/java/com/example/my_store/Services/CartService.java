package com.example.my_store.Services;

import android.support.design.widget.BottomSheetBehavior;

import com.example.my_store.Model.Orders;
import com.example.my_store.Model.OrderProducts;
import com.example.my_store.Model.User;
import com.example.my_store.dto.OrderProductDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CartService {
    @POST("api/cart")
    Call<OrderProducts> addToCart(@Body Orders newOrderProd);

    @POST("api/getcart")
    Call<List<OrderProductDTO>> getCart(@Body User user);

    @PUT("api/cart")
    Call<OrderProducts> updateCart(@Body Orders newOrder);

    @GET("api/cart/{id}/{status}")
    Call<Orders> updateCartStatus(@Path("id") Long id,@Path("status") String status);

}
