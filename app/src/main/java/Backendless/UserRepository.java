package Backendless;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;
import java.util.concurrent.ExecutionException;

import Models.Database.User;

import static com.backendless.media.rtp.RtpSocket.TAG;

public class UserRepository {


    @SuppressLint("StaticFieldLeak")
    public User getUser(final String id) {
        try {
            return new AsyncTask<String, Void, User>() {
                @Override
                protected User doInBackground(String... ids) {
                    List<User> users = Backendless.Data.of(User.class).find();
                    for (int i = 0; i < users.size(); i++) {
                        if (ids[0].equals(users.get(i).getId())){
                            return users.get(i);
                        } else{
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
}
