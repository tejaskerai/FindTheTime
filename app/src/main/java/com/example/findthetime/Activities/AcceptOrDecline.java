package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.findthetime.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import Backendless.UserActivityRepository;
import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.User_Activity;

public class AcceptOrDecline extends AppCompatActivity {


    ImageView home;
    Button accept;
    Button decline;
    Activity activity;
    TextView nameT, placeT;
    UserActivityRepository userActivityRepository =new UserActivityRepository();
    List<User_Activity> user_activities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_or_decline);
        initializeUI();
        getData();
        setData();




    }

    private void initializeUI() {

        nameT = findViewById(R.id.acceptDeclineName);
        placeT = findViewById(R.id.acceptDeclinePlace);

        home = (ImageView) findViewById(R.id.home_accept_decline);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AcceptOrDecline.this, Homepage.class);
                startActivity(intent);

            }
        });


        accept = findViewById(R.id.btn_accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if activity is still pending,
                if (activity.getPending() == true){
                    System.out.println("still pending");
                    // Call graph and add calendar details to db
                    // toast saying activity accepted, direct to homepage
                    // change db status
                    userActivityRepository.updateUserActivity(user_activities.get(0), true);
                    Toast.makeText(AcceptOrDecline.this, "You have accepted the activity", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(AcceptOrDecline.this, Homepage.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(AcceptOrDecline.this, "Activity has already started", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(AcceptOrDecline.this, Homepage.class);
                    startActivity(intent);
                }
            }
        });

        decline = findViewById(R.id.btn_decline);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: remove user from table 'users invited'
                // direct user to home page

                userActivityRepository.deleteUserActivity(user_activities.get(0));

                Intent intent= new Intent(AcceptOrDecline.this, Homepage.class);

                startActivity(intent);
            }
        });

    }

    public void getData() {
        Intent intent = getIntent();
        if (getIntent().hasExtra("activity")) {
            activity = (Activity) intent.getSerializableExtra("activity");
            user_activities = userActivityRepository.getJoinedUserActivity(activity.getObjectId(), CurrentUser.getCurrentUser().objectId);
            System.out.println(activity.getName());
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        nameT.setText(activity.getName());
        placeT.setText(activity.getPlace());
    }
}
