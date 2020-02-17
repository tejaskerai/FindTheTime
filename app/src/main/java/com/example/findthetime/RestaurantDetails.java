package com.example.findthetime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ZomatoAPIService.ZomatoAPI;

public class RestaurantDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);


        CreateRestaurantActivity act = new CreateRestaurantActivity();



        Intent intent = getIntent();
        String restName = intent.getStringExtra(act.restName);
        TextView text = findViewById(R.id.textView);
        text.setText(restName);
    }
}
