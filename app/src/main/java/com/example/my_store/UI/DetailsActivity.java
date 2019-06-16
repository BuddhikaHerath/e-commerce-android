package com.example.my_store.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_store.Clients.APICLIENT;
import com.example.my_store.Model.Orders;
import com.example.my_store.Model.OrderProducts;
import com.example.my_store.Model.Product;
import com.example.my_store.Model.User;
import com.example.my_store.R;
import com.example.my_store.Services.CartService;
import com.example.my_store.Services.ProductService;
import com.example.my_store.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private Product product;
    private Button addtocart,decrementBtn,incrementBtn;
    private ImageView mobimage;
    private TextView pmodel, pcompany, pPrice, pdetails, txtQuantity, txtTotal;
    private Long id;
    private int qty = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        addtocart = (Button) findViewById(R.id.btnAddToCart);
        mobimage = (ImageView) findViewById(R.id.itemImage);
        pmodel = (TextView) findViewById(R.id.itemModel);
        pcompany = (TextView) findViewById(R.id.itemCompany);
        pPrice = (TextView) findViewById(R.id.itemPrice);
        pdetails = (TextView) findViewById(R.id.itemDetails);
        addtocart = findViewById(R.id.btnAddToCart);
        decrementBtn = findViewById(R.id.details_DecrementBtn);
        incrementBtn = findViewById(R.id.details_IncrementBtn);
        txtQuantity = findViewById(R.id.product_qty);
        txtTotal = findViewById(R.id.productTotal);

        Intent intent = new Intent();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getLong("id");

//            Product product = new Product();
//            product.setId(id);

            ProductService productService = APICLIENT.getClient().create(ProductService.class);
            Call<ProductDTO> call = productService.getProductById(id);

            call.enqueue(new Callback<ProductDTO>() {
                @Override
                public void onResponse(Call<ProductDTO> call, Response<ProductDTO> response) {
                    if (response.isSuccessful()) {
                        ProductDTO prodDTO = response.body();
                        //ProductDTO prodDTO = orderProdDTO.getProductDTO();
                        //Product prod = new Product();
                        product = new Product();
                        product.setId(prodDTO.getId());
                        product.setCompany(prodDTO.getCompany());
                        product.setDescription(prodDTO.getDescription());
                        System.err.println("prod mage "+prodDTO.getImage());
                        byte[] decodedString = Base64.decode(prodDTO.getImage(), Base64.DEFAULT);
                        product.setImage(decodedString);
                        product.setPrice(prodDTO.getPrice());
                        product.setQuantity(prodDTO.getQuantity());
                        product.setTitle(prodDTO.getTitle());


                        pPrice.setText(String.valueOf(product.getPrice()));
                        pdetails.setText(product.getDescription());
                        pmodel.setText(product.getTitle());
                        pcompany.setText(product.getCompany());

                        decodedString = product.getImage();
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        mobimage.setImageBitmap(decodedByte);

                        txtQuantity.setText(String.valueOf(qty));
                        txtTotal.setText(String.valueOf(qty*product.getPrice()));
                    }
                }

                @Override
                public void onFailure(Call<ProductDTO> call, Throwable t) {
                    System.out.println(t.getMessage());

                }


            });
        }

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCart(v);
            }
        });
        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementQty(v);
            }
        });
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementQty(v);
            }
        });


    }

    private void incrementQty(View view) {
        SharedPreferences shared = getSharedPreferences("login",getApplicationContext().MODE_PRIVATE);
        User user = new User();
        user.setUsername(shared.getString("username",""));
        user.setUsername(shared.getString("password",""));


        ProductService productService = APICLIENT.getClientWithAuth(user.getUsername(),user.getPassword()).create(ProductService.class);
        Call<ResponseBody> call = productService.getQty(product.getId(),(qty+1));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.err.println(response);
                if (response.isSuccessful()) {
                    qty+=1;
                    txtQuantity.setText(String.valueOf(qty));
                    txtTotal.setText(String.valueOf(qty*product.getPrice()));
                    System.err.println("Increment Successfully!");
                    Toast.makeText(getApplicationContext(), "Increment Successfully!", Toast.LENGTH_SHORT);
//                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
//                    startActivity(intent);
                }else{
                    System.err.println("Increment un-Successfully!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.err.println(t.getMessage());
            }

        });
    }

    private void decrementQty(View view) {
        SharedPreferences shared = getSharedPreferences("login",getApplicationContext().MODE_PRIVATE);
        User user = new User();
        user.setUsername(shared.getString("username",""));
        user.setUsername(shared.getString("password",""));


        ProductService productService = APICLIENT.getClientWithAuth(user.getUsername(),user.getPassword()).create(ProductService.class);
        Call<ResponseBody> call = productService.getQty(product.getId(),(qty-1));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    qty-=1;
                    txtQuantity.setText(String.valueOf(qty));
                    txtTotal.setText(String.valueOf(qty*product.getPrice()));
                    System.err.println("Decremented Successfully!");
                    Toast.makeText(getApplicationContext(), "Decremented Successfully!", Toast.LENGTH_SHORT);
//                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
//                    startActivity(intent);
                }else{
                    System.err.println("Decrement un-Successfully!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.err.println(t.getMessage());
            }

        });
    }

    public void AddToCart(View view) {
        SharedPreferences shared = getSharedPreferences("login",getApplicationContext().MODE_PRIVATE);
        User user = new User();
        user.setUsername(shared.getString("username",""));

        OrderProducts orderProd = new OrderProducts();
        orderProd.setProduct(product);
        orderProd.setQuantity(qty);
        List<OrderProducts> orderProdsList = new ArrayList<>();
        orderProdsList.add(orderProd);

        Orders orders = new Orders();
        orders.setUser(user);
        orders.setCartOrders(orderProdsList);

        CartService cartService = APICLIENT.getClientWithAuth(user.getUsername(),user.getPassword()).create(CartService.class);
        Call<OrderProducts> call = cartService.addToCart(orders);

        call.enqueue(new Callback<OrderProducts>() {
            @Override
            public void onResponse(Call<OrderProducts> call, Response<OrderProducts> response) {
                if (response.isSuccessful()) {
                    System.out.println("Successfully added to the cart");
                    Toast.makeText(DetailsActivity.this, "Successfully added to the cart", Toast.LENGTH_SHORT);
//                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
//                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<OrderProducts> call, Throwable t) {
                System.err.println(t.getMessage());
            }

        });
    }

}
