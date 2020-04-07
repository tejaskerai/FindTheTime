package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findthetime.Adapters.CreatedActivitiesAdapter;
import com.example.findthetime.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Backendless.ActivityRepository;
import Models.CurrentUser;
import Models.Database.Activity;

public class ViewCreatedActivities extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView home;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        recyclerView = findViewById(R.id.recyclerView);
        home = (ImageView) findViewById(R.id.home_activityList);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ViewCreatedActivities.this, Homepage.class);
                startActivity(intent);
            }
        });

        title = findViewById(R.id.listTitle);
        String name = "Created Activities";
        title.setText(name);
        title.setTextSize(35);

        //TODO: Get list of all activities the user has created


        ActivityRepository activityRepository = new ActivityRepository();
        List<Activity> activities = activityRepository.getActivityByCreatorId(CurrentUser.getCurrentUser().objectId);



        CreatedActivitiesAdapter createdActivitiesAdapter = new CreatedActivitiesAdapter(this, activities);
        recyclerView.setAdapter(createdActivitiesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
