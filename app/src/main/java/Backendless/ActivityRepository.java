package Backendless;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.User_Activity;

public class ActivityRepository {

    private static final String TAG = Initialisation.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    public Activity createActivity(String name, String address) {

        final Activity activity = new Activity();
        activity.setName(name);
        activity.setDateAndTime(null);
        activity.setPending(Boolean.TRUE);
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
    public Activity getPendingActivityByActivityId(String activityId) {

        String whereClause = "objectId = '" + activityId + "' and pending = true";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        try {
            return new AsyncTask<String, Void, Activity>() {
                @Override
                protected Activity doInBackground(String... ids) {
                    List<Activity> results = Backendless.Data.of(Activity.class).find(queryBuilder);
                    for (int i = 0; i < results.size(); i++) {
                        return results.get(i);
                    }
                    return null;
                }
            }.execute(activityId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    public Activity getActivityByActivityId(String activityId) {

        String whereClause = "objectId = '" + activityId + "'";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        try {
            return new AsyncTask<String, Void, Activity>() {
                @Override
                protected Activity doInBackground(String... ids) {
                    List<Activity> results = Backendless.Data.of(Activity.class).find(queryBuilder);
                    for (int i = 0; i < results.size(); i++) {
                        return results.get(i);
                    }
                    return null;
                }
            }.execute(activityId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressLint("StaticFieldLeak")
    public List<Activity> getActivityByCreatorId(String userId) {

        String whereClause = "creator = '" + userId + "'";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        try {
            return new AsyncTask<String, Void, List<Activity>>() {
                @Override
                protected List<Activity> doInBackground(String... ids) {
                    List<Activity> results = Backendless.Data.of(Activity.class).find(queryBuilder);
                    return results;
                }
            }.execute(userId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    public List<Activity> getActivityPendingStatusByActivityId(String activityId) {

        String whereClause = "objectId = '" + activityId + "' and pending = true";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        try {
            return new AsyncTask<String, Void, List<Activity>>() {
                @Override
                protected List<Activity> doInBackground(String... ids) {
                    List<Activity> results = Backendless.Data.of(Activity.class).find(queryBuilder);
                    return results;
                }
            }.execute(activityId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setRelation(List<User_Activity> user_activitiesCollection, Activity activity) {
        Backendless.Data.of(Activity.class).addRelation(activity, "user_activities:User_Activity:n", user_activitiesCollection,
                new AsyncCallback<Integer>() {
                    @Override
                    public void handleResponse(Integer response) {
                        Log.i("MYAPP", "relation has been set");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e("MYAPP", "server reported an error - " + fault.getMessage());
                    }
                });
    }


    public void updatePendingStatus(final Activity activity, final Boolean val) {
        Backendless.Persistence.save(activity, new AsyncCallback<Activity>() {
            public void handleResponse(Activity saved_activity) {
                saved_activity.setPending(val);
                Backendless.Persistence.save(saved_activity, new AsyncCallback<Activity>() {
                    @Override
                    public void handleResponse(Activity response) {
                        System.out.println("updated");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        System.out.println("an error has occurred, the error code can be retrieved with fault.getCode()");
                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                System.out.println("an error has occurred, the error code can be retrieved with fault.getCode()");
            }
        });
    }


    public void setDateTime(final Activity activity, final Date date) {
        Backendless.Persistence.save(activity, new AsyncCallback<Activity>() {
            public void handleResponse(Activity saved_activity) {
                saved_activity.setDateAndTime(date);
                Backendless.Persistence.save(saved_activity, new AsyncCallback<Activity>() {
                    @Override
                    public void handleResponse(Activity response) {
                        System.out.println("updated");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        System.out.println("an error has occurred, the error code can be retrieved with fault.getCode()");
                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                System.out.println("an error has occurred, the error code can be retrieved with fault.getCode()");
            }
        });
    }
}
