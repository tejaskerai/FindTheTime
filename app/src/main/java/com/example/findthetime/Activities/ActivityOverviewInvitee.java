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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import Backendless.ActivityRepository;
import CalendarService.PostRequest;
import Models.CurrentUser;
import Models.Database.Activity;

public class ActivityOverviewInvitee extends AppCompatActivity {
    ImageView home;
    Button addToCalendar;
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
        addToCalendar();
    }

    public void getData() {
        Intent intent = getIntent();
        if (getIntent().hasExtra("activity")) {
            activity = (Activity) intent.getSerializableExtra("activity");
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeUI() {

        activityName = findViewById(R.id.activityName);
        activityDate = findViewById(R.id.activityDate);
        activityTime = findViewById(R.id.activityTime);
        addToCalendar = findViewById(R.id.btn_add_to_calendar);
        home = (ImageView) findViewById(R.id.home_activity_overview);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityOverviewInvitee.this, Homepage.class);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        activityName.setText(activity.getName());
        DateFormat df = new SimpleDateFormat("E dd/MM/yy");
        String formattedDate = df.format(activity.getDateAndTime());
        String formattedStart = sdf.format(activity.getDateAndTime());
        startDate = formattedStart;
        System.out.println("Start date: " + startDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activity.getDateAndTime());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int time = calendar.get(Calendar.HOUR_OF_DAY);

        //
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, day);
        end.set(Calendar.MONTH, month);
        end.set(Calendar.YEAR, year);
        end.set(Calendar.HOUR_OF_DAY, time + 1);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        Date e = end.getTime();
        String formattedEnd = sdf.format(e);
        endDate = formattedEnd;
        System.out.println("End date: " + endDate);

        String stringTime = Integer.toString(time);
        System.out.println("Time: " + stringTime);
        activityDate.setText(formattedDate);
        activityTime.setText(stringTime + ":00");

    }

    public void addToCalendar() {

        addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject event = new JSONObject();
                JSONObject start = new JSONObject();

                JSONObject end = new JSONObject();
                System.out.println("Start: " + startDate);
                System.out.println("end: " + endDate);

                try {
                    start.put("dateTime", startDate);
                    start.put("timeZone", "UTC");

                    end.put("dateTime", endDate);
                    end.put("timeZone", "UTC");

                    event.put("subject", activity.getName());
                    event.put("start", start);
                    event.put("end", end);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(CurrentUser.getCurrentUser().authenticationResult.getAccessToken());

                try {
                    PostRequest postRequest =
                            new PostRequest("https://graph.microsoft.com/v1.0/me/events", event.toString())
                                    .setBearerToken(CurrentUser.getCurrentUser().authenticationResult.getAccessToken());

                    postRequest.execute().get();
                    Toast.makeText(ActivityOverviewInvitee.this, "Event added", Toast.LENGTH_SHORT).show();

                } catch (IOException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                ActivityRepository activityRepository = new ActivityRepository();
                activityRepository.updatePendingStatus(activity, false);

                Intent intent = new Intent(ActivityOverviewInvitee.this, Homepage.class);

                startActivity(intent);
            }
        });

    }
}
