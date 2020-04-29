package com.example.findthetime.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.findthetime.R;
import java.util.List;
import Models.Domain.Restaurant;

public class CreateRestaurantActivity extends AppCompatActivity {

    public static final String id = "id";

    /* UI & Debugging Variables */
    Button italian;
    Button chinese;
    Button mexican;
    Button american;
    Button japanese;
    Button indian;
    Button breakfast;
    Button greek;
    Button french;
    Button streetFood;
    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        initializeUI();

    }

    private void initializeUI() {

        home = (ImageView) findViewById(R.id.home_cuisines);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateRestaurantActivity.this, Homepage.class);
                startActivity(intent);

            }
        });

        italian = findViewById(R.id.btn_italian);
        italian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);
                int number = 55;
                intent.putExtra("id", number);
                startActivity(intent);

            }
        });

        chinese = findViewById(R.id.btn_chinese);
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);

                int number = 25;
                intent.putExtra("id", number);
                startActivity(intent);
            }
        });
        

        mexican = findViewById(R.id.btn_mexican);
        mexican.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Restaurant> restaurantList = zomatoService.getRestaurants(73, lat, lon);
//                printRest(restaurantList);

                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);

                int number = 73;
                intent.putExtra("id", number);
                startActivity(intent);
            }
        });

        american = findViewById(R.id.btn_american);
        american.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);

                int number = 1;
                intent.putExtra("id", number);
                startActivity(intent);
            }
        });


        japanese = findViewById(R.id.btn_japanese);
        japanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);

                int number = 25;
                intent.putExtra("id", number);
                startActivity(intent);
            }
        });

        indian = findViewById(R.id.btn_indian);
        indian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);

                int number = 148;
                intent.putExtra("id", number);
                startActivity(intent);
            }
        });

        breakfast = findViewById(R.id.btn_breakfast);
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);

                int number = 182;
                intent.putExtra("id", number);
                startActivity(intent);
            }
        });

        greek = findViewById(R.id.btn_greek);
        greek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);

                int number = 156;
                intent.putExtra("id", number);
                startActivity(intent);
            }
        });

        french = findViewById(R.id.btn_french);
        french.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);

                int number = 45;
                intent.putExtra("id", number);
                startActivity(intent);
            }
        });

        streetFood = findViewById(R.id.btn_street_food);
        streetFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(CreateRestaurantActivity.this, RestaurantList.class);

                int number = 90;
                intent.putExtra("id", number);
                startActivity(intent);
            }
        });


    }

    private void printRest(List<Restaurant> restaurants) {
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println(restaurants.get(i).getName());
            System.out.println(restaurants.get(i).getAddress());
            System.out.println(restaurants.get(i).getPhoneNumbers());
            System.out.println(restaurants.get(i).getTimings());
        }
    }

}
