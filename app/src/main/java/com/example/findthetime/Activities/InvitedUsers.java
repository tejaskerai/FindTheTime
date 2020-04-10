package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findthetime.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Backendless.UserActivityRepository;
import Backendless.UserRepository;
import Models.Database.Activity;
import Models.Database.User;
import Models.Database.User_Activity;

public class InvitedUsers extends AppCompatActivity {

    Button viewTimes;
    ImageView home;
    Activity activity;

    ArrayList<User> users = new ArrayList<>();
    ArrayList<String> userEmails = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private ListView joinedUsersLst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited_users);

        getData();
        dataRetrieval();

        initializeUI();

    }

    private void dataRetrieval() {


        UserActivityRepository userActivityRepository = new UserActivityRepository();
        List<User_Activity> user_activities = userActivityRepository.getJoinedUsersActivity(activity.getObjectId());


        UserRepository userRepository = new UserRepository();

        for (int i = 0; i < user_activities.size(); i++) {
            users.add(userRepository.getUserById(user_activities.get(0).userObjectId));
            userEmails.add(users.get(i).getEmail());
        }
        System.out.println(userEmails);
    }

    private void initializeUI() {

        joinedUsersLst = findViewById(R.id.users_accepted_lst);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userEmails);
        joinedUsersLst.setAdapter(adapter);

        home = (ImageView) findViewById(R.id.home_invitedUsers);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InvitedUsers.this, Homepage.class);
                startActivity(intent);

            }
        });

        viewTimes = findViewById(R.id.btn_view_times);
        viewTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: change intent to view list of dates available
                //TODO: Use calendar combining algorithm here with the users that have accepted and use that hashmap
                Intent intent = new Intent(InvitedUsers.this, CreateMovieActivity.class);

                startActivity(intent);

            }
        });
    }

    public void getData() {

        Intent intent = getIntent();
        if (getIntent().hasExtra("activity")) {
            activity = (Activity) intent.getSerializableExtra("activity");
            System.out.println(activity.getName());
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }
}
