package CalendarService;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostRequest extends AsyncTask<String, Void, Void> {

    String endpoint;
    String jsonBody;
    Type typeOfReturn;
    HttpURLConnection con;

    public PostRequest(String endpoint, String jsonBody) throws IOException {
        this.endpoint = endpoint;
        this.jsonBody = jsonBody;
        URL url = null;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.con = (HttpURLConnection)url.openConnection();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Void doInBackground(String... data) {
        Gson gson = new Gson();

        try {
            setConnectionProperties();
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int code = con.getResponseCode();
            System.out.println("response code: " + code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PostRequest setBearerToken(String token) {
        con.setRequestProperty("Authorization", "Bearer " + token);
        return this;
    }

    private void setConnectionProperties() throws ProtocolException {
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
    }
}