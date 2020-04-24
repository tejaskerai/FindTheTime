package com.example.findthetime.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findthetime.Helper.FileHelper;
import com.example.findthetime.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Backendless.ActivityRepository;
import Backendless.UserActivityRepository;
import Backendless.UserRepository;
import CalendarService.MSGraph;
import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.User;
import Models.Database.User_Activity;

public class InviteUsers extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //TextView eventName;
    ImageView home;

    private EditText itemET;
    private Button btn;
    private Button inviteUsers;
    private ListView itemsList;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    UserRepository userRepository = new UserRepository();
    List<User> users = userRepository.getAllUsers();
    List<String> userEmails = new ArrayList<>();
    Button addUserButton;
    ArrayAdapter<String> mAdapter;

    String name;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        for (int i = 0; i < users.size(); i++) {
            userEmails.add(users.get(i).getEmail());
        }
        initializeUI();
        getData();

        items = FileHelper.readData(this);
        System.out.println(items);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);
        btn.setOnClickListener(this);
        itemsList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                String email = itemET.getText().toString().toLowerCase().replaceAll("\\s+", "");
                System.out.println("Email: " + email);
                if (userEmails.contains(email)) {
                    String itemEntered = itemET.getText().toString();
                    adapter.add(itemEntered);
                    itemET.setText("");
                    FileHelper.writeData(items, this);
                    Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show();
                    System.out.println(items);
                    break;
                } else {
                    Toast.makeText(this, "Email is empty or doesn't exist", Toast.LENGTH_SHORT).show();
                }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        adapter.notifyDataSetChanged();
        FileHelper.writeData(items, this);
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }


    public void getData() {

        Intent intent = getIntent();
        if (getIntent().hasExtra("restDetails")) {

            //System.out.println(getIntent().getSerializableExtra("restDetails").toString());
            HashMap<String, String> restDetails = (HashMap<String, String>) intent.getSerializableExtra("restDetails");


            name = restDetails.get("restName");
            address = restDetails.get("restAddress");
            System.out.println("name " + name);
            System.out.println("address " + address);

            System.out.println(restDetails);
//            eventName = findViewById(R.id.eventName);
//            eventName.setText(restDetails.get("restName"));
        }

        if (getIntent().hasExtra("movieDetails")) {

            HashMap<String, String> movieDetails = (HashMap<String, String>) intent.getSerializableExtra("movieDetails");

            name = movieDetails.get("filmName");
            address = movieDetails.get("cinemaName");
            System.out.println("name " + name);
            System.out.println("address " + address);

//            eventName = findViewById(R.id.eventName);
//            eventName.setText(movieDetails.get("filmName"));

        }
    }

    private void initializeUI() {

        itemET = findViewById(R.id.item_edit_text);
        btn = findViewById(R.id.add_btn);
        inviteUsers = findViewById(R.id.invitebutton);
        itemsList = findViewById(R.id.items_list);


        home = (ImageView) findViewById(R.id.home_activityAddUsers);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InviteUsers.this, Homepage.class);
                startActivity(intent);
            }
        });

        inviteUsers = (Button) findViewById(R.id.invitebutton);
        inviteUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.size() == 0) {

                    Toast.makeText(InviteUsers.this, "Please add friends to invite", Toast.LENGTH_SHORT)
                            .show();
                } else {

                    // Creating activity
                    ActivityRepository activityRepository = new ActivityRepository();
                    Activity activity = activityRepository.createActivity(name, address);


                    // List of all users
                    items = FileHelper.readData(InviteUsers.this);


                    // Gets list of all users invited
                    ArrayList<User> users = new ArrayList<User>();
                    for (int i = 0; i < items.size(); i++) {
                        User user = userRepository.getUserByEmail(items.get(i));
                        users.add(user);
                    }

                    // Create records in User_Activity table with user and activity ID
                    UserActivityRepository userActivityRepository = new UserActivityRepository();
                    for (int i = 0; i < items.size(); i++) {
                        userActivityRepository.createUserActivity(users.get(i).objectId, activity.objectId);
                    }


                    // Set relation between Activity created and User_Activity
                    List<User_Activity> user_activities = userActivityRepository.getUserActivityByActivityId(activity.objectId);
                    activityRepository.setRelation(user_activities, activity);


                    // Set relation between User and User_Activity
                    for (int i = 0; i < items.size(); i++) {
                        List<User_Activity> user_activities1 = userActivityRepository.getUserActivityByUserId(users.get(i).objectId);
                        userRepository.setRelation(user_activities1, users.get(i));
                    }

                    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
                    final Date startDate = new Date();
                    System.out.println("start: " + formatter.format(startDate));


                    // Gets the date 7 days after the start date
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(startDate);
                    calendar.add(Calendar.DAY_OF_YEAR, 7);
                    final Date endDate = calendar.getTime();

                    Toast.makeText(InviteUsers.this, "Adding your events", Toast.LENGTH_SHORT)
                            .show();
                    // Get creators calendar events
                    MSGraph msGraph = new MSGraph();
                    msGraph.callGraphCalendarAPI(CurrentUser.getCurrentUser().authenticationResult, "https://graph.microsoft.com/v1.0/me/calendarview?startdatetime=" + formatter.format(startDate) + "&enddatetime=" + formatter.format(endDate), startDate, endDate, InviteUsers.this);


                    // Toast message
                    final String text = "Activity added";
                    Toast.makeText(InviteUsers.this, text, Toast.LENGTH_SHORT)
                            .show();

                    Intent intent = new Intent(InviteUsers.this, Homepage.class);
                    startActivity(intent);

                }

            }
        });
    }

    public boolean emailChecker(String email) {
        String normalisedEmail = email.replaceAll("\\s", "").toLowerCase();
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalisedEmail);
        return matcher.matches();
    }
}
