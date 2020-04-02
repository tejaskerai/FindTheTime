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

import com.example.findthetime.Activities.DatesList;
import com.example.findthetime.Activities.InvitedUsers;
import com.example.findthetime.R;

public class DatesListAdapter extends RecyclerView.Adapter<DatesListAdapter.MyViewHolder> {


    Context context;

    public DatesListAdapter(Context ct) {
        context = ct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //holder.movieName.setText(movies.get(position).getFilmName());
//        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

        //Todo: go to page to view times avail for that date
        //Intent intent = new Intent(context, InvitedUsers.class);
        //intent.putExtra("filmName", movies.get(position).getFilmName());
        //context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        // return movies.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView movieName;
        ConstraintLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.rowName);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
