package Models.Database;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String name;
    public String email;
    public String id;
    public List activities;
    public String objectId;

    public List getActivities() {
        return activities;
    }

    public void setActivities(List activities) {
        this.activities = activities;
    }

    public User() {

    }

    public User(String name, String email, String id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public void addActivity(Activity activity){
        if (activities == null)
            activities = new ArrayList<>();

        activities.add(activity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}


