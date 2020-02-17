package ZomatoAPIService;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Domain.Restaurant;


public class ZomatoAPI {

    // static variable single_instance of type Singleton
    private static ZomatoAPI single_instance = null;


    private String apiKey = "dded01546e797abd601af8f21c95e218";


    String restName;

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    String urlVal;

    public String getUrl() {
        return urlVal;
    }

    public void setUrl(String url) {
        this.urlVal = url;
    }

    String timings;

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    String phoneNumbers;

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


//    public JsonObjectRequest getCuisine(String url){
//
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        //for (int i = 0; i < 1; i++) {
//
//                            try {
//                                JSONArray cuisines = response.getJSONArray("cuisines");
//                                JSONObject element = cuisines.getJSONObject(116);
//                                JSONObject cuisine = element.getJSONObject("cuisine");
//
//                                String cuisineName = cuisine.getString("cuisine_name");
//                                int cuisineId = cuisine.getInt("cuisine_id");
//
//
//                                System.out.println("Name: " + cuisineName + " Cuisine Id: " + cuisineId);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    //}
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Content-Type", "application/json");
//                headers.put("user-key", apiKey);
//                return headers;
//            }
//        };
//        return request;
//    }


    public List<Restaurant> getRestaurants(String id, final String url){
        JSONArray restaurants = MakeRequest(url);
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();

        try {
            for (int i=0; i<restaurants.length(); i++){

                JSONObject element = restaurants.getJSONObject(i);
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

//        final List<Restaurant> restaurants = new ArrayList<Restaurant>();
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject object) {
//                            try {
//                                JSONArray restaurantsJson = object.getJSONArray("restaurants");
//                                for (int i=0; i<restaurantsJson.length(); i++){
//
//                                    JSONObject element = restaurantsJson.getJSONObject(i);
//                                    JSONObject restaurantJson = element.getJSONObject("restaurant");
//                                    restaurants.add(new Restaurant(restaurantJson.getString("name"),
//                                            restaurantJson.getString("url"),
//                                            restaurantJson.getString("timings"),
//                                            restaurantJson.getString("phone_numbers"),
//                                            restaurantJson.getJSONObject("location").getString("address")));
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Content-Type", "application/json");
//                headers.put("user-key", apiKey);
//                return headers;
//            }
//        };
//        return request;
    }

    private JSONArray MakeRequest(String url) {
        HttpURLConnection urlConnection = null;
        URL url1 = null;
        JSONArray object = null;
        InputStream inStream = null;
        try {
            url1 = new URL(url.toString());
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("user-key", apiKey);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null) {
                response += temp;
            }
            object = (JSONArray) new JSONTokener(response).nextValue();
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
        return object;
    }
}
