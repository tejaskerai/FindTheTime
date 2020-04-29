package com.example.findthetime.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.findthetime.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import JSONService.OpenCageService;
import Models.Domain.Location;

public class CreateNewActivity extends AppCompatActivity {

    /* UI & Debugging Variables*/
    Button createMovieActivity;
    Button createRestaurantActivity;
    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_activity);
        initializeUI();

    }

    private void initializeUI() {

        createRestaurantActivity = findViewById(R.id.btn_restaurant_activity);
        createRestaurantActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mbuilder = new AlertDialog.Builder(CreateNewActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_postcode, null);
                final EditText mPostcode = (EditText) mView.findViewById(R.id.postcode);
                Button mDone = (Button) mView.findViewById(R.id.btn_done);
                mDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((!mPostcode.getText().toString().isEmpty()) && (postcodeChecker(mPostcode.getText().toString()))) {
                            Toast.makeText(CreateNewActivity.this,
                                    "successful",
                                    Toast.LENGTH_SHORT).show();
                            String normalisedPC = mPostcode.getText().toString().replaceAll("\\s", "").toLowerCase();
                            Location location = (Location) getApplicationContext();
                            OpenCageService openCageService = new OpenCageService();
                            List<Location> longLat = openCageService.getLongLat(normalisedPC);
                            Double lat = longLat.get(0).getLat();
                            Double lon = longLat.get(0).getLon();
                            location.setLat(lat);
                            location.setLon(lon);
                            Intent restaurantActivity = new Intent(CreateNewActivity.this, CreateRestaurantActivity.class);
                            startActivity(restaurantActivity);
                        } else {
                            Toast.makeText(CreateNewActivity.this,
                                    "Postcode is empty or is invalid",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mbuilder.setView(mView);
                AlertDialog dialog = mbuilder.create();
                dialog.show();

            }
        });

        createMovieActivity = findViewById(R.id.btn_movie_activity);
        createMovieActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent movies = new Intent(CreateNewActivity.this, CreateMovieActivity.class);
                startActivity(movies);
            }
        });

        home = (ImageView) findViewById(R.id.home_createActivity);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateNewActivity.this, Homepage.class);
                startActivity(intent);

            }
        });

    }

    public boolean postcodeChecker(String postcode) {

        String normalisedPC = postcode.replaceAll("\\s", "").toLowerCase();
        String regex = "^[a-z]{1,2}[0-9R][0-9a-z]?[0-9][abd-hjlnp-uw-z]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalisedPC);
        return matcher.matches();
    }

}

