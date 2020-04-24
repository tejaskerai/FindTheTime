package com.example.findthetime.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.findthetime.Adapters.CreatedActivitiesAdapter;
import com.example.findthetime.R;
import com.microsoft.identity.client.ISingleAccountPublicClientApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import CalendarService.MSGraph;
import Models.CurrentUser;

public class Homepage extends AppCompatActivity {

    private ISingleAccountPublicClientApplication mSingleAccountApp;


    /* UI & Debugging Variables */
    Button signOutButton;
    Button createActivity;
    Button viewCreatedActivities;
    Button viewActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        signOutButton = findViewById(R.id.btn_signOut);



        // TODO: Add sign out function from main activity
        // Use mSingleAccountApp.signOut() to sign the user out
        // You may also be required to redirect user back to the signin page manually
        // Call mSingleAccountApp.signOut() from a separate thread

        initializeUI();
    }

    private void initializeUI(){
        createActivity = findViewById(R.id.btn_create_activity);
        createActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newActivity = new Intent(Homepage.this, CreateNewActivity.class);
                startActivity(newActivity);
            }
        });

        viewCreatedActivities = findViewById(R.id.btn_view_created_activities);
        viewCreatedActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: change intent

                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
                final Date startDate = new Date();
                System.out.println("start: " + formatter.format(startDate));


                // Gets the date 7 days after the start date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                final Date endDate = calendar.getTime();


                // Get creators calendar events
//                MSGraph msGraph = new MSGraph();
//                msGraph.callGraphCalendarAPI(CurrentUser.getCurrentUser().authenticationResult, "https://graph.microsoft.com/v1.0/me/calendarview?startdatetime=" + formatter.format(startDate) + "&enddatetime=" + formatter.format(endDate), startDate, endDate, Homepage.this);

                Intent newActivity = new Intent(Homepage.this, ViewCreatedActivities.class);
                startActivity(newActivity);
            }
        });

        viewActivities = findViewById(R.id.btn_view_activities);
        viewActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: change intent
                Intent newActivity = new Intent(Homepage.this, ViewActivities.class);
                startActivity(newActivity);
            }
        });



        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                MainActivity mainActivity = new MainActivity();
//                mainActivity.setmSingleAccountApp(null);
                //mainActivity.signOut();
                System.out.println("sign out clicked");
                Intent login = new Intent(Homepage.this, MainActivity.class);
                startActivity(login);
            }
        });
    }

    /**
     * Display the error message
     */
    private void displayError(@NonNull final Exception exception) {
        System.out.println("Error occurred in sign out");
    }

    public void SignOut() {



    }
    public void update(){
        System.out.println("sign out from update");
    }
}
