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

import Requests.OpenCageAPIRequest;

public class OpenCageService {

    private String apiKey = "e877f67eae5f42bb829f22a90ab730b3";


    public List<Double> getLongLat(String placename) {
        //String url = "https://api.opencagedata.com/geocode/v1/json?q=" + placename + "&key=" + apiKey;
        //URL url1 = null;

        URL url = null;

        try {

            url = new URL("https://api.opencagedata.com/geocode/v1/json?q=" + placename + "&key=" + apiKey);

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }

        AsyncTask<URL, Long, List<Double>> result = new OpenCageAPIRequest().execute(url);
        List<Double> longLat = null;

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
