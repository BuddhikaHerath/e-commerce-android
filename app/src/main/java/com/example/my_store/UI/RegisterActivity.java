package com.example.my_store.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_store.Clients.APICLIENT;
import com.example.my_store.Model.User;
import com.example.my_store.R;
import com.example.my_store.Services.UserService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText InputName, InputPassword, ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        ConfirmPassword = (EditText) findViewById(R.id.register_phone_number_input);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = InputPassword.getText().toString();
                String conPass = ConfirmPassword.getText().toString();
                if(pass.equals(conPass)){
                    String name = InputName.getText().toString();
                    User user = new User();
                    user.setUsername(name);
                    user.setPassword(pass);

                    UserService userService = APICLIENT.getClient().create(UserService.class);
                    Call<ResponseBody> call = userService.registerUser(user);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            System.err.println("Reg : "+response);
                            if (response.isSuccessful()) {

                                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(login);
                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this, "User already registered!", Toast.LENGTH_SHORT).show();
                                System.err.println("register un-success");
                                APICLIENT.setNull();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            System.out.println(t.getMessage());
                            APICLIENT.setNull();
                        }


                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Password not matched!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
