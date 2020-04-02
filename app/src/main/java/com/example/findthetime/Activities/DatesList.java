package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findthetime.Adapters.DatesListAdapter;
import com.example.findthetime.Adapters.RestaurantListAdapter;
import com.example.findthetime.R;

public class DatesList extends AppCompatActivity {


    RecyclerView recyclerView;
    ImageView home;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        recyclerView = findViewById(R.id.recyclerView);
        home = (ImageView) findViewById(R.id.home_activityList);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DatesList.this, Homepage.class);
                startActivity(intent);
            }
        });
        title = findViewById(R.id.listTitle);
        String name = "Available dates";
        title.setText(name);

        // TODO: change parameters to a hashmap times available
        //DatesListAdapter datesListAdapter = new DatesListAdapter(this, restaurants);


        //recyclerView.setAdapter(datesListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
