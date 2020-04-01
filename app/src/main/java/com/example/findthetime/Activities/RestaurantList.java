package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findthetime.Adapters.RestaurantListAdapter;
import com.example.findthetime.R;

import java.util.ArrayList;
import java.util.List;

import JSONService.ZomatoService;
import Models.Domain.Location;
import Models.Domain.Restaurant;

public class RestaurantList extends AppCompatActivity {

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

                Intent intent = new Intent(RestaurantList.this, Homepage.class);
                startActivity(intent);
            }
        });
        title = findViewById(R.id.listTitle);
        String name = "Restaurants";
        title.setText(name);
        Location location = (Location) getApplicationContext();
        String lat = location.getLat().toString();
        String lon = location.getLon().toString();
        int cuisineId = getIntent().getIntExtra("id", 0);
        ZomatoService zomatoService = new ZomatoService();
        List<Restaurant> restaurants = zomatoService.getRestaurants(cuisineId, lat, lon);
        RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(this, restaurants);
        recyclerView.setAdapter(restaurantListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
