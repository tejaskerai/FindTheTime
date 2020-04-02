package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.findthetime.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class InvitedUsers extends AppCompatActivity {

    Button viewTimes;
    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited_users);

        initializeUI();

    }

    private void initializeUI() {


        home = (ImageView) findViewById(R.id.home_invitedUsers);
        home.setOnClickListener(new View.OnClickListener(){
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
                Intent intent= new Intent(InvitedUsers.this, CreateMovieActivity.class);

                startActivity(intent);
            }
        });
    }

}
