package com.example.my_store.UI;


import android.app.LauncherActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.my_store.Adapter.ProductAdapter;
import com.example.my_store.Clients.APICLIENT;
import com.example.my_store.Model.Product;
import com.example.my_store.Model.User;
import com.example.my_store.R;
import com.example.my_store.Services.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private Button joinNowButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences shared = getSharedPreferences("login",getApplicationContext().MODE_PRIVATE);
        String username = shared.getString("username","");

        if(username.equalsIgnoreCase("")) {
            setContentView(R.layout.activity_main);
            joinNowButton = (Button) findViewById(R.id.main_join_now_btn);
            loginButton = (Button) findViewById(R.id.main_login_btn);


            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            joinNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            final User user = new User();
            user.setUsername(username);
            user.setPassword(shared.getString("password",""));

            UserService userService = APICLIENT.getClientWithAuth(user.getUsername(),user.getPassword()).create(UserService.class);
            Call<List<User>> call = userService.userLogin(user);

            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        SharedPreferences shared = getSharedPreferences("login",getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("username",user.getUsername());
                        editor.putString("password",user.getPassword());
                        editor.commit();

                        Intent login = new Intent(MainActivity.this, Navigation.class);
                        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(login);
                        finish();
                    }else{
                        System.err.println("login un-success");
                        APICLIENT.setNull();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    System.out.println(t.getMessage());
                    APICLIENT.setNull();
                }


            });

        }



    }


}