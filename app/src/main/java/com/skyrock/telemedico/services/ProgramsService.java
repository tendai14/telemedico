package com.skyrock.telemedico.services;

import com.skyrock.telemedico.Models.ProgramsResponseModelList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ProgramsService {
    @GET("programs.json")
    Call<ProgramsResponseModelList> getPrograms(@Header("Authorization") String auth, @Query("page") int page);
}
