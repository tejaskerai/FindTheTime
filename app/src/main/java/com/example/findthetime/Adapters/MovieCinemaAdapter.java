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

import com.example.findthetime.Activities.CinemaList;
import com.example.findthetime.Activities.TimesList;
import com.example.findthetime.R;

import java.util.List;

import Models.Domain.Cinema;

public class MovieCinemaAdapter extends RecyclerView.Adapter<MovieCinemaAdapter.MyViewHolder>{

    List<Cinema> cinemas;
    String film;

    Context context;

    public MovieCinemaAdapter(Context ct, List<Cinema> cinemaList, String filmName) {

        this.context = ct;
        this.cinemas = cinemaList;
        this.film = filmName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.cinemaName.setText(cinemas.get(position).getCinemaName());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TimesList.class);

                intent.putExtra("cinemaName", cinemas.get(position).getCinemaName());
                intent.putExtra("filmName", film);
                intent.putStringArrayListExtra("times", cinemas.get(position).getTimes());

                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cinemaName;
        ConstraintLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cinemaName = itemView.findViewById(R.id.rowName);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
