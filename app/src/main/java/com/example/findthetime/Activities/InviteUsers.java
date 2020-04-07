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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Backendless.ActivityRepository;
import Backendless.UserActivityRepository;
import Backendless.UserRepository;
import Models.Database.Activity;
import Models.Database.User;

public class InviteUsers extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //TextView eventName;
    ImageView home;

    private EditText itemET;
    private Button btn;
    private Button inviteUsers;
    private ListView itemsList;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    Button addUserButton;
    ArrayAdapter<String> mAdapter;

    String name;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);



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
        switch(v.getId()){
            case R.id.add_btn:
                if (!itemET.getText().toString().isEmpty() && emailChecker(itemET.getText().toString())){
                    String itemEntered = itemET.getText().toString();
                    adapter.add(itemEntered);
                    itemET.setText("");
                    FileHelper.writeData(items, this);
                    Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show();
                    System.out.println(items);
                    break;
                }else{
                    Toast.makeText(this, "Email is empty or invalid", Toast.LENGTH_SHORT).show();
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


    public void getData(){

        Intent intent = getIntent();
        if (getIntent().hasExtra("restDetails")) {

            //System.out.println(getIntent().getSerializableExtra("restDetails").toString());
            HashMap<String, String> restDetails = (HashMap<String, String>) intent.getSerializableExtra("restDetails");


            name = restDetails.get("restName");
            address = restDetails.get("restAddress");
            System.out.println("name " + name);
            System.out.println("address "+address);

            System.out.println(restDetails);
//            eventName = findViewById(R.id.eventName);
//            eventName.setText(restDetails.get("restName"));
        }

        if (getIntent().hasExtra("movieDetails")) {

            HashMap<String, String> movieDetails = (HashMap<String, String>) intent.getSerializableExtra("movieDetails");



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
        home.setOnClickListener(new View.OnClickListener(){
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

                //TODO
                //Direct user to home page
                //Write to database, with invited users and events details

                // Creating activity
                ActivityRepository activityRepository = new ActivityRepository();
                Activity activity = activityRepository.createActivity(name, address);

                UserActivityRepository userActivityRepository = new UserActivityRepository();

                items = FileHelper.readData(InviteUsers.this);

                UserRepository userRepository = new UserRepository();

                ArrayList<User> users = new ArrayList<User>();

                for (int i = 0; i < items.size(); i++){
                    User user = userRepository.getUserByEmail(items.get(i));
                    users.add(user);
                }

                //userActivityRepository.createUserActivity(user.objectId, activity.objectId);

                for (int i = 0; i< items.size(); i++){
                    userActivityRepository.createUserActivity(users.get(i).objectId, activity.objectId);
                }
                System.out.println("List:" + userActivityRepository.getUserActivityByActivityId(activity.objectId));



                final String text = "Activity added";
                Toast.makeText(InviteUsers.this, text, Toast.LENGTH_SHORT)
                        .show();

                Intent intent = new Intent(InviteUsers.this, Homepage.class);
                startActivity(intent);
            }
        });
    }

    public boolean emailChecker(String email){
        String normalisedEmail = email.replaceAll("\\s", "").toLowerCase();
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalisedEmail);
        return matcher.matches();
    }
}
