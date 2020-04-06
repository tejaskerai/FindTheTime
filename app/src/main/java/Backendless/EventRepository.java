package Backendless;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Models.Database.Event;
import Models.Database.User;

public class EventRepository {

    private static final String TAG = Initialisation.class.getSimpleName();


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

}
