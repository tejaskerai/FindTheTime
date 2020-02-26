package com.example.findthetime.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findthetime.R;

import java.util.ArrayList;

public class MovieTimesAdapter extends RecyclerView.Adapter<MovieTimesAdapter.MyViewHolder> {


    ArrayList<String> times;
    Context context;

    public MovieTimesAdapter(Context ct, ArrayList<String> movieTimes) {
        this.times = movieTimes;
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
    public void onBindViewHolder(@NonNull MovieTimesAdapter.MyViewHolder holder, int position) {

        holder.time.setText(times.get(position));

        // Set on click listener

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
