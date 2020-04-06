package Models;

import com.microsoft.identity.client.IAuthenticationResult;

public class CurrentUser {


    public String email;
    public String objectId;
    public IAuthenticationResult authenticationResult;

    private CurrentUser(String email, String objectId, IAuthenticationResult authenticationResult) {
        this.email = email;
        this.objectId = objectId;
        this.authenticationResult = authenticationResult;
    }


    private static CurrentUser _user;

    public static void setCurrentUser(String email, String objectId, IAuthenticationResult authenticationResult) {
        CurrentUser._user = new CurrentUser(email, objectId, authenticationResult);
    }

    public static CurrentUser getCurrentUser()
    {
        if (CurrentUser._user == null) {
            return null;
        }
        return CurrentUser._user;

    }
}
