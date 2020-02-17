package JSONService;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Models.Domain.Restaurant;
import Requests.ZomatoAPIRequest;

public class ZomatoService {
    public List<Restaurant> getRestaurants(int id, String lat, String lon) {
        String url = "https://developers.zomato.com/api/v2.1/search?entity_type=zone&lat=" + lat + "&lon=" + lon + "&cuisines=" + id + "&sort=real_distance";
        URL url1 = null;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        AsyncTask<URL, Long, List<Restaurant>> result = new ZomatoAPIRequest().execute(url1);
        List<Restaurant> restaurantList = null;
        try {
            restaurantList = result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < restaurantList.size(); i++) {
            System.out.println(restaurantList.get(i).getName());
        }
        return restaurantList;
    }
}
