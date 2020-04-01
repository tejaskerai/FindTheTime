package com.example.findthetime.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.findthetime.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RestaurantDetails extends AppCompatActivity {


    TextView restNameT, restAddressT, restPhoneNumberT, restTimingsT, restURLT;

    Button continueButton;

    ImageView home;

    String restName, restAddress, restPhoneNumber, restTimings, restURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        initializeUI();

        getData();
        setData();

    }

    private void initializeUI() {

        restNameT = findViewById(R.id.restName);
        restAddressT = findViewById(R.id.restAddress);
        restPhoneNumberT = findViewById(R.id.restPhoneNumber);
        restTimingsT = findViewById(R.id.restTimings);
        restURLT = findViewById(R.id.restUrl);

        home = findViewById(R.id.home_restaurantDetails);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RestaurantDetails.this, Homepage.class);
                startActivity(intent);

            }
        });


        continueButton = findViewById(R.id.continueButtonRest);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (getIntent().hasExtra("restDetails")) {
                    Intent intent = getIntent();

                    HashMap<String, String> restDetails = (HashMap<String, String>) intent.getSerializableExtra("restDetails");

                    Intent inviteUsers = new Intent(RestaurantDetails.this, InviteUsers.class);

                    inviteUsers.putExtra("restDetails", restDetails);
                    startActivity(inviteUsers);

                }else {
                    System.out.println("No data");
                }

            }
        });


    }

    private void getData() {
        if (getIntent().hasExtra("restDetails")){
            Intent intent = getIntent();
            HashMap<String, String> restDetails = (HashMap<String, String>) intent.getSerializableExtra("restDetails");
            restName = restDetails.get("restName");
            restAddress = restDetails.get("restAddress");
            restPhoneNumber = restDetails.get("restPhoneNumber");
            restTimings = restDetails.get("restTimings");
            restURL = restDetails.get("restURL");
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {

        restNameT.setText(restName);
        restAddressT.setText(restAddress);
        restPhoneNumberT.setText(restPhoneNumber);
        restTimingsT.setText(restTimings);
        restURLT.setText(restURL);

    }
}
