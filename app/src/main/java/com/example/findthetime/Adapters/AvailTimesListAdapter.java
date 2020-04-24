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

import com.example.findthetime.Activities.ActivityOverview;
import com.example.findthetime.Activities.InvitedUsers;
import com.example.findthetime.R;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import Models.Database.Activity;

public class AvailTimesListAdapter extends RecyclerView.Adapter<AvailTimesListAdapter.MyViewHolder> {


    Context context;
    Date date;
    List<Integer> times;
    Activity activity;

    public AvailTimesListAdapter(Context ct, List<Integer> timesLst, Date chosenDate, Activity chosenActivity) {
        context = ct;
        times = timesLst;
        date = chosenDate;
        activity = chosenActivity;
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



        holder.time.setText(times.get(position).toString() + ":00");
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        //Todo: go to page to see activity details and add to calendar


        Intent intent = new Intent(context, ActivityOverview.class);
        intent.putExtra("date", date);
        intent.putExtra("time", times.get(position));
        intent.putExtra("activity", (Serializable) activity);
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
