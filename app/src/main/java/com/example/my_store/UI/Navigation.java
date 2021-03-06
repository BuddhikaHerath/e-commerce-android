package com.example.my_store.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

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

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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


                    adapter = new ProductAdapter(products, Navigation.this);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences shared = getSharedPreferences("login",getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("username","");
            editor.putString("password","");
            editor.commit();

            Intent intent = new Intent(this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(Navigation.this, Navigation.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(Navigation.this, Cart.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

//        } else if (id == R.id.nav_tools) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
