package com.skyrock.telemedico.authorization;

import android.util.Base64;

public class LoginUtils {

    public static String getClientAuthorizationHeader(String userName, String password) {
        String credential = userName + ":" + password;
        return "Basic " + Base64.encodeToString(credential.getBytes(),Base64.NO_WRAP);
    }
    public static String getClientAuthorizationHeader(){
        return getClientAuthorizationHeader("2", "8ad430750-da24-55bd-e938-ffe6d5dc83c");
    }
}
