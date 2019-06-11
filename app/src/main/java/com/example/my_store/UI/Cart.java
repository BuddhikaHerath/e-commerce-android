package com.example.my_store.UI;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_store.Adapter.CartAdapter;
import com.example.my_store.Clients.APICLIENT;
import com.example.my_store.Model.OrderProducts;
import com.example.my_store.Model.Orders;
import com.example.my_store.Model.Product;
import com.example.my_store.Model.User;
import com.example.my_store.R;
import com.example.my_store.Services.CartService;
import com.example.my_store.dto.OrderProductDTO;
import com.example.my_store.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<OrderProducts> orderProducts;
    private TextView lblTotal;
    private Button purchaseBtn;
    private long orderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Shopping Cart");

        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderProducts = new ArrayList<>();

        lblTotal = findViewById(R.id.textViewtotal);
        purchaseOrder();

        loadRecylerViewData();

    }

    private void purchaseOrder() {
        purchaseBtn = findViewById(R.id.purchase);
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shared = getSharedPreferences("login",getApplicationContext().MODE_PRIVATE);
                User user = new User();
                user.setUsername(shared.getString("username",""));
                user.setPassword(shared.getString("password",""));

                CartService cartService = APICLIENT.getClientWithAuth(user.getUsername(),user.getPassword()).create(CartService.class);
                Call<Orders> call = cartService.updateCartStatus(orderId,"complete");

                call.enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                        System.err.println("Order purchase response");
                        if (response.isSuccessful()) {
                            System.err.println("Order purchase complete");
                            Toast.makeText(getBaseContext(),"Order Purchase Successful!",Toast.LENGTH_LONG);
                            Cart.this.finish();
                        } else {
                            System.err.println("Order purchase un-complete");
                        }
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        System.err.println("order purchase"+t.getMessage());
                    }


                });
            }
        });
    }

    private void loadRecylerViewData() {
        SharedPreferences sharedPreferences = getSharedPreferences("login",getApplicationContext().MODE_PRIVATE);
        User user = new User();
        user.setUsername(sharedPreferences.getString("username",""));
        user.setPassword(sharedPreferences.getString("password",""));

        CartService cartService = APICLIENT.getClientWithAuth(user.getUsername(),user.getPassword()).create(CartService.class);
        Call<List<OrderProductDTO>> call = cartService.getCart(user);

        call.enqueue(new Callback<List<OrderProductDTO>>() {
            @Override
            public void onResponse(Call<List<OrderProductDTO>> call, Response<List<OrderProductDTO>> response) {
                System.err.println("Get cart reponse sucess");
                if (response.isSuccessful()) {
                    System.err.println("Get cart sucess");
                    List<OrderProductDTO> orderProductDTOList = response.body();
                    orderProducts = new ArrayList<>();
                    System.err.println("order prod dto "+orderProductDTOList.size());
                    for(OrderProductDTO orderProdDTO : orderProductDTOList){
                        OrderProducts orderProd = new OrderProducts();
                        orderProd.setId(orderProdDTO.getId());
                        orderProd.setQuantity(orderProdDTO.getQuantity());

                        ProductDTO prodDTO = orderProdDTO.getProductDTO();
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

                        orderProd.setProduct(prod);

                        orderProducts.add(orderProd);
                        orderId = orderProdDTO.getOrdersDTO().getId();

                    }
                    System.err.println("Get cart sucess "+orderProducts.size());
                    adapter = new CartAdapter(orderProducts, getApplicationContext(),lblTotal);
                    recyclerView.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                    calc();

                } else {
//                    Log.d()
                    System.out.println(response);
                }
            }

            @Override
            public void onFailure(Call<List<OrderProductDTO>> call, Throwable t) {
                System.out.println("Cart un-success"+t.getMessage());

            }


        });

    }


    private void calc() {

        double total = 0;


        for (OrderProducts orderProduct : orderProducts) {
            double price = orderProduct.getProduct().getPrice();
            int qty = orderProduct.getQuantity();

            total+= price*qty;
        }

        lblTotal.setText(String.valueOf(total));
    }

}
