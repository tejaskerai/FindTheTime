package com.example.findthetime.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.findthetime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import JSONService.MovieJSON;
import Models.Domain.Movie;

public class MovieList extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_movie_activity);


        initializeUI(readJsonfile());


    }

    private void initializeUI(List<Movie> movies) {


        LinearLayout layout = (LinearLayout) findViewById(R.id.rootLayout);
        for (int i = 0; i < movies.size(); i++) {

            final int count = i;

            System.out.println("Count: " + count);

//            newbtn = new Button(MovieList.this);
//            newbtn.setId(i+1);
//            newbtn.setText(movies.get(i).getFilmName());
//            newbtn.setTextSize(23);
//            int textColor = Color.parseColor("#FFFFFF");
//            newbtn.setTextColor(textColor);
//            newbtn.setWidth(1000);
//            newbtn.setHeight(350);
//            newbtn.setBackgroundResource(R.drawable.custom_button);
//
//            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            buttonLayoutParams.setMargins(8, 20, 8, 20);
//            newbtn.setLayoutParams(buttonLayoutParams);
//
//            //newbtn(newbtn, i);
//            layout.addView(newbtn);
        }

    }

//    public void newbtn(Button btn, int id){
//
//        final int no = id;
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(RestaurantList.this, RestaurantDetails.class);
//
//                System.out.println("Count in on click: " + no);
//                intent.putExtra("id", no);
//
//                startActivity(intent);
//            }
//        });
//
//    }

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
//            for (int i = 0;i < movies.size(); i++){
//
//                System.out.println("Film id: " + movies.get(i).getFilmId());
//                System.out.println("Film name: " + movies.get(i).getFilmName());
//                System.out.println("Release date: " + movies.get(i).getReleaseDate());
//                System.out.println("Age rating: " + movies.get(i).getAgeRating());
//                System.out.println("Trailer: " + movies.get(i).getTrailer());
//                System.out.println("Plot: " + movies.get(i).getPlot());
//                System.out.println(" ");
//            }

            return movies;
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
