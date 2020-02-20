package Requests;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Models.Domain.Location;

public class OpenCageAPIRequest extends AsyncTask<URL, Long, List<Location>> {

    private static HttpURLConnection connection;

    BufferedReader reader;
    String line;
    StringBuffer responseContent = new StringBuffer();

    @Override
    protected List<Location> doInBackground(URL... urls) {

        URL url = null;

        try {

            url = new URL(urls[0].toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //object = (JSONObject) new JSONTokener(response).nextValue();
            //array = (JSONArray)  new JSONTokener(object.getString("restaurants")).nextValue();
        JSONObject object = null;
        try {
            object = new JSONObject(responseContent.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Location> longLat = getLongLat(object);


       // List<Restaurant> restaurantList = getRestaurants(array);

        return longLat;
    }

    private List<Location> getLongLat(JSONObject object) {


        JSONArray resultsArr = null;
        try {
            resultsArr = object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //for (int i = 0; i < restaurants.length(); i++) {

        List<Location> longLat = new ArrayList<Location>();

        try {

            JSONObject element = resultsArr.getJSONObject(0);
            JSONObject geometry = element.getJSONObject("geometry");
            Double lon = geometry.getDouble("lng");
            Double lat = geometry.getDouble("lat");

            longLat.add(new Location(lon,lat));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // }

        return longLat;
    }
}
