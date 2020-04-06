package Models.Database;

import java.util.ArrayList;
import java.util.List;

public class Activity {

    public String name;
    public String type;
    public String place;
    public String dateAndTime;
    public String creator;
    public List<User> users;
    public String objectId;


    public List getUsers() {
        return users;
    }

    public void setUsers(List users) {
        this.users = users;
    }


    public Activity() {
    }

    public Activity(String name, String type, String place, String dateAndTime, String creator) {
        this.name = name;
        this.type = type;
        this.place = place;
        this.dateAndTime = dateAndTime;
        this.creator = creator;
        this.users = new ArrayList<User>();

    }


    public void addUser(User user){
        if (users == null)
            users = new ArrayList<>();

        users.add(user);
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
