package com.skyrock.telemedico.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.skyrock.telemedico.R;
import com.skyrock.telemedico.storage.SharedPreferenceManager;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(SplashScreenActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (sharedPreferenceManager.isLogggedIn()){
            Intent intent = new Intent(SplashScreenActivity.this, EventsActivity.class);
            startActivity(intent);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    SplashScreenActivity.this.startActivity(mainIntent);
                    SplashScreenActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }


    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(SplashScreenActivity.this);


            if (sharedPreferenceManager.isLogggedIn()){
                Intent intent = new Intent(SplashScreenActivity.this, EventsActivity.class);
                startActivity(intent);
            }else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /* Create an Intent that will start the Menu-Activity. */

                        Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        SplashScreenActivity.this.startActivity(mainIntent);

                        SplashScreenActivity.this.finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(SplashScreenActivity.this);
        if (sharedPreferenceManager.isLogggedIn()){
            Intent intent = new Intent(SplashScreenActivity.this, EventsActivity.class);
            startActivity(intent);
        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */

                    Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    SplashScreenActivity.this.startActivity(mainIntent);

                    SplashScreenActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }

    }
}
