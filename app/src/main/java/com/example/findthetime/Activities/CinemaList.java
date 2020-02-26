package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findthetime.Adapters.MovieCinemaAdapter;
import com.example.findthetime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import JSONService.MovieJSON;
import Models.Domain.Cinema;
import Models.Domain.Movie;

public class CinemaList extends AppCompatActivity {



    String filmName;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        recyclerView = findViewById(R.id.recyclerView);

        getData();

        List<Cinema> cinemas = readJsonfile();

        MovieCinemaAdapter movieCinemaAdapter = new MovieCinemaAdapter(this, cinemas);

        recyclerView.setAdapter(movieCinemaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public String getData(){

        if(getIntent().hasExtra("filmName")){

            filmName = getIntent().getStringExtra("filmName");

        }else{
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
        return filmName;
    }

    public List<Cinema> readJsonfile() {

        String json = null;

        try{
            String filmName = getData();
            InputStream is = getAssets().open(filmName+ ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject object = new JSONObject(json);
            MovieJSON movieJSON = new MovieJSON();
            System.out.println(movieJSON.getCinemas(object));
            List<Cinema> cinemas = movieJSON.getCinemas(object);

            return cinemas;
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }

}
