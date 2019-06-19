package com.example.my_store.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_store.Clients.APICLIENT;
import com.example.my_store.Model.OrderProducts;
import com.example.my_store.Model.Orders;
import com.example.my_store.Model.User;
import com.example.my_store.R;
import com.example.my_store.Services.CartService;
import com.example.my_store.UI.Cart;
import com.example.my_store.UI.DetailsActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<OrderProducts> orderProducts;
    private Context context;
    private TextView totalText;
    private Cart cart;

    public CartAdapter(List<OrderProducts> orderProducts, Context context, TextView totalText, Cart cart) {
        this.orderProducts = orderProducts;
        this.context = context;
        this.totalText = totalText;
        this.cart = cart;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final OrderProducts orderProduct = orderProducts.get(position);
        holder.textViewHead.setText(String.valueOf(orderProduct.getProduct().getTitle()));
        holder.txtQuantity.setText("Quantity : "+orderProduct.getQuantity());
        holder.txtTotal.setText("$ "+(orderProduct.getQuantity()*orderProduct.getProduct().getPrice()) );
       // holder.textDescription.setText(String.valueOf(orderProduct.getQuantity()));

        System.err.println("image " + orderProduct.getProduct().getImage());
        byte[] decodedString = orderProduct.getProduct().getImage();
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView.setImageBitmap(decodedByte);
//        Picasso.get()
//                .load(product.getImageUrl())
//                .into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", orderProduct.getProduct().getId());
                i.putExtras(bundle);
                context.startActivity(i);
                cart.finish();
            }
        });
    }

//    private void incrementBtn(final OrderProducts orderProduct, final int position) {
//        System.err.println("prod d : " + orderProduct.getProduct().getId());
//        SharedPreferences shared = context.getSharedPreferences("login", context.MODE_PRIVATE);
//        User user = new User();
//        user.setUsername(shared.getString("username", ""));
//
//        final OrderProducts op = new OrderProducts();
//        op.setProduct(orderProduct.getProduct());
//        op.setQuantity(1 + orderProduct.getQuantity());
//        List<OrderProducts> orderProds = new ArrayList<>();
//        orderProds.add(op);
//
//        Orders order = new Orders();
//        order.setUser(user);
//        order.setCartOrders(orderProds);
//
//        CartService cartService = APICLIENT.getClient().create(CartService.class);
//        Call<OrderProducts> call = cartService.updateCart(order);
//
//        call.enqueue(new Callback<OrderProducts>() {
//            @Override
//            public void onResponse(Call<OrderProducts> call, Response<OrderProducts> response) {
//
//                if (response.isSuccessful()) {
//                    System.err.println("Order Product incremented Successfully!");
//                    orderProduct.setQuantity(op.getQuantity());
//                    orderProducts.set(position, orderProduct);
//                    CartAdapter.this.notifyDataSetChanged();
//                    TotalCal();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrderProducts> call, Throwable t) {
//
//            }
//
//        });
//
//    }
//
//    private void decrementBtn(final OrderProducts orderProduct, final int position) {
//        SharedPreferences shared = context.getSharedPreferences("login", context.MODE_PRIVATE);
//        User user = new User();
//        user.setUsername(shared.getString("username", ""));
//
//        final OrderProducts op = new OrderProducts();
//        op.setProduct(orderProduct.getProduct());
//        op.setQuantity(-1 + orderProduct.getQuantity());
//        List<OrderProducts> orderProds = new ArrayList<>();
//        orderProds.add(op);
//
//        Orders order = new Orders();
//        order.setUser(user);
//        order.setCartOrders(orderProds);
//
//        CartService cartService = APICLIENT.getClient().create(CartService.class);
//        Call<OrderProducts> call = cartService.updateCart(order);
//
//        call.enqueue(new Callback<OrderProducts>() {
//            @Override
//            public void onResponse(Call<OrderProducts> call, Response<OrderProducts> response) {
//
//
//                if (response.isSuccessful()) {
//                    System.err.println("Order Product decremented Successfully!");
//                    orderProduct.setQuantity(op.getQuantity());
//                    orderProducts.set(position, orderProduct);
//                    CartAdapter.this.notifyDataSetChanged();
//                    TotalCal();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrderProducts> call, Throwable t) {
//
//            }
//
//        });
//    }

//    private void TotalCal() {
//        double total = 0;
//        for (OrderProducts orderProd : orderProducts) {
//            total += orderProd.getQuantity() * orderProd.getProduct().getPrice();
//        }
//        totalText.setText(String.valueOf(total));
//    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead;
        public TextView txtQuantity;
        public TextView txtTotal;
        public ImageView imageView;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = itemView.findViewById(R.id.textViewHeading);
            imageView = itemView.findViewById(R.id.imageViewList);
            linearLayout = itemView.findViewById(R.id.linear_row_id);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtTotal = itemView.findViewById(R.id.txtTotal);
        }

    }
}