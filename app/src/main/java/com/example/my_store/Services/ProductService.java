package com.example.my_store.Services;

import com.example.my_store.Model.Product;
import com.example.my_store.dto.ProductDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {

    @GET("api/products")
    Call<List<ProductDTO>> getProducts();

    @GET("api/products/{id}")
    Call<ProductDTO> getProductById(@Path(value = "id") Long productID);

    @GET("api/products/qtyavailable/{id}/{qty}")
    Call<ResponseBody> getQty(@Path(value = "id") Long id,@Path(value = "qty") int qty);

}
