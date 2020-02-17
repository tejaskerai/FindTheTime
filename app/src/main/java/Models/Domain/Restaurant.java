package Models.Domain;

public class Restaurant {

    public Restaurant(String name, String url, String timings, String phoneNumbers, String address) {
        this.name = name;
        this.url = url;
        this.timings = timings;
        this.phoneNumbers = phoneNumbers;
        this.address = address;
    }

    String name;
    String url;
    String timings;
    String phoneNumbers;
    String address;

}
