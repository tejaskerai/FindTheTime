package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.backendless.Backendless;

import Backendless.EventRepository;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.findthetime.R;
import com.google.gson.JsonObject;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.ISingleAccountPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalServiceException;

import org.joda.time.DateTimeComparator;
import org.json.JSONObject;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import CalendarService.RoundEvent;
import JSONService.EventService;
import JSONService.UserService;
import Models.CurrentUser;
import Models.Database.Activity;
import Models.Domain.CalendarEvent;
import Models.Database.User;
import Backendless.Initialisation;
import Backendless.UserRepository;

import configurations.BackendlessConfig;

import com.google.common.collect.Sets;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();


    /* Azure AD v2 Configs */
    final static String AUTHORITY = "https://login.microsoftonline.com/common";


    /* UI & Debugging Variables */
    Button signInButton;

    /* Azure AD Variables */
    private ISingleAccountPublicClientApplication mSingleAccountApp;

    EventRepository eventRepository = new EventRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("In main activity");


//

        Backendless.initApp(this, BackendlessConfig.APPLICATION_ID, BackendlessConfig.API_KEY);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //mQueue = Volley.newRequestQueue(this);
        initializeUI();

        // Creates a PublicClientApplication object with res/raw/auth_config_single_account.json
        PublicClientApplication.createSingleAccountPublicClientApplication(MainActivity.this,
                R.raw.auth_config_single_account,
                new IPublicClientApplication.ISingleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(ISingleAccountPublicClientApplication application) {
                        /**
                         * This test app assumes that the app is only going to support one account.
                         * This requires "account_mode" : "SINGLE" in the config json file.
                         **/
                        mSingleAccountApp = application;
                        System.out.println("loading account");
                        //loadAccount();
                        signOut();
                    }

                    @Override
                    public void onError(MsalException exception) {
                        displayError(exception);
                    }
                });
    }


    private void initializeUI() {
        signInButton = findViewById(R.id.btn_signIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mSingleAccountApp == null) {
                    return;
                }

                mSingleAccountApp.signIn(MainActivity.this, null, getScopes(), getAuthInteractiveCallback());

            }

        });
    }

    private void displayError(@NonNull final Exception exception) {
        System.out.println("An error occurred on log in");
    }

    /**
     * Load the currently signed-in account, if there's any.
     */
    private void loadAccount() {
        if (mSingleAccountApp == null) {
            return;
        }

        mSingleAccountApp.getCurrentAccountAsync(new ISingleAccountPublicClientApplication.CurrentAccountCallback() {
            @Override
            public void onAccountLoaded(@Nullable IAccount activeAccount) {
                // You can use the account data to update your UI or your app database.
                updateUI(activeAccount);

            }

            @Override
            public void onAccountChanged(@Nullable IAccount priorAccount, @Nullable IAccount currentAccount) {
                if (currentAccount == null) {
                    // Perform a cleanup task as the signed-in account changed.
                    performOperationOnSignOut();
                }
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                displayError(exception);
            }
        });
    }

    /**
     * Updates UI based on the current account.
     */
    private void updateUI(@Nullable final IAccount account) {
        if (account != null) {
            openHomepage();
            System.out.println("Account has been loaded");
        } else {
            signInButton.setEnabled(true);

        }
    }

    public void openHomepage() {
        Intent homePage = new Intent(MainActivity.this, Homepage.class);

        startActivity(homePage);
    }


    /**
     * Extracts a scope array from a text field,
     * i.e. from "User.Read User.ReadWrite" to ["user.read", "user.readwrite"]
     */
    private String[] getScopes() {
        return "user.read calendars.readwrite".split(" ");
    }

    /**
     * Callback used for interactive request.
     * If succeeds we use the access token to call the Microsoft Graph.
     * Does not check cache.
     */
    private AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {

            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                Log.d(TAG, "Successfully authenticated");
                Log.d(TAG, "ID Token: " + authenticationResult.getAccount().getClaims().get("id_token"));

                System.out.println("1 graph");

                /* call graph */
                callGraphAPI(authenticationResult, "https://graph.microsoft.com/v1.0/me");

//                String startDate = "2020-04-10T06:00:00.000Z";
//                String endDate = "2020-04-20T06:00:00.000Z";

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
                Date startDate = new Date();
                System.out.println("start: " + formatter.format(startDate));


                // Gets the date 7 days after the start date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                Date endDate = calendar.getTime();
                System.out.println("End " + formatter.format(endDate));


                callGraphCalendarAPI(authenticationResult,
                        "https://graph.microsoft.com/v1.0/me/calendarview?startdatetime=" + formatter.format(startDate) + "&enddatetime=" + formatter.format(endDate), startDate, endDate);

                System.out.println("finish graph");
                System.out.println("2 update");
                /* Update account */
                updateUI(authenticationResult.getAccount());
                System.out.println("finish update");

            }

            @Override
            public void onError(MsalException exception) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());
                displayError(exception);

                if (exception instanceof MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception instanceof MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                }
            }

            @Override
            public void onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    private void performOperationOnSignOut() {
        final String signOutText = "Signed Out.";
        Toast.makeText(MainActivity.this, signOutText, Toast.LENGTH_SHORT)
                .show();
    }

    public void signOut() {

        if (mSingleAccountApp == null) {
            System.out.println("account is null");
            return;
        }

        /**
         * Removes the signed-in account and cached tokens from this app (or device, if the device is in shared mode).
         */
        mSingleAccountApp.signOut(new ISingleAccountPublicClientApplication.SignOutCallback() {
            @Override
            public void onSignOut() {
                updateUI(null);
                //nothing
                System.out.println("reached here");
                System.out.println("user has successfully logged out");
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                displayError(exception);
            }
        });
    }


    /**
     * Make an HTTP request to obtain MSGraph data
     */
    private void callGraphAPI(final IAuthenticationResult authenticationResult, final String url) {
        MSGraphRequestWrapper.callGraphAPIUsingVolley(
                MainActivity.this,
//                graphResourceTextView.getText().toString()
                url,
                authenticationResult.getAccessToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        /* Successfully called graph, process data and send to UI */

                        UserService userService = new UserService();
                        List<User> user = userService.getUser(response);
                        String name = user.get(0).getName();
                        String email = user.get(0).getEmail();
                        String id = user.get(0).getId();

                        System.out.println("name: " + name);
                        System.out.println("email: " + email);
                        System.out.println("id: " + id);

                        UserRepository userRepository = new UserRepository();

                        User userFound = userRepository.getUserByEmail(email);
                        if (userFound == null) {

                            User userCreated = userRepository.saveUser(name, email, id);
                            CurrentUser.setCurrentUser(
                                    userCreated.email,
                                    userCreated.objectId,
                                    authenticationResult);

                        } else {
                            CurrentUser.setCurrentUser(
                                    userFound.email,
                                    userFound.objectId,
                                    authenticationResult);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.toString());
                        displayError(error);
                    }
                });
    }


    private void callGraphAPI1(final IAuthenticationResult authenticationResult, final String url) {
        MSGraphRequestWrapper.callGraphAPIUsingVolley(
                MainActivity.this,
//                graphResourceTextView.getText().toString()
                url,
                authenticationResult.getAccessToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        /* Successfully called graph, process data and send to UI */

                        System.out.println(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.toString());
                        displayError(error);
                    }
                });
    }

    public void callGraphCalendarAPI(final IAuthenticationResult authenticationResult, String url, final Date start, final Date end) {
        MSGraphRequestWrapper.callGraphAPIUsingVolley(
                MainActivity.this,
//                graphResourceTextView.getText().toString()
                url,
                authenticationResult.getAccessToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        /* Successfully called graph, process data and send to UI */
                        EventService eventService = new EventService();
                        List<CalendarEvent> events = eventService.getEvent(response);

                        for (int i = 0; i < events.size(); i++) {
                            System.out.println("Start: " + events.get(i).getStart());
                            System.out.println("End: " + events.get(i).getEnd());
                        }

                        RoundEvent roundEvent = new RoundEvent();

                        // Initialise availTimes array
                        HashMap<Date, List<Integer>> availTimes = new HashMap<Date, List<Integer>>();

                        for (int i = 0; i < events.size(); i++) {

                            if (availTimes.containsKey(events.get(i).getStartDate()) == false) {

                                System.out.println("not in map");

                                // Initialise start array of times
                                List<Integer> startarr = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24));

                                List<Integer> hours = roundEvent.subtractHours(startarr, events.get(i).getStart(), events.get(i).getEnd());
                                availTimes.put(events.get(i).getStartDate(), hours);

                            } else {
                                if (events.get(i - 1).getStartDate().equals(events.get(i).getStartDate())) {

                                    List<Integer> hours = roundEvent.subtractHours(availTimes.get(events.get(i).getStartDate()), events.get(i).getStart(), events.get(i).getEnd());

                                    availTimes.put(events.get(i).getStartDate(), hours);
                                } else {
                                    // Initialise start array of times
                                    List<Integer> startarr = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24));

                                    List<Integer> hours = roundEvent.subtractHours(startarr, events.get(i).getStart(), events.get(i).getEnd());
                                    availTimes.put(events.get(i).getStartDate(), hours);
                                }
                            }
                        }

                        System.out.println(availTimes);


                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {

                            // Gets the date 1 days after the start date
                            List<Integer> startarr = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24));
                            Date endDate = formatter.parse(formatter.format(start));
                            int count = 0;

                            Date endDateWithNoTime = formatter.parse(formatter.format(end));

                            System.out.println("endDateWithNoTime: " + endDateWithNoTime);

                            System.out.println("enddate not added: " + endDate);

//                            Calendar calendar2 = Calendar.getInstance();
//                            calendar2.setTime(endDate);
//                            calendar2.add(Calendar.DAY_OF_YEAR, 1);
//                            endDate = formatter.parse(formatter.format(calendar2.getTime()));

                            System.out.println("enddate added: " + endDate);


                            if (endDateWithNoTime.equals(endDate)) {
                                System.out.println("dates are equal");
                            } else {
                                System.out.println("not equal");
                            }

                            while (!endDateWithNoTime.equals(endDate)) {
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(endDate);
                                calendar2.add(Calendar.DAY_OF_YEAR, 1);
                                endDate = formatter.parse(formatter.format(calendar2.getTime()));
                                System.out.println("in loop: " + endDate);
                                count++;
                                if (availTimes.containsKey(endDate)){
                                    System.out.println("already in");
                                }else{
                                    availTimes.put(endDate, startarr);
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        System.out.println("final: " + availTimes);

                        // Getting an iterator
                        Iterator hmIterator = availTimes.entrySet().iterator();
                        DateFormat df = new SimpleDateFormat("E dd/MM/yy");

//
//                        while (hmIterator.hasNext()) {
//                            Map.Entry mapElement = (Map.Entry)hmIterator.next();
//                            Date date = (Date)mapElement.getKey();
//                            List<Integer> times = (List<Integer>)mapElement.getValue();
//                            //System.out.println("Formatred: " + df.format(date));
//                            System.out.println("Times list: " + times);
//                            String joined = TextUtils.join(", ", times);
//                            System.out.println("String: " + joined);
//                            //eventRepository.createEvent(CurrentUser.getCurrentUser().objectId, date, joined);
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.toString());
                        displayError(error);
                    }
                });

    }


}
