package com.skyrock.telemedico.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.skyrock.telemedico.Models.UserLoginResponseModel;

public class SharedPreferenceManager {

    public static final String SHARED_PREF_NAME="user_shared_preff";
    private static SharedPreferenceManager mInstance;
    private Context context;

    public SharedPreferenceManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context){
        if (mInstance==null){
            mInstance=new SharedPreferenceManager(context);
        }
        return mInstance;
    }

    public void saveUserCredentials(UserLoginResponseModel userLoginResponseModel){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token",userLoginResponseModel.getAccess_token());
        editor.putString("token_type",userLoginResponseModel.getToken_type());
        editor.putString("refresh_token",userLoginResponseModel.getRefresh_token());
        editor.putString("expires_in",userLoginResponseModel.getExpires_in());
        editor.putString("scope",userLoginResponseModel.getScope());

        editor.apply();
    }

    public boolean isLogggedIn(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        return sharedPreferences.getString("access_token",null) != null;
    }



    public UserLoginResponseModel userLoginResponseModel(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        return new UserLoginResponseModel(
                sharedPreferences.getString("access_token", null),
                sharedPreferences.getString("token_type",null),
                sharedPreferences.getString("refresh_token",null),
                sharedPreferences.getString("expires_in",null),
                sharedPreferences.getString("scope", null)

        );

    }

    public boolean isTokenExpired(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        sharedPreferences.getInt("expires_in", 0);
        //do nothing
        return true;
    }

    public void refreshToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        sharedPreferences.getString("refresh_token",null);
    }

    public String getUserAuthorizationHeader(){
        UserLoginResponseModel userLoginResponseModel = userLoginResponseModel();
        return "Bearer "+userLoginResponseModel.getAccess_token();
    }

    public void clear(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
