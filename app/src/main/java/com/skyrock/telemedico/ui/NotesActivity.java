package com.skyrock.telemedico.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;

import com.skyrock.telemedico.R;

public class NotesActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        toolbar = findViewById(R.id.endlini_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Diagnosis Notes");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.whiteCardColor));
        }
    }
}
