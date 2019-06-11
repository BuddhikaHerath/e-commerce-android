package com.example.my_store.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;

import com.example.my_store.Adapter.ProductAdapter;
import com.example.my_store.Clients.APICLIENT;
import com.example.my_store.Model.Product;
import com.example.my_store.R;
import com.example.my_store.Services.ProductService;
import com.example.my_store.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();

        adapter = new ProductAdapter(products, this);
        recyclerView.setAdapter(adapter);
        loadRecylerViewData();

    }

    private void loadRecylerViewData() {


        ProductService productService = APICLIENT.getClient().create(ProductService.class);
        Call<List<ProductDTO>> call = productService.getProducts();

        call.enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                if (response.isSuccessful()) {

                    List<ProductDTO> productsDTOList = response.body();
                    products = new ArrayList<>();
                    for(ProductDTO prodDTO : productsDTOList){
                        Product prod = new Product();
                        prod.setId(prodDTO.getId());
                        prod.setCompany(prodDTO.getCompany());
                        prod.setDescription(prodDTO.getDescription());
                        System.err.println("prod mage "+prodDTO.getImage());
                        byte[] decodedString = Base64.decode(prodDTO.getImage(), Base64.DEFAULT);
                        prod.setImage(decodedString);
                        prod.setPrice(prodDTO.getPrice());
                        prod.setQuantity(prodDTO.getQuantity());
                        prod.setTitle(prodDTO.getTitle());

                        products.add(prod);
                    }


                    adapter = new ProductAdapter(products, Product_List.this);
                    recyclerView.setAdapter(adapter);
//                    System.out.println(" respon" +  response.body() );



//                    loadRecylerViewData();

                    //adapter.notifyDataSetChanged();
                } else {

                    System.out.println(response);
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                System.out.println(t.getMessage());

            }


        });

    }
}


