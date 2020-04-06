package Backendless;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.User_Activity;

public class UserActivityRepository {

    private static final String TAG = Initialisation.class.getSimpleName();


    public void createUserActivity(String userId, String activityId) {

        //for (int i = 0; i < userId.size(); i++){

        final User_Activity user_activity = new User_Activity();

        user_activity.setUserObjectId(userId);
        user_activity.setActivityObjectId(activityId);
        user_activity.setJoined(Boolean.FALSE);

        //}


        final List saveInvitedPeople = new ArrayList<>();

        Backendless.Data.of(User_Activity.class).save(user_activity, new AsyncCallback<User_Activity>() {
            @Override
            public void handleResponse(User_Activity saveUserActivity) {

                Log.i(TAG, "Saved User_activity");
                saveInvitedPeople.add(saveUserActivity);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Log.e(TAG, fault.getMessage());
            }
        });
    }
}
