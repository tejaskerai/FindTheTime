package Models;

public class CurrentUser {

    private CurrentUser(String email, String objectId, String authenticationToken) {
        this.email = email;
        this.objectId = objectId;
        this.authenticationToken = authenticationToken;
    }
    public String email;
    public String objectId;
    public String authenticationToken;


    private static CurrentUser _user;

    public static void setCurrentUser(String email, String objectId, String authenticationToken) {
        CurrentUser._user = new CurrentUser(email, objectId, authenticationToken);
    }

    public static CurrentUser getCurrentUser()
    {
        if (CurrentUser._user == null) {
            return null;
        }
        return CurrentUser._user;

    }
}
