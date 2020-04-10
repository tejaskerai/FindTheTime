package Backendless;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;
import java.util.concurrent.ExecutionException;

import Models.Database.Activity;
import Models.Database.User;
import Models.Database.User_Activity;

import static com.backendless.media.rtp.RtpSocket.TAG;

public class UserRepository {


    public void createTable() {


    }


    @SuppressLint("StaticFieldLeak")
    public User getUserById(final String userId) {

        String whereClause = "objectId = '" + userId + "'";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        try {
            return new AsyncTask<String, Void, User>() {
                @Override
                protected User doInBackground(String... ids) {
                    List<User> results = Backendless.Data.of( User.class ).find( queryBuilder );
                    for (int i = 0; i < results.size(); i++) {
                        return results.get(i);
                    }
                    return null;
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
    public User getUserByEmail(String email) {

        String whereClause = "email = '" + email + "'";
        final DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        try {
            return new AsyncTask<String, Void, User>() {
                @Override
                protected User doInBackground(String... ids) {
                    List<User> results = Backendless.Data.of( User.class ).find( queryBuilder );
                    for (int i = 0; i < results.size(); i++) {
                        return results.get(i);
                    }
                    return null;
                }
            }.execute(email).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    public List<User> getAllUsers() {

        try {
            return new AsyncTask<String, Void, List<User>>() {
                @Override
                protected List<User> doInBackground(String... ids) {
                    List<User> results = Backendless.Data.of( User.class ).find();
                    for (int i = 0; i < results.size(); i++) {
                        return results;
                    }
                    return null;
                }
            }.execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    public User saveUser(String name, String email, String id) {

        final User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setId(id);

        try {
            return new AsyncTask<User, Void, User>() {
                @Override
                protected User doInBackground(User... users) {
                    return Backendless.Data.of(User.class).save(user);
                }
            }.execute(user).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }





    public void setRelation(List<User_Activity> user_activitiesCollection, User user){
        Backendless.Data.of( User.class ).addRelation( user, "user_activities:User_Activity:n", user_activitiesCollection,
                new AsyncCallback<Integer>()
                {
                    @Override
                    public void handleResponse( Integer response )
                    {
                        Log.i( "MYAPP", "relation has been set");
                    }

                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Log.e( "MYAPP", "server reported an error - " + fault.getMessage() );
                    }
                } );
    }


}
