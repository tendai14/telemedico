package com.skyrock.telemedico.services;

import com.skyrock.telemedico.Models.OrganisationUnitResponseModelList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface OrganisationUnitsService {
    @GET("organisationUnits.json")
    Call<OrganisationUnitResponseModelList> getPrograms(@Header("Authorization") String auth, @Query("page") int page);
}
