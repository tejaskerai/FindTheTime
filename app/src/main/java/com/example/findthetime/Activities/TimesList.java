package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findthetime.Adapters.MovieTimesAdapter;
import com.example.findthetime.R;

import java.util.ArrayList;
import java.util.List;

public class TimesList extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> times;
    String filmName;
    String cinemaName;
    ImageView home;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        home = (ImageView) findViewById(R.id.home_activityList);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TimesList.this, Homepage.class);
                startActivity(intent);

            }
        });

        title = findViewById(R.id.listTitle);
        String name = "Show Times";
        title.setText(name);
        recyclerView = findViewById(R.id.recyclerView);
        getData();
        MovieTimesAdapter movieTimesAdapter = new MovieTimesAdapter(this, times, cinemaName, filmName);
        recyclerView.setAdapter(movieTimesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    public void getData(){

        if(getIntent().hasExtra("cinemaName") && getIntent().hasExtra("times") && getIntent().hasExtra("filmName")){

             cinemaName = getIntent().getStringExtra("cinemaName");
             times = getIntent().getStringArrayListExtra("times");
             filmName = getIntent().getStringExtra("filmName");

        }else{
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }


}
