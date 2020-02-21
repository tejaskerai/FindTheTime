package com.example.findthetime.Activities;

import android.os.Bundle;

import com.example.findthetime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import JSONService.ZomatoService;
import Models.Domain.Location;
import Models.Domain.Restaurant;

public class RestaurantDetails extends AppCompatActivity {


    TextView restNameT, restAddressT, restPhoneNumberT, restTimingsT, restURLT;

    String restName, restAddress, restPhoneNumber, restTimings, restURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        restNameT = findViewById(R.id.restName);
        restAddressT = findViewById(R.id.restAddress);
        restPhoneNumberT = findViewById(R.id.restPhoneNumber);
        restTimingsT = findViewById(R.id.restTimings);
        restURLT = findViewById(R.id.restUrl);


        getData();
        setData();

    }

    private void getData(){

        if(getIntent().hasExtra("restName") && getIntent().hasExtra("restAddress") && getIntent().hasExtra("restPhoneNumber") && getIntent().hasExtra("restTimings") && getIntent().hasExtra("restURL")){

            restName = getIntent().getStringExtra("restName");
            restAddress = getIntent().getStringExtra("restAddress");
            restPhoneNumber = getIntent().getStringExtra("restPhoneNumber");
            restTimings = getIntent().getStringExtra("restTimings");
            restURL = getIntent().getStringExtra("restURL");


        }else{
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){

        restNameT.setText(restName);
        restAddressT.setText(restAddress);
        restPhoneNumberT.setText(restPhoneNumber);
        restTimingsT.setText(restTimings);
        restURLT.setText(restURL);

    }
}
