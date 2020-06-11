package com.skyrock.telemedico.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skyrock.telemedico.Models.UserLoginResponseModel;
import com.skyrock.telemedico.R;
import com.skyrock.telemedico.authorization.LoginRetrofitClient;
import com.skyrock.telemedico.authorization.LoginUtils;
import com.skyrock.telemedico.progress.Progress;
import com.skyrock.telemedico.storage.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText userName, password;
    Button logUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        logUser.setOnClickListener((v)->{
            if (!validateFields()){
                return;
            }else{
                Progress.initProgressDialog(this);
                Progress.setProgressColor();
                Progress.showProgressDialog("User Logging in", "Please Wait....");
                authenticateUser();
            }




        });

    }

    void initViews(){
        userName = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);
        logUser = findViewById(R.id.auth_user);
    }


    boolean validateFields(){

        String pass,username;
        boolean valid=true;

        username = this.userName.getText().toString().trim();
        pass = password.getText().toString().trim();

        if (username.isEmpty()){
            userName.requestFocus();
            userName.setError("Please Enter user name");
            valid=false;

        }


        if (pass.isEmpty()){
            password.requestFocus();
            password.setError("Please Enter password");
            valid=false;

        }

        return valid;
    }


    void authenticateUser(){

        if (!validateFields()){
            return;
        }
        String username = this.userName.getText().toString();
        String password = this.password.getText().toString();

        Call<UserLoginResponseModel> userLoginResponseModelCall = LoginRetrofitClient
                .getInstance()
                .getLoginService()
                .loginUser(username, password, "password", LoginUtils.getClientAuthorizationHeader());

        userLoginResponseModelCall.enqueue(new Callback<UserLoginResponseModel>() {
            @Override
            public void onResponse(Call<UserLoginResponseModel> call, Response<UserLoginResponseModel> response) {
                if (response.isSuccessful()){
                    Progress.dismissProgress();
                    UserLoginResponseModel userLoginResponseModel= response.body();
                    SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(MainActivity.this);
                    sharedPreferenceManager.saveUserCredentials(userLoginResponseModel);

                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                    Progress.dismissProgress();
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Progress.dismissProgress();
            }
        });
    }




}
