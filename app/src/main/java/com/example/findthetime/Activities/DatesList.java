package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.findthetime.Adapters.DatesListAdapter;
import com.example.findthetime.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Models.Database.Activity;
import Models.Database.User;

public class DatesList extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView home;
    TextView title;
    Activity activity;

    List<Date> dates;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        recyclerView = findViewById(R.id.recyclerView);
        home = (ImageView) findViewById(R.id.home_activityList);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DatesList.this, Homepage.class);
                startActivity(intent);
            }
        });
        title = findViewById(R.id.listTitle);
        String name = "Available dates";
        title.setText(name);

        getData();

        DatesListAdapter datesListAdapter = new DatesListAdapter(this, dates, users, activity);
        recyclerView.setAdapter(datesListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getData() {

        Intent intent = getIntent();
        if (getIntent().hasExtra("dates") && getIntent().hasExtra("users") && getIntent().hasExtra("activity")) {
            dates = (List<Date>) intent.getSerializableExtra("dates");
            users = (ArrayList<User>) intent.getSerializableExtra("users");

            activity = (Activity) intent.getSerializableExtra("activity");
            System.out.println("Users: " + users);
            System.out.println("Dates: " + dates);
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }
}
