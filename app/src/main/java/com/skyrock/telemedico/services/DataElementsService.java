package com.skyrock.telemedico.services;

import com.skyrock.telemedico.Models.DataElementResponseModelList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DataElementsService {
    @GET("dataElements.json")
    Call<DataElementResponseModelList> getDataElements(@Header("Authorization") String auth, @Query("page") int page);
}
