package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findthetime.R;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import CalendarService.PostRequest;
import Models.CurrentUser;
import Models.Database.Activity;

public class ActivityOverview extends AppCompatActivity {


    ImageView home;
    Button addToCalendar;
    Integer time;
    Date date;
    Activity activity;
    TextView activityName, activityDate, activityTime;

    String startDate;
    String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        initializeUI();
        getData();
        setData();
        getDates();

    }


    private void initializeUI() {

        activityName = findViewById(R.id.activityName);
        activityDate = findViewById(R.id.activityDate);
        activityTime = findViewById(R.id.activityTime);
        addToCalendar = findViewById(R.id.btn_add_to_calendar);
        home = (ImageView) findViewById(R.id.home_activity_overview);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityOverview.this, Homepage.class);
                startActivity(intent);

            }
        });



        addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject event = new JSONObject();
                JSONObject start = new JSONObject();

                JSONObject end = new JSONObject();

                try {
                    start.put("dateTime", "2020-04-21T07:00:00.000");
                    start.put("timeZone", "UTC");

                    end.put("dateTime", "2020-04-21T08:00:00.000");
                    end.put("timeZone", "UTC");

                    event.put("subject", "my event");
                    event.put("start", start);
                    event.put("end", end);

                } catch (JSONException e) {
                    e.printStackTrace();
                }




                System.out.println(CurrentUser.getCurrentUser().authenticationResult.getAccessToken());
                String eventJson = event.toString();

                try {
                    PostRequest postRequest =
                            new PostRequest("https://graph.microsoft.com/v1.0/me/events", event.toString())
                                    .setBearerToken(CurrentUser.getCurrentUser().authenticationResult.getAccessToken());

                    postRequest.execute().get();
                    Toast.makeText(ActivityOverview.this, "Event added", Toast.LENGTH_SHORT).show();

                } catch (IOException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }



                Intent intent= new Intent(ActivityOverview.this, Homepage.class);

                startActivity(intent);

                // Todo set pending to false
            }
        });

    }

    public void getData() {
        Intent intent = getIntent();
        if (getIntent().hasExtra("time") && getIntent().hasExtra("date") && getIntent().hasExtra("activity")) {
            time = (Integer) intent.getSerializableExtra("time");
            date = (Date) intent.getSerializableExtra("date");
            activity = (Activity) intent.getSerializableExtra("activity");
            System.out.println(time);
            System.out.println("Date picked activity overview: " + date);
            System.out.println("Activity picked activity overview: " + activity);
            System.out.println("Test: " + activity.getName());
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {

        activityName.setText(activity.getName());
        DateFormat df = new SimpleDateFormat("E dd/MM/yy");
        String formattedDate = df.format(date);
        activityDate.setText(formattedDate);
        activityTime.setText(time + ":00");

    }


    public void getDates(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH, day);
        start.set(Calendar.MONTH, month);
        start.set(Calendar.YEAR, year);
        start.set(Calendar.HOUR_OF_DAY, time);
        start.set(Calendar.MINUTE,0);
        start.set(Calendar.SECOND,0);
        start.set(Calendar.MILLISECOND,0);

        Date d = start.getTime();
        String formattedStart = sdf.format(d);
        System.out.println("Start date: " + formattedStart);
        startDate = formattedStart;

        //
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, day);
        end.set(Calendar.MONTH, month);
        end.set(Calendar.YEAR, year);
        end.set(Calendar.HOUR_OF_DAY, time+1);
        end.set(Calendar.MINUTE,0);
        end.set(Calendar.SECOND,0);
        end.set(Calendar.MILLISECOND,0);

        Date e = end.getTime();
        String formattedEnd = sdf.format(e);
        endDate = formattedEnd;
        System.out.println("End date: " + formattedEnd);
    }


}
