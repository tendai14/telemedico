package com.skyrock.telemedico.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;

import com.skyrock.telemedico.R;

public class Progress {

    private static ProgressDialog progressDialog;

    public static void initProgressDialog(Context context) {

        progressDialog = new ProgressDialog(context);
    }

    public static void showProgressDialog(String title, String message) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgress() {
        progressDialog.dismiss();
    }

    public static void setProgressColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            progressDialog.setProgressStyle(R.color.colorPrimaryDark);


        }
    }

}