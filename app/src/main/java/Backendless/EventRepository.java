package Backendless;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.Event;
import Models.Database.User;
import Models.Database.User_Activity;

public class EventRepository {

    private static final String TAG = Initialisation.class.getSimpleName();


    @SuppressLint("StaticFieldLeak")
    public Event createEvent(String userId, Date date, String timesLst) {

        final Event event = new Event();
        event.setUserId(userId);
        event.setDate(date);
        event.setAvailTimes(timesLst);

        try {

            return new AsyncTask<Event, Void, Event>() {
                @Override
                protected Event doInBackground(Event... events) {
                    return Backendless.Data.of(Event.class).save(event);
                }
            }.execute(event).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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


    @SuppressLint("StaticFieldLeak")
    public int deleteEvents(String userId){
        final String whereClause = "userId = '" + userId + "'";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        try {
            return new AsyncTask<String, Void, Integer>() {
                @Override
                protected Integer doInBackground(String... ids) {
                    int number = Backendless.Data.of( Event.class ).remove(whereClause);
                    return number;
                }
            }.execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
