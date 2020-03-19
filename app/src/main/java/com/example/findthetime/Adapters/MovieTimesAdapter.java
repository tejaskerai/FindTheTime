package com.example.findthetime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findthetime.Activities.MovieDetails;
import com.example.findthetime.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieTimesAdapter extends RecyclerView.Adapter<MovieTimesAdapter.MyViewHolder> {


    ArrayList<String> times;
    String cinema;
    String film;

    Context context;

    public MovieTimesAdapter(Context ct, ArrayList<String> movieTimes, String cinemaName, String filmName) {
        this.times = movieTimes;
        this.cinema = cinemaName;
        this.film = filmName;
        this.context = ct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new MovieTimesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTimesAdapter.MyViewHolder holder, final int position) {

        holder.time.setText(times.get(position));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetails.class);

                final HashMap<String, String> movieDetails = new HashMap<String, String>();

                movieDetails.put("filmName", film);
                movieDetails.put("cinemaName", cinema);
                movieDetails.put("filmTime", times.get(position));
                intent.putExtra("movieDetails", movieDetails);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return times.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView time;
        ConstraintLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.rowName);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
