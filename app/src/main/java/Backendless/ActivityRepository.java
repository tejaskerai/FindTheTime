package Backendless;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.User;

public class ActivityRepository {

    private static final String TAG = Initialisation.class.getSimpleName();


    @SuppressLint("StaticFieldLeak")
    public Activity createActivity(String name, String address) {

        final Activity activity = new Activity();
        activity.setName(name);
        //activity.setDateAndTime("09/03/2020");
        activity.setCreator(CurrentUser.getCurrentUser().objectId);
        activity.setPlace(address);
        //activity.addUser(CurrentUser.getCurrentUser());
        //final List saveActivities = new ArrayList<>();
        try {
            return new AsyncTask<Activity, Void, Activity>() {

                @Override
                protected Activity doInBackground(Activity... activities) {
                    return Backendless.Data.of(Activity.class).save(activity);
                }
            }.execute(activity).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    public Activity getActivity(final String id) {
        try {
            return new AsyncTask<String, Void, Activity>() {
                @Override
                protected Activity doInBackground(String... ids) {
                    List<Activity> activities = Backendless.Data.of(Activity.class).find();
                    for (int i = 0; i < activities.size(); i++) {
                        if (ids[0].equals(activities.get(i).getObjectId())) {
                            return activities.get(i);
                        } else {
                            return null;
                        }
                    }
                    return null;
                }
            }.execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
