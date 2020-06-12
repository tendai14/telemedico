package com.skyrock.telemedico.services;

import com.skyrock.telemedico.Models.ProgramStagesResponseModelList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ProgramStagesService {
    @GET("programStages.json")
    Call<ProgramStagesResponseModelList> getPrograms(@Header("Authorization") String auth, @Query("page") int page);
}
