package com.skyrock.telemedico.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.skyrock.telemedico.Models.DataElementResponseModelList;
import com.skyrock.telemedico.Models.DataElementsModel;
import com.skyrock.telemedico.R;
import com.skyrock.telemedico.adapters.DataElementsRecyclerviewAdapter;
import com.skyrock.telemedico.adapters.EventsRecyclerViewAdapter;
import com.skyrock.telemedico.api.RetrofitClient;
import com.skyrock.telemedico.events.EventsModel;
import com.skyrock.telemedico.progress.Progress;
import com.skyrock.telemedico.storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataElementsActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private DataElementsRecyclerviewAdapter adapter;
    private Integer nextPage=1;
    private int hasMore= 1;
    private List<DataElementsModel> dataElements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_elements);


        toolbar = findViewById(R.id.endlini_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Data Elements");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.whiteCardColor));
        }

        if (getSupportActionBar() != null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataElementsActivity.this, EventsActivity.class);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.show_data_elements_recycler);


        //recyclerView Section
        adapter = new DataElementsRecyclerviewAdapter(dataElements);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);



        Progress.initProgressDialog(this);
        Progress.setProgressColor();
        Progress.showProgressDialog("Loading Data Elements", "Please Wait");
        fetchDataElements();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if(hasMore >= 1) {
                        Toast.makeText(DataElementsActivity.this, "Loading More DataElements", Toast.LENGTH_LONG).show();
                       fetchDataElements();
                    }
                }
            }
        });

    }


    void fetchDataElements(){
        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(DataElementsActivity.this);

        Call<DataElementResponseModelList> dataElementResponseModelListCall = RetrofitClient
                .getInstance()
                .getDataElementsService()
                .getDataElements(sharedPreferenceManager.getUserAuthorizationHeader(), nextPage);

        dataElementResponseModelListCall.enqueue(new Callback<DataElementResponseModelList>() {
            @Override
            public void onResponse(Call<DataElementResponseModelList> call, Response<DataElementResponseModelList> response) {
                if (response.isSuccessful()){
                    Progress.dismissProgress();
                    assert response.body() != null;
                    generateDataElementsList(response.body().getDataElements());
                    hasMore = response.body().getPageCount();
                    nextPage ++;
                }else {
                    Toast.makeText(DataElementsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataElementResponseModelList> call, Throwable t) {
                Toast.makeText(DataElementsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    void generateDataElementsList(List<DataElementsModel> eventsModelList){

        this.dataElements.addAll(eventsModelList);

        adapter.notifyDataSetChanged();
    }
}
