package JSONService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Database.User;

public class UserService {

    String name;
    String email;
    String id;

    public List<User> getUser(JSONObject response){


        List<User> userDetail = new ArrayList<User>();

        try {
            name = response.getString("displayName");
            email = response.getString("userPrincipalName");
            id = response.getString("id");

            userDetail.add(new User(name, email, id));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userDetail;
    }


}
