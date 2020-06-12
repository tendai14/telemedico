package com.skyrock.telemedico.events;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface EventsService {
    @GET("events.json")
    Call<EventsResponseModelList> getEvents(@Header("Authorization") String auth, @Query("page") String page, @QueryMap(encoded = true) Map<String, String> filters);
}
