package Models.Database;

public class User_Activity {

    public String objectId;
    public String userObjectId;
    public String activityObjectId;
    public Boolean joined;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public String getActivityObjectId() {
        return activityObjectId;
    }

    public void setActivityObjectId(String activityObjectId) {
        this.activityObjectId = activityObjectId;
    }

    public Boolean getJoined() {
        return joined;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }
}
