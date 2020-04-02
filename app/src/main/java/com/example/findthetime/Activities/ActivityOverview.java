package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.findthetime.R;

public class ActivityOverview extends AppCompatActivity {


    ImageView home;
    Button addToCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        initializeUI();
    }


    private void initializeUI() {

        home = (ImageView) findViewById(R.id.home_activity_overview);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityOverview.this, Homepage.class);
                startActivity(intent);

            }
        });


        addToCalendar = findViewById(R.id.btn_add_to_calendar);
        addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: call microsoft to add event to calendar
                Intent intent= new Intent(ActivityOverview.this, Homepage.class);

                startActivity(intent);
            }
        });

    }
}
