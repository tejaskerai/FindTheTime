package com.example.findthetime;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;


public class CreateNewActivity extends AppCompatActivity {

    /* UI & Debugging Variables*/
    Button createMovieActivity;
    Button createRestaurantActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_activity);

        initializeUI();


    }

    private void initializeUI(){
        createRestaurantActivity = findViewById(R.id.btn_restaurant_activity);
        createRestaurantActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent restaurantActivity = new Intent(CreateNewActivity.this, CreateRestaurantActivity.class);
                startActivity(restaurantActivity);
            }
        });


    }

}
