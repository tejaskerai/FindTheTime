package Backendless;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

import Models.Database.Activity;
import Models.Database.User;

public class Initialisation {

    private static final String TAG = Initialisation.class.getSimpleName();

    private static void setUserToActivitiesRelation(User savedUser, List savedActivities) {
        Backendless.Data.of(User.class).addRelation(savedUser,
                "activities:Activity:n", savedActivities,
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