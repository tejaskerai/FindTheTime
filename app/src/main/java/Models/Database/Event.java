package Models.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

    Date date;
    String availTimes;
    public List<User> users;

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
}
