package Models.Domain;

import java.util.ArrayList;
import java.util.List;

public class Cinema {

    int cinemaId;
    String cinemaName;
    ArrayList<String> times = new ArrayList<>();

    public Cinema(int cinemaId, String cinemaName, ArrayList<String> times) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.times = times;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public ArrayList<String> getTimes() {
        return times;
    }
}
