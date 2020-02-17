package JSONService;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetailJSON {

    private static String name;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    private static String email;

    public static void setName(String name) {
        UserDetailJSON.name = name;
    }

    public static void setEmail(String email) {
        UserDetailJSON.email = email;
    }

    /**
     * Parse JSON response
     */
    public void parseName(@NonNull final JSONObject graphResponse) {
        try {
            name = graphResponse.getString("displayName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setName(name);
    }


    public void parseEmail(@NonNull final JSONObject graphResponse) {
        try {
            email = graphResponse.getString("userPrincipalName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setEmail(email);
    }


}
