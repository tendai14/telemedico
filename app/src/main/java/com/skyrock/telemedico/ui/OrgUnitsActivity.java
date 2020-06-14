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
import com.skyrock.telemedico.Models.OrganisationUnitResponseModelList;
import com.skyrock.telemedico.Models.OrganisationUnitsModel;
import com.skyrock.telemedico.R;
import com.skyrock.telemedico.adapters.EventsRecyclerViewAdapter;
import com.skyrock.telemedico.adapters.OrgUnitsRecyclerviewAdapter;
import com.skyrock.telemedico.adapters.ProgramsRecyclerviewAdapter;
import com.skyrock.telemedico.api.RetrofitClient;
import com.skyrock.telemedico.events.EventsModel;
import com.skyrock.telemedico.progress.Progress;
import com.skyrock.telemedico.storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrgUnitsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrgUnitsRecyclerviewAdapter adapter;
    private Integer nextPage=1;
    private int hasMore= 1;
    private List<OrganisationUnitsModel> orgUnits =new ArrayList<>();

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracked_entity_instances);

        toolbar = findViewById(R.id.endlini_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Org Units");
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
                Intent intent = new Intent(OrgUnitsActivity.this, EventsActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.show_tei_recycler);


        //recyclerView Section
        adapter = new OrgUnitsRecyclerviewAdapter(orgUnits);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);



        Progress.initProgressDialog(this);
        Progress.setProgressColor();
        Progress.showProgressDialog("Loading Org Units", "Please Wait");
        fetchOrgUnits();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if(hasMore >= 1) {
                        Toast.makeText(OrgUnitsActivity.this, "Loading More Org Units", Toast.LENGTH_LONG).show();
                        fetchOrgUnits();
                    }
                }
            }
        });
    }

    private void fetchOrgUnits() {
        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        Call<OrganisationUnitResponseModelList> organisationUnitResponseModelListCall = RetrofitClient
                .getInstance()
                .getOrganisationUnitsService()
                .getOrgUnits(sharedPreferenceManager.getUserAuthorizationHeader(), nextPage);

        organisationUnitResponseModelListCall.enqueue(new Callback<OrganisationUnitResponseModelList>() {
            @Override
            public void onResponse(Call<OrganisationUnitResponseModelList> call, Response<OrganisationUnitResponseModelList> response) {
                if (response.isSuccessful()){
                    Progress.dismissProgress();
                    generateOrgUnitsList(response.body().getOrganisationUnits());
                    hasMore = response.body().getPageCount();
                    nextPage ++;

                }else{
                    Toast.makeText(OrgUnitsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrganisationUnitResponseModelList> call, Throwable t) {

                Toast.makeText(OrgUnitsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void generateOrgUnitsList(List<OrganisationUnitsModel> orgUnits){

        this.orgUnits.addAll(orgUnits);

        adapter.notifyDataSetChanged();
    }
}
