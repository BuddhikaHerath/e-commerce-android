package com.example.my_store.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.my_store.Adapter.ProductAdapter;
import com.example.my_store.Clients.APICLIENT;
import com.example.my_store.Model.Product;
import com.example.my_store.R;
import com.example.my_store.Services.ProductService;

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
        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        products = new ArrayList<>();

        adapter = new ProductAdapter(products, this);
        recyclerView.setAdapter(adapter);
        loadRecylerViewData();

    }

    private void loadRecylerViewData() {


        ProductService productService = APICLIENT.getClient().create(ProductService.class);
        Call<List<Product>> call = productService.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
//                testresult.setText("code" +response.code());.
                    products = response.body();
                    adapter = new ProductAdapter(products, Product_List.this);
                    recyclerView.setAdapter(adapter);
                    loadRecylerViewData();
//                    products.addAll(products);
                    adapter.notifyDataSetChanged();
                } else {
//                    Log.d()
                    System.out.println(response);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t.getMessage());

            }


        });

    }
}


