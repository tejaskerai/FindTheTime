package Models.Database;

import com.backendless.Backendless;

import java.util.ArrayList;
import java.util.List;

public class Activity {

    public String name;
    public String type;
    public String place;
    public String dateAndTime;
    public String creator;
    public Boolean pending;
    public List<User_Activity> user_activities;
    public String objectId;



    public Activity() {
    }

    public Activity(String name, String type, String place, String dateAndTime, String creator, Boolean pending) {
        this.name = name;
        this.type = type;
        this.place = place;
        this.dateAndTime = dateAndTime;
        this.creator = creator;
        this.pending = pending;
        this.user_activities = new ArrayList<User_Activity>();
    }

    public void addUserActivity(User_Activity userActivity){
        if (user_activities == null)
            user_activities = new ArrayList<>();

        user_activities.add(userActivity);
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}
