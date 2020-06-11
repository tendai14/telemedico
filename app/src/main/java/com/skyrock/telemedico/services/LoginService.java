package com.skyrock.telemedico.services;

import com.skyrock.telemedico.Models.UserLoginResponseModel;
import com.skyrock.telemedico.authorization.LoginRetrofitClient;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {

    @POST("uaa/oauth/token")
    @FormUrlEncoded
    Call<UserLoginResponseModel> loginUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grant_type,
            @Header("Authorization") String auth
    );

}
