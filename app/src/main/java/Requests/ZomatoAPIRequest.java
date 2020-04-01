package Requests;

import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Models.Domain.Restaurant;

public class ZomatoAPIRequest extends AsyncTask<URL, Long, List<Restaurant>> {


    @Override
    protected List<Restaurant> doInBackground(URL... urls) {
        HttpURLConnection urlConnection = null;
        URL url1 = null;
        JSONObject object = null;
        JSONArray array = null;
        InputStream inStream = null;
        try {
            url1 = new URL(urls[0].toString());
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("user-key", "dded01546e797abd601af8f21c95e218");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null) {
                response += temp;
            }
            object = (JSONObject) new JSONTokener(response).nextValue();
            array = (JSONArray)  new JSONTokener(object.getString
                    ("restaurants")).nextValue();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    // this will close the bReader as well
                    inStream.close();
                } catch (IOException ignored) {
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        List<Restaurant> restaurantList = getRestaurants(array);
        return restaurantList;
    }

    private List<Restaurant> getRestaurants(JSONArray arr) {
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        try {
            for (int i = 0; i < arr.length(); i++) {

                JSONObject element = arr.getJSONObject(i);
                JSONObject restaurantJson = element.getJSONObject("restaurant");
                restaurantList.add(new Restaurant(restaurantJson.getString("name"),
                        restaurantJson.getString("url"),
                        restaurantJson.getString("timings"),
                        restaurantJson.getString("phone_numbers"),
                        restaurantJson.getJSONObject("location").getString("address")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return restaurantList;
    }
}

