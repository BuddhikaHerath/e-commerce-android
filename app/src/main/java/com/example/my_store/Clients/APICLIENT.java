package com.example.my_store.Clients;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICLIENT {

        public static final String BASE_URL = "http://192.168.137.1:8080/"; //genymotionpubli
   // public static final String BASE_URL = "http://10.0.3.2:8080/"; //genymotionpubli
    //    public static final String BASE_URL = "http://10.0.2.2:8080/";    //avd

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientWithAuth(final String username, final String password) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();

                    Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                            Credentials.basic(username, password));

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())

                    .client(okHttpClient)

                    .build();
        }
        return retrofit;
    }

    public static void setNull() {
        retrofit = null;
    }
}

