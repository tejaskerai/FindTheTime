package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findthetime.Adapters.AvailTimesListAdapter;
import com.example.findthetime.R;

import java.util.Date;
import java.util.List;

import Models.Database.Activity;

public class AvailTimesList extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView home;
    TextView title;
    List<Integer> times;
    Date date;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        getData();

        recyclerView = findViewById(R.id.recyclerView);
        home = (ImageView) findViewById(R.id.home_activityList);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AvailTimesList.this, Homepage.class);
                startActivity(intent);
            }
        });
        title = findViewById(R.id.listTitle);
        String name = "Available times";
        title.setText(name);

        AvailTimesListAdapter availTimesListAdapter = new AvailTimesListAdapter(this, times, date, activity);
        recyclerView.setAdapter(availTimesListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getData() {
        Intent intent = getIntent();
        if (getIntent().hasExtra("times") && getIntent().hasExtra("date") && getIntent().hasExtra("activity")) {
            times = (List<Integer>) intent.getSerializableExtra("times");
            date = (Date) intent.getSerializableExtra("date");
            activity = (Activity) intent.getSerializableExtra("activity");
            System.out.println(times);
            System.out.println("Date picked: " + date);
            System.out.println("Chosen activity: " + activity);
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }
}
