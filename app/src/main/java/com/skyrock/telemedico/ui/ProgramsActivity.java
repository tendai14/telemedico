package com.skyrock.telemedico.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.skyrock.telemedico.Models.DataElementsModel;
import com.skyrock.telemedico.Models.ProgramsModel;
import com.skyrock.telemedico.Models.ProgramsResponseModelList;
import com.skyrock.telemedico.R;
import com.skyrock.telemedico.adapters.DataElementsRecyclerviewAdapter;
import com.skyrock.telemedico.adapters.ProgramsRecyclerviewAdapter;
import com.skyrock.telemedico.api.RetrofitClient;
import com.skyrock.telemedico.progress.Progress;
import com.skyrock.telemedico.storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramsActivity extends AppCompatActivity {


    Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgramsRecyclerviewAdapter adapter;
    private Integer nextPage=1;
    private int hasMore= 1;
    private List<ProgramsModel> programs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);


        toolbar = findViewById(R.id.endlini_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Programs");
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
                Intent intent = new Intent(ProgramsActivity.this, EventsActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.show_programs_recycler);


        //recyclerView Section
        adapter = new ProgramsRecyclerviewAdapter(programs);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);



        Progress.initProgressDialog(this);
        Progress.setProgressColor();
        Progress.showProgressDialog("Loading Programs", "Please Wait");
        fetchPrograms();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if(hasMore >= 1) {
                        Toast.makeText(ProgramsActivity.this, "Loading More programs", Toast.LENGTH_LONG).show();
                        fetchPrograms();
                    }
                }
            }
        });
    }

    private void fetchPrograms() {
        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        Call<ProgramsResponseModelList> programsResponseModelListCall = RetrofitClient
                .getInstance()
                .getProgramsService()
                .getPrograms(sharedPreferenceManager.getUserAuthorizationHeader(), nextPage);

        programsResponseModelListCall.enqueue(new Callback<ProgramsResponseModelList>() {
            @Override
            public void onResponse(Call<ProgramsResponseModelList> call, Response<ProgramsResponseModelList> response) {
                if (response.isSuccessful()){
                    Progress.dismissProgress();
                    assert response.body() != null;
                    generateProgramsList(response.body().getPrograms());
                    hasMore = response.body().getPageCount();
                    nextPage ++;
                }else {
                    Progress.dismissProgress();
                    Toast.makeText(ProgramsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProgramsResponseModelList> call, Throwable t) {
                Progress.dismissProgress();
                Toast.makeText(ProgramsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    void generateProgramsList(List<ProgramsModel> programsModelList){

        this.programs.addAll(programsModelList);

        adapter.notifyDataSetChanged();
    }
}
