package com.skyrock.telemedico.authorization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skyrock.telemedico.services.LoginService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRetrofitClient {


    private static final String BASE_URL="https://dev.itin.africa/telemed/";

    private static LoginRetrofitClient mInstance;
    private Retrofit retrofit;
    private LoginRetrofitClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized  LoginRetrofitClient getInstance(){

        if (mInstance==null) {
            mInstance = new LoginRetrofitClient();
        }
        return mInstance;
    }

    public LoginService getLoginService(){
        return retrofit.create(LoginService.class);
    }

}
