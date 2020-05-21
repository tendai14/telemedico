package com.skyrock.telemedico.authorization;

import android.util.Base64;

public class LoginUtils {

    public static String getClientAuthorizationHeader(String userName, String password) {
        String credential = userName + ":" + password;
        return "Basic " + Base64.encodeToString(credential.getBytes(),Base64.NO_WRAP);
    }
    public static String getClientAuthorizationHeader(){
        return getClientAuthorizationHeader("1", "5neOzmKN8cziCk7XytylQctuRS1HUA0VJT67ZyZB");
    }
}
