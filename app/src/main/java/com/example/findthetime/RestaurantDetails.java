package com.example.findthetime;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.List;

import JSONService.ZomatoService;
import Models.Domain.Location;
import Models.Domain.Restaurant;

public class RestaurantDetails extends AppCompatActivity {


    Location location = (Location) getApplicationContext();

    String lat = location.getLat().toString();
    String lon = location.getLon().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);


        int restId = getIntent().getIntExtra("id", 0);

        ZomatoService zomatoService = new ZomatoService();
        List<Restaurant> restaurants = zomatoService.getRestaurants(restId, lat, lon);

        System.out.println(" ");
        System.out.println("name: " + restaurants.get(restId).getName());
        System.out.println(restaurants.get(restId).getAddress());
        System.out.println(restaurants.get(restId).getPhoneNumbers());
        System.out.println(restaurants.get(restId).getTimings());
        System.out.println(restaurants.get(restId).getUrl());
        System.out.println(" ");

    }

}
