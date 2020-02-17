package ZomatoAPIService;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Domain.Restaurant;


public class ZomatoAPI {

    // static variable single_instance of type Singleton
    private static ZomatoAPI single_instance = null;

    // static method to create instance of Singleton class
    public static ZomatoAPI getInstance()
    {
        if (single_instance == null)
            single_instance = new ZomatoAPI();

        return single_instance;
    }


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


    public JsonObjectRequest getRestaurants(String id, final String url){
        final List<Restaurant> restaurants = new ArrayList<Restaurant>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
                            try {
                                JSONArray restaurantsJson = object.getJSONArray("restaurants");
                                for (int i=0; i<restaurantsJson.length(); i++){

                                    JSONObject element = restaurantsJson.getJSONObject(i);
                                    JSONObject restaurantJson = element.getJSONObject("restaurant");
                                    restaurants.add(new Restaurant(restaurantJson.getString("name"),
                                            restaurantJson.getString("url"),
                                            restaurantJson.getString("timings"),
                                            restaurantJson.getString("phone_numbers"),
                                            restaurantJson.getJSONObject("location").getString("address")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("user-key", apiKey);
                return headers;
            }
        };
        return request;
    }
}
