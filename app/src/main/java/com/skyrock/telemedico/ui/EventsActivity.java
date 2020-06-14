package com.skyrock.telemedico.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.skyrock.telemedico.R;
import com.skyrock.telemedico.adapters.EventsRecyclerViewAdapter;
import com.skyrock.telemedico.api.RetrofitClient;
import com.skyrock.telemedico.events.EventsModel;
import com.skyrock.telemedico.events.EventsResponseModelList;
import com.skyrock.telemedico.progress.Progress;
import com.skyrock.telemedico.storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private EventsRecyclerViewAdapter adapter;
    private Integer nextPage=1;
    private int hasMore= 1;
    private List<EventsModel> events =new ArrayList<>();

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        toolbar = findViewById(R.id.endlini_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Home");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.whiteCardColor));
        }

        recyclerView = findViewById(R.id.view_events_recycler);


        drawer = findViewById(R.id.drawer_menu);
        navigationView = findViewById(R.id.nav_view);

        //recyclerView Section
        adapter = new EventsRecyclerViewAdapter(events, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);


        //action bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setElevation(10f);

        navigationView.invalidate();

        navigationView.setNavigationItemSelectedListener(this);

        Progress.initProgressDialog(this);
        Progress.setProgressColor();
        Progress.showProgressDialog("Loading Sensor Data", "Please Wait");
        showEventsData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if(hasMore >= 1) {
                        Toast.makeText(EventsActivity.this, "Loading More Sensor Data", Toast.LENGTH_LONG).show();
                        showEventsData();
                    }
                }
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;


        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        drawer.closeDrawers();

        switch (menuItem.getItemId()){

            case R.id.nav_logout:
                SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(EventsActivity.this);
                sharedPreferenceManager.clear();
                Toast.makeText(this, "You are now logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EventsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_data_elements:
                Intent intent_dataElements = new Intent(EventsActivity.this, DataElementsActivity.class);
                startActivity(intent_dataElements);
                break;

            case R.id.nav_programs:
                Intent intent_programs = new Intent(EventsActivity.this, ProgramsActivity.class);
                startActivity(intent_programs);
                break;

            case R.id.nav_program_stages:
                Intent intent_stages = new Intent(EventsActivity.this, ProgramStagesActivity.class);
                startActivity(intent_stages);
                break;

            case R.id.nav_tei:
                Intent intent_org_unit = new Intent(EventsActivity.this, OrgUnitsActivity.class);
                startActivity(intent_org_unit);
                break;
        }

        return true;

    }

    private void showEventsData() {
        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(EventsActivity.this);

        Map<String, String> data = new HashMap<>();
        data.put("orgUnit", "B7Vu9GVmEbn");

        Call<EventsResponseModelList> eventsResponseModelListCall = RetrofitClient
                .getInstance()
                .getEventsService()
                .getEvents(sharedPreferenceManager.getUserAuthorizationHeader(), nextPage.toString(), data);

        eventsResponseModelListCall.enqueue(new Callback<EventsResponseModelList>() {
            @Override
            public void onResponse(Call<EventsResponseModelList> call, Response<EventsResponseModelList> response) {
                if (response.isSuccessful()){
                    Progress.dismissProgress();
                    generateEventList(response.body().getEvents());
                    hasMore = response.body().getPageCount();
                    nextPage ++;

                }else{
                    Progress.dismissProgress();
                    Toast.makeText(EventsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventsResponseModelList> call, Throwable t) {
                Progress.dismissProgress();
                Toast.makeText(EventsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


    void generateEventList(List<EventsModel> eventsModelList){

        this.events.addAll(eventsModelList);

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }


}
