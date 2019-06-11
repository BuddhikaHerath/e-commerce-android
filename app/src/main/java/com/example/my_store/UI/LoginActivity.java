package com.example.my_store.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.my_store.Clients.APICLIENT;
import com.example.my_store.Model.Product;
import com.example.my_store.Model.User;
import com.example.my_store.R;
import com.example.my_store.Services.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button login_btn;
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = (Button) findViewById(R.id.login_btn);
         username = findViewById(R.id.login_phone_number_input);
         password = findViewById(R.id.login_password_input);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final User user = new User();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());

                String credential = user.getUsername() + ":" + user.getPassword();
                String auth = Base64.encodeToString(credential.getBytes(), Base64.DEFAULT);
//                Intent intent = new Intent(LoginActivity.this, Product_List.class);
//                startActivity(intent);

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

                            Intent login = new Intent(LoginActivity.this, Navigation.class);
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
        });

//

    }
}
