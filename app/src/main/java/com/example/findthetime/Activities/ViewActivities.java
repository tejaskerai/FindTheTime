package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findthetime.Adapters.ViewActivitiesListAdapter;
import com.example.findthetime.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Backendless.ActivityRepository;
import Backendless.UserActivityRepository;
import Backendless.UserRepository;
import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.User_Activity;

public class ViewActivities extends AppCompatActivity {

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

                Intent intent = new Intent(ViewActivities.this, Homepage.class);
                startActivity(intent);
            }
        });
        title = findViewById(R.id.listTitle);
        String name = "Activities";
        title.setText(name);

        // Get list of User_Activity objects by user ID
        UserActivityRepository userActivityRepository = new UserActivityRepository();
        List<User_Activity> user_activities = userActivityRepository.getUserActivityByUserId(CurrentUser.getCurrentUser().objectId);
        ArrayList<String> userActivities = new ArrayList<String>();
        for (int i = 0; i < user_activities.size() ; i++){
            System.out.println(user_activities.get(i).activityObjectId);
            userActivities.add(user_activities.get(i).activityObjectId);
        }

        // Gets list of Activity objects from activity id
        ActivityRepository activityRepository = new ActivityRepository();
        ArrayList<Activity> activities = new ArrayList<>();
        for (int i = 0; i < userActivities.size(); i++) {
            System.out.println(activityRepository.getActivityByActivityId(userActivities.get(i)).get(0).name);
            activities.add(activityRepository.getActivityByActivityId(userActivities.get(i)).get(0));

        }


        ViewActivitiesListAdapter viewActivitiesListAdapter = new ViewActivitiesListAdapter(this, activities);
        recyclerView.setAdapter(viewActivitiesListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
}
