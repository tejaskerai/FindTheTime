package com.example.findthetime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import JSONService.ZomatoService;
import Models.Domain.Location;
import Models.Domain.Restaurant;

public class RestaurantList2 extends AppCompatActivity {


    List<String> restName = new ArrayList<String>();
    List<String> restAddress = new ArrayList<String>();
    List<String> restPhoneNumber = new ArrayList<String>();
    List<String> restTimings = new ArrayList<String>();
    List<String> restURL = new ArrayList<String>();



    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list2);

        recyclerView = findViewById(R.id.recyclerView);


        Location location = (Location) getApplicationContext();

        String lat = location.getLat().toString();
        String lon = location.getLon().toString();

        int cuisineId = getIntent().getIntExtra("id", 0);


        ZomatoService zomatoService = new ZomatoService();
        List<Restaurant> restaurants = zomatoService.getRestaurants(cuisineId, lat, lon);
        populate_restName(restaurants);

        RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(this, restName);

        recyclerView.setAdapter(restaurantListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void populate_restName(List<Restaurant> restaurants){
        for (int i = 0; i < restaurants.size(); i++){
            restName.add(restaurants.get(i).getName());
        }
    }
}
