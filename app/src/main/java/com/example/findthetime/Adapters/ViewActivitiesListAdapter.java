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

import com.example.findthetime.Activities.Homepage;
import com.example.findthetime.R;

import java.util.ArrayList;

import Models.Database.Activity;

public class ViewActivitiesListAdapter extends RecyclerView.Adapter<ViewActivitiesListAdapter.MyViewHolder>{


    ArrayList<Activity> activities;
    Context context;

    public ViewActivitiesListAdapter(Context ct, ArrayList<Activity> activitiesList) {
        context = ct;
        activities = activitiesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.activityName.setText(activities.get(position).getName());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        //Todo: If statement to direct user to correct screen
        //if user has not accepted activity
            // direct user to page to accept of decline activity


        //else
            //if activity is still pending
                // direct user to page saying they have already accepted/declined
            //else
                // direct user to activity overview page where they can add to calendar
        
        Intent intent = new Intent(context, Homepage.class);
        //intent.putExtra("filmName", movies.get(position).getFilmName());
        context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
         return activities.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView activityName;
        ConstraintLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.rowName);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
