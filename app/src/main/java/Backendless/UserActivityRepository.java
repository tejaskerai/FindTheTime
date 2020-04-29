package Backendless;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Models.Database.User_Activity;

public class UserActivityRepository {

    private static final String TAG = Initialisation.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    public List<User_Activity> getUserActivityByActivityId(String activityId) {

        String whereClause = "activityObjectId = '" + activityId + "'";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        try {
            return new AsyncTask<String, Void, List<User_Activity>>() {
                @Override
                protected List<User_Activity> doInBackground(String... ids) {
                    List<User_Activity> results = Backendless.Data.of(User_Activity.class).find(queryBuilder);

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

    @SuppressLint("StaticFieldLeak")
    public List<User_Activity> getUserActivityByUserId(String userId) {

        String whereClause = "userObjectId = '" + userId + "'";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        try {
            return new AsyncTask<String, Void, List<User_Activity>>() {
                @Override
                protected List<User_Activity> doInBackground(String... ids) {
                    List<User_Activity> results = Backendless.Data.of(User_Activity.class).find(queryBuilder);
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
    public List<User_Activity> getJoinedUsersActivity(String activityId) {

        String whereClause = "activityObjectId = '" + activityId + "' and joined = true";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        try {
            return new AsyncTask<String, Void, List<User_Activity>>() {
                @Override
                protected List<User_Activity> doInBackground(String... ids) {
                    List<User_Activity> results = Backendless.Data.of(User_Activity.class).find(queryBuilder);
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

    @SuppressLint("StaticFieldLeak")
    public List<User_Activity> getJoinedUserActivity(String activityId, String userId) {

        String whereClause = "activityObjectId = '" + activityId + "' and userObjectId = '" + userId + "' and joined = false";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        try {
            return new AsyncTask<String, Void, List<User_Activity>>() {
                @Override
                protected List<User_Activity> doInBackground(String... ids) {
                    List<User_Activity> results = Backendless.Data.of(User_Activity.class).find(queryBuilder);
                    return results;
                }
            }.execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateUserActivity(final User_Activity user_activity, final Boolean val) {
        Backendless.Persistence.save(user_activity, new AsyncCallback<User_Activity>() {
            public void handleResponse(User_Activity saved_user_activity) {
                saved_user_activity.setJoined(val);
                Backendless.Persistence.save(saved_user_activity, new AsyncCallback<User_Activity>() {
                    @Override
                    public void handleResponse(User_Activity response) {
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


    public void deleteUserActivity(User_Activity user_activity) {
        Backendless.Persistence.save(user_activity, new AsyncCallback<User_Activity>() {
            public void handleResponse(User_Activity savedUserActivity) {
                Backendless.Persistence.of(User_Activity.class).remove(savedUserActivity,
                        new AsyncCallback<Long>() {
                            public void handleResponse(Long response) {
                                System.out.println("Contact has been deleted");
                            }

                            public void handleFault(BackendlessFault fault) {
                                System.out.println("an error has occurred");
                            }
                        });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                System.out.println("an error has occurred, the error code can be retrieved with fault.getCode()");
            }
        });
    }

    public void createUserActivity(String userId, String activityId) {

        final User_Activity user_activity = new User_Activity();

        user_activity.setUserObjectId(userId);
        user_activity.setActivityObjectId(activityId);
        user_activity.setJoined(Boolean.FALSE);


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
