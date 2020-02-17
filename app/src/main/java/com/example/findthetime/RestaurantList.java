package com.example.findthetime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import JSONService.ZomatoService;
import Models.Domain.Restaurant;

public class RestaurantList extends AppCompatActivity {
    Button newbtn;

    /* Location details*/
    String lat = "51.600941";
    String lon = "-0.285640";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);


        int cuisineId = getIntent().getIntExtra("id", 0);
        ZomatoService zomatoService = new ZomatoService();
        List<Restaurant> restaurants = zomatoService.getRestaurants(cuisineId, lat, lon);


        initializeUI(restaurants);
//        for (int i = 0; i < restaurants.size(); i++) {
//            System.out.println(restaurants.get(i).getName());
//            System.out.println(restaurants.get(i).getAddress());
//            System.out.println(restaurants.get(i).getPhoneNumbers());
//            System.out.println(restaurants.get(i).getTimings());
//            System.out.println(restaurants.get(i).getUrl());
//        }

    }


    public void initializeUI(List<Restaurant> restaurants){


        LinearLayout layout = (LinearLayout) findViewById(R.id.rootLayout);
        for (int i = 0; i < restaurants.size(); i++) {


            final int count = i;

            System.out.println("Count: " + count);


            newbtn = new Button(RestaurantList.this);
            newbtn.setId(i+1);
            newbtn.setText(restaurants.get(i).getName());
            newbtn.setTextSize(23);
            int textColor = Color.parseColor("#FFFFFF");
            newbtn.setTextColor(textColor);
            newbtn.setWidth(1000);
            newbtn.setHeight(350);
            newbtn.setBackgroundResource(R.drawable.custom_button);

            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.setMargins(8, 20, 8, 20);
            newbtn.setLayoutParams(buttonLayoutParams);

            newbtn(newbtn, i);
            layout.addView(newbtn);

            System.out.println("Count last: " + count);
            System.out.println(restaurants.get(i).getName());
            System.out.println(restaurants.get(i).getAddress());
            System.out.println(restaurants.get(i).getPhoneNumbers());
            System.out.println(restaurants.get(i).getTimings());
            System.out.println(restaurants.get(i).getUrl());
        }

    }

    public void newbtn(Button btn, int id){

        final int no = id;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RestaurantList.this, RestaurantDetails.class);

                System.out.println("Count in on click: " + no);
                intent.putExtra("id", no);

                startActivity(intent);
            }
        });

    }


}
