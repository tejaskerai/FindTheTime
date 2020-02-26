package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.findthetime.Adapters.MovieTimesAdapter;
import com.example.findthetime.R;

import java.util.ArrayList;
import java.util.List;

public class TimesList extends AppCompatActivity {

    RecyclerView recyclerView;


    ArrayList<String> times;
    String cinemaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        recyclerView = findViewById(R.id.recyclerView);

        getData();

        MovieTimesAdapter movieTimesAdapter = new MovieTimesAdapter(this, times);

        recyclerView.setAdapter(movieTimesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    public void getData(){

        if(getIntent().hasExtra("cinemaName") && getIntent().hasExtra("times")){

             cinemaName = getIntent().getStringExtra("cinemaName");
             times = getIntent().getStringArrayListExtra("times");

        }else{
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }


}
