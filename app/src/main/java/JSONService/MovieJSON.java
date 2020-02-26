package JSONService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Domain.Cinema;
import Models.Domain.Movie;

public class MovieJSON {

    public List<Movie> getMovies(JSONObject object){

        List<Movie> movieList = new ArrayList<Movie>();

        try {

            JSONArray films = object.getJSONArray("films");

            for (int i = 0; i < films.length(); i++){

                JSONObject element = films.getJSONObject(i);
                int filmId = element.getInt("film_id");
                String filmName = element.getString("film_name");
                JSONArray release_dates = element.getJSONArray("release_dates");
                JSONObject RDelement = release_dates.getJSONObject(0);
                String releaseDate = RDelement.getString("release_date");
                JSONArray age_rating = element.getJSONArray("age_rating");
                JSONObject ARelement = age_rating.getJSONObject(0);
                String ageRating = ARelement.getString("rating");
                String trailer = element.getString("film_trailer");
                String plot = element.getString("synopsis_long");

                movieList.add(new Movie(filmId, filmName, releaseDate, ageRating, trailer, plot));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;

    }

    public List<Cinema> getCinemas(JSONObject object){

        List<Cinema> cinemaList = new ArrayList<Cinema>();

        try {
            JSONArray cinemas = object.getJSONArray("cinemas");

            for (int i = 0; i < cinemas.length(); i++){

                JSONObject element = cinemas.getJSONObject(i);
                int cinemaId = element.getInt("cinema_id");
                String cinemaName = element.getString("cinema_name");
                JSONObject showings = element.getJSONObject("showings");
                JSONObject Standard = showings.getJSONObject("Standard");
                JSONArray times = Standard.getJSONArray("times");

                ArrayList<String> timeList = new ArrayList<>();
                for (int j = 0; j < times.length(); j++){

                    JSONObject elementTime = times.getJSONObject(j);
                    String start = elementTime.getString("start_time");
                    String end = elementTime.getString("end_time");
                    String startEnd = (start + " - " + end);
                    timeList.add(startEnd);
                }
                cinemaList.add(new Cinema(cinemaId, cinemaName,timeList));
            }
            return cinemaList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
