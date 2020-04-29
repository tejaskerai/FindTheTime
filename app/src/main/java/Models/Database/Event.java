package Models.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

    Date date;
    String availTimes;
    String userId;
    public List<User> users;
    public String objectId;

    public Event() {
    }

    public Event(Date date, String availTimes) {
        this.date = date;
        this.availTimes = availTimes;
    }

    public void addUser(User user){
        if (users == null)
            users = new ArrayList<>();

        users.add(user);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAvailTimes() {
        return availTimes;
    }

    public void setAvailTimes(String availTimes) {
        this.availTimes = availTimes;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
