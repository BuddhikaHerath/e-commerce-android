package com.example.my_store.Clients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICLIENT {

        public static final String BASE_URL = "http://192.168.137.1:8080/api/"; //genymotionpubli
//    public static final String BASE_URL = "http://10.0.3.2:8080/api/"; //genymotionpubli
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
}

