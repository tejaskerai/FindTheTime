package JSONService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Models.Domain.Movie;

public class MovieJSON {

    public List<Movie> getMovies(JSONObject object){



        List<Movie> movieList = new ArrayList<Movie>();

        try {

            JSONArray films = object.getJSONArray("films");

            for (int i = 0;i < films.length(); i++){

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

//            System.out.println("Film id: " + filmId);
//            System.out.println("Film name: " + filmName);
//            System.out.println("Release date: " + releaseDate);
//            System.out.println("Age rating: " + ageRating);
//            System.out.println("Trailer: " + trailer);
//            System.out.println("Plot: " + plot);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;

    }
}
