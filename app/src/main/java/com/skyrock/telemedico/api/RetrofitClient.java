package com.skyrock.telemedico.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skyrock.telemedico.events.EventsService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //define base url
    private static final String BASE_URL="https://dev.itin.africa/telemed/api/";

    //create retrofit instance
    private static RetrofitClient mInstance;

    //create retrofit Object
    private Retrofit retrofit;


    private RetrofitClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("dd-MM-yyyy")
                .create();


        //Defining retrofit api service
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

//        OkHttpClient client = new OkHttpClient();
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    //get retrofit instance
    public static synchronized  RetrofitClient getInstance(){

        if (mInstance==null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public EventsService getEventsService(){
        return retrofit.create(EventsService.class);
    }

}
