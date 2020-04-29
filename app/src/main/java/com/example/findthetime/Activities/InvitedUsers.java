package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findthetime.R;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Backendless.EventRepository;
import Backendless.UserActivityRepository;
import Backendless.UserRepository;
import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.Event;
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
            users.add(userRepository.getUserById(user_activities.get(i).userObjectId));
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

                if (users.size() == 0){

                    Toast.makeText(InvitedUsers.this, "No friends have joined", Toast.LENGTH_SHORT).show();
                }else {
                    List<Date> dates = lists();

                    Intent intent = new Intent(InvitedUsers.this, DatesList.class);

                    intent.putExtra("dates", (Serializable) dates);
                    intent.putExtra("users", (Serializable) users);
                    intent.putExtra("activity", (Serializable) activity);

                    startActivity(intent);
                }

            }
        });
    }


    public List<Date> lists(){
        EventRepository eventRepository = new EventRepository();
        List<Set<Date>> listOfListOfdates = new ArrayList<Set<Date>>();
        List<Event> temp;

        // Loop through all accepted users
        for (int i = 0; i < userEmails.size(); i++){
            Set<Date> listOfDates = new HashSet<>();
            //Get dates for user
            temp = eventRepository.getEventsByUserId(users.get(i).getObjectId());
            System.out.println(temp);
            // Adds users dates to a list
            for(int j = 0; j < temp.size(); j++){
                listOfDates.add(temp.get(j).getDate());
                System.out.println("List of dates: " + listOfDates);
            }
            // Adds list of dates to a list
            listOfListOfdates.add(listOfDates);
        }

        // Adds Creators calendar events to list
        List<Event> events = eventRepository.getEventsByUserId(CurrentUser.getCurrentUser().objectId);
        System.out.println("Creator: " + events);
        Set<Date> listOfDates1 = new HashSet<>();
        // Adds users dates to a list
        for(int i = 0; i < events.size(); i++){
            listOfDates1.add(events.get(i).getDate());
            System.out.println("List of dates: " + listOfDates1);
        }

        listOfListOfdates.add(listOfDates1);

        System.out.println("Here: " + listOfListOfdates);
        // Intersecting list of dates
        Set<Date> intersection = listOfListOfdates.get(0);
        for (Set<Date> scan : listOfListOfdates.subList(1, listOfListOfdates.size())) {
            intersection = Sets.intersection(intersection, scan);
        }
        List<Date> dates = Lists.newArrayList(intersection);

        System.out.println("Intersected dates: "+dates);

        return  dates;
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
