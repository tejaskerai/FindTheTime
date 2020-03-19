package JSONService;

import android.os.AsyncTask;
import android.widget.ScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Models.Domain.Location;
import Requests.OpenCageAPIRequest;

public class OpenCageService {

    private String apiKey = "e877f67eae5f42bb829f22a90ab730b3";


    public List<Location> getLongLat(String placename) {

        URL url = null;

        try {

            url = new URL("https://api.opencagedata.com/geocode/v1/json?q=" + placename + "&key=" + apiKey);

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }

        AsyncTask<URL, Long, List<Location>> result = new OpenCageAPIRequest().execute(url);
        List<Location> longLat = null;

        try {
            longLat = result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return longLat;
    }

}
