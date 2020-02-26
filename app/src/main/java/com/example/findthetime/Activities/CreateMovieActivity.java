package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.findthetime.Adapters.MovieListAdapter;
import com.example.findthetime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import JSONService.MovieJSON;
import Models.Domain.Movie;

public class CreateMovieActivity extends AppCompatActivity {


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        recyclerView = findViewById(R.id.recyclerView);

        List<Movie> movies = readJsonfile();

        MovieListAdapter movieListAdapter = new MovieListAdapter(this, movies);

        recyclerView.setAdapter(movieListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private List<Movie> readJsonfile() {

        String json = null;

        try{
            InputStream is = getAssets().open("movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject object = new JSONObject(json);
            MovieJSON movieJSON = new MovieJSON();
            System.out.println(movieJSON.getMovies(object));
            List<Movie> movies = movieJSON.getMovies(object);

            return movies;
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
