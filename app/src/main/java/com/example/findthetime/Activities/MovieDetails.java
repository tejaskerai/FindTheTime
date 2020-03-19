package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.findthetime.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MovieDetails extends AppCompatActivity {

    TextView filmNameT, cinemaNameT, timeT;

    Button continueButton;

    ImageView home;

    String filmName, cinemaName, time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initializeUI();

    }


    private void initializeUI() {

//        filmNameT = findViewById(R.id.restName);
//        cinemaNameT = findViewById(R.id.restAddress);
//        timeT = findViewById(R.id.restPhoneNumber);

        getData();
        //setData();

        // TODO change home id to match this page

//        home = findViewById(R.id.home_restaurantDetails);
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MovieDetails.this, Homepage.class);
//                startActivity(intent);
//
//            }
//        });


//        continueButton = findViewById(R.id.continueButton);
//        continueButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//
//                HashMap<String, String> movieDetails = new HashMap<String, String>();
//
//                movieDetails.put("filmName", filmName);
//                movieDetails.put("cinemaName", cinemaName);
//                movieDetails.put("time", time);
//
//                System.out.println("details :" + movieDetails);
//                Intent inviteUsers = new Intent(MovieDetails.this, InviteUsers.class);
//
//                startActivity(inviteUsers);
//
//            }
//        });


    }

    private void getData() {


        if (getIntent().hasExtra("movieDetails")){

            Intent intent = getIntent();

            HashMap<String, String> movieDetails = (HashMap<String, String>) intent.getSerializableExtra("movieDetails");

            filmName = movieDetails.get("filmName");
            cinemaName = movieDetails.get("cinemaName");
            time = movieDetails.get("filmTime");

            System.out.println("name: "+filmName);
            System.out.println("cinema: "+cinemaName);
            System.out.println("time: "+time);

        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {

        filmNameT.setText(filmName);
        cinemaNameT.setText(cinemaName);
        timeT.setText(time);

    }
}
