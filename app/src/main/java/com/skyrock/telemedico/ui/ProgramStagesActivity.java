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

import com.skyrock.telemedico.Models.ProgramStagesModel;
import com.skyrock.telemedico.Models.ProgramStagesResponseModelList;
import com.skyrock.telemedico.Models.ProgramsModel;
import com.skyrock.telemedico.R;
import com.skyrock.telemedico.adapters.ProgramStagesRecyclerviewAdapter;
import com.skyrock.telemedico.adapters.ProgramsRecyclerviewAdapter;
import com.skyrock.telemedico.api.RetrofitClient;
import com.skyrock.telemedico.progress.Progress;
import com.skyrock.telemedico.storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramStagesActivity extends AppCompatActivity {


    Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgramStagesRecyclerviewAdapter adapter;
    private Integer nextPage=1;
    private int hasMore= 1;
    private List<ProgramStagesModel> programStages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_stages);

        toolbar = findViewById(R.id.endlini_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Programs Stages");
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
                Intent intent = new Intent(ProgramStagesActivity.this, EventsActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.show_program_stages_recycler);


        //recyclerView Section
        adapter = new ProgramStagesRecyclerviewAdapter(programStages);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        Progress.initProgressDialog(this);
        Progress.setProgressColor();
        Progress.showProgressDialog("Loading Program Stages", "Please Wait");
        fetchProgramStages();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if(hasMore >= 1) {
                        Toast.makeText(ProgramStagesActivity.this, "Loading More Program Stages", Toast.LENGTH_LONG).show();
                        fetchProgramStages();
                    }
                }
            }
        });

    }

     void  fetchProgramStages(){
         SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(this);

         Call<ProgramStagesResponseModelList> programStagesResponseModelListCall = RetrofitClient
                 .getInstance()
                 .getProgramStagesService()
                 .getPrograms(sharedPreferenceManager.getUserAuthorizationHeader(), nextPage);

         programStagesResponseModelListCall.enqueue(new Callback<ProgramStagesResponseModelList>() {
             @Override
             public void onResponse(Call<ProgramStagesResponseModelList> call, Response<ProgramStagesResponseModelList> response) {
                 if (response.isSuccessful()){
                     Progress.dismissProgress();
                     ProgramStagesResponseModelList programStagesResponseModelList = response.body();
                      generateProgramStagesList(programStagesResponseModelList.getProgramStages());
                      hasMore = response.body().getPageCount();
                 }else{
                     Toast.makeText(ProgramStagesActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<ProgramStagesResponseModelList> call, Throwable t) {
                 Toast.makeText(ProgramStagesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });

     }

     void generateProgramStagesList(List<ProgramStagesModel> programStages){

         this.programStages.addAll(programStages);

         adapter.notifyDataSetChanged();

     }
}
