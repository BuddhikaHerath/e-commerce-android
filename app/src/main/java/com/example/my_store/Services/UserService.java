package com.example.my_store.Services;

import com.example.my_store.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @POST("user/name")
    Call<List<User>> userLogin(@Body User user);
}
