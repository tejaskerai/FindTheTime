package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.findthetime.Adapters.RestaurantListAdapter;
import com.example.findthetime.R;

import java.util.ArrayList;
import java.util.List;

import JSONService.ZomatoService;
import Models.Domain.Location;
import Models.Domain.Restaurant;

public class RestaurantList extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        recyclerView = findViewById(R.id.recyclerView);

        Location location = (Location) getApplicationContext();

        String lat = location.getLat().toString();
        String lon = location.getLon().toString();

        int cuisineId = getIntent().getIntExtra("id", 0);

        ZomatoService zomatoService = new ZomatoService();
        List<Restaurant> restaurants = zomatoService.getRestaurants(cuisineId, lat, lon);

        RestaurantListAdapter restaurantListAdapter = new RestaurantListAdapter(this, restaurants);

        System.out.println("list adapter: " + restaurantListAdapter);
        recyclerView.setAdapter(restaurantListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
