package Backendless;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Models.Database.Activity;
import Models.Database.Event;
import Models.Database.User;

public class Initialisation {

    private static final String TAG = Initialisation.class.getSimpleName();


    // Method - Add Single Activity (bunch of users if u like)
    // Method - Add users to existing activities - (User user, Activity ac) ac.users.add(user)

    public void savedActivities(final User savedUser) {


        final Activity activity1 = new Activity();
        activity1.setName("Activity 1");
        activity1.setDateAndTime("09/03/2020");
        activity1.setPlace("London");
        activity1.setType("Movie");
        activity1.addUser(savedUser);

        final Activity activity2 = new Activity();
        activity2.setName("Activity 2");
        activity2.setDateAndTime("09/02/2020");
        activity2.setPlace("Manchester");
        activity2.setType("Restaurant");
        activity2.addUser(savedUser);


        final List savedActivities = new ArrayList<>();


        Backendless.Data.of(Activity.class).save(activity1, new AsyncCallback<Activity>() {
            @Override
            public void handleResponse(Activity savedActivity1) {
                Log.i(TAG, "First activity has been saved");
                savedActivities.add(savedActivity1);

                Backendless.Data.of(Activity.class).save(activity2, new AsyncCallback<Activity>() {
                    @Override
                    public void handleResponse(Activity savedActivity2) {

                        Log.i(TAG, "Second activity has been saved");
                        savedActivities.add(savedActivity2);

                        setUserToActivitiesRelation(savedUser, savedActivities);
                        setActivityToUsersRelation(activity1, activity1.users);
                        setActivityToUsersRelation(activity2, activity2.users);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e(TAG, fault.getMessage());
                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG, fault.getMessage());
            }
        });
    }


    public void saveEvents(final User savedUser) {


        final Event event = new Event();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");

        try {
            Date start1D = sdf.parse("2020-02-13T09:00:00.0000000");
            event.setDate(start1D);
            event.setAvailTimes("[5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]");

            event.addUser(savedUser);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        final List savedEvents = new ArrayList<>();


        Backendless.Data.of(Event.class).save(event, new AsyncCallback<Event>() {
            @Override
            public void handleResponse(Event savedEvent) {
                Log.i(TAG, "User has been saved");
                //savedActivities(savedUser);
                savedEvents.add(savedEvent);

                setUserToEventsRelation(savedUser, savedEvents);

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG, fault.getMessage());
            }
        });
    }



    private static void setUserToEventsRelation(User savedUser, List savedEvents) {
        Backendless.Data.of(User.class).addRelation(savedUser,
                "events:Event:n", savedEvents,
                new AsyncCallback() {

                    @Override
                    public void handleResponse(Object response) {
                        Log.i(TAG, "Relation has be set");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e(TAG, fault.getMessage());
                    }
                });
    }


    private static void setUserToActivitiesRelation(User savedUser, List savedActivites) {
        Backendless.Data.of(User.class).addRelation(savedUser,
                "activities:Activity:n", savedActivites,
                new AsyncCallback() {

                    @Override
                    public void handleResponse(Object response) {
                        Log.i(TAG, "Relation has be set");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e(TAG, fault.getMessage());
                    }
                });
    }

    private static void setActivityToUsersRelation(Activity savedActivity, List users) {
        Backendless.Data.of(Activity.class).addRelation(savedActivity,
                "users:User:n", users,
                new AsyncCallback() {

                    @Override
                    public void handleResponse(Object response) {
                        Log.i(TAG, "Relation has be set");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e(TAG, fault.getMessage());
                    }
                });
    }



}