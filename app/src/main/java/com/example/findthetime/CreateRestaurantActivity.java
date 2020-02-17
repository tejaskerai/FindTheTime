package com.example.findthetime;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import ZomatoAPIService.ZomatoAPI;

public class CreateRestaurantActivity extends AppCompatActivity {


    public String restName;
    public String timings;
    public String phoneNumbers;
    public String urlVal;
    public String address;

    /* Location details*/
    String lat = "51.600941";
    String lon = "-0.285640";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        initializeUI();

    }


    private void initializeUI() {

        italian = findViewById(R.id.btn_italian);
        italian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ZomatoAPI zomatoAPI = new ZomatoAPI();

                String italianRestId = "55";

                String url = "https://developers.zomato.com/api/v2.1/search?entity_type=zone&lat=" + lat + "&lon=" + lon + "&cuisines=" + italianRestId + "&sort=real_distance";

                MySingleton.getInstance(CreateRestaurantActivity.this).addToRequestQueue(zomatoAPI.getRestaurants(italianRestId, url));




                System.out.println("From create activity: " + restName);

                Intent italianRest = new Intent(CreateRestaurantActivity.this, RestaurantDetails.class);

                italianRest.putExtra("restName", restName);
                italianRest.putExtra("urlVal", urlVal);
                italianRest.putExtra("phoneNumbers", phoneNumbers);
                italianRest.putExtra("timings", timings);
                italianRest.putExtra("address", address);

                startActivity(italianRest);

            }
        });


    }

}
