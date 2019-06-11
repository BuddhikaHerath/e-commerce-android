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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private Product product;
    private Button addtocart;
    private ImageView mobimage;
    private TextView pmodel, pcompany, pPrice, pdetails;
    private Long id;

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

    }

    public void AddToCart(View view) {
        SharedPreferences shared = getSharedPreferences("login",getApplicationContext().MODE_PRIVATE);
        User user = new User();
        user.setUsername(shared.getString("username",""));

        OrderProducts orderProd = new OrderProducts();
        orderProd.setProduct(product);
        orderProd.setQuantity(1);
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

                    Toast.makeText(getApplicationContext(), "Successfully added to the cart", Toast.LENGTH_SHORT);
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
