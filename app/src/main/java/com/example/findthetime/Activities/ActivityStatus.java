package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.findthetime.R;

public class ActivityStatus extends AppCompatActivity {


    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        initializeUI();

    }

    private void initializeUI() {
        home = (ImageView) findViewById(R.id.home_activity_status);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityStatus.this, Homepage.class);
                startActivity(intent);
            }
        });
    }
}
