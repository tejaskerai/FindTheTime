package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.findthetime.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AcceptOrDecline extends AppCompatActivity {


    ImageView home;
    Button accept;
    Button decline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_or_decline);

        initializeUI();

    }

    private void initializeUI() {

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

                //TODO: If statement
                //if activity is still pending,
                    // Call graph and add calendar details to db
                    // toast saying activity accepted, direct to homepage
                //else
                    // toast saying sorry too late, activity has started

                Intent intent= new Intent(AcceptOrDecline.this, Homepage.class);

                startActivity(intent);
            }
        });

        decline = findViewById(R.id.btn_deline);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: remove user from table 'users invited'
                // direct user to home page

                Intent intent= new Intent(AcceptOrDecline.this, Homepage.class);

                startActivity(intent);
            }
        });

    }
}
