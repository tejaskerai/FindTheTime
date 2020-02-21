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

import com.example.findthetime.R;
import com.example.findthetime.Activities.RestaurantDetails;

import java.util.List;

import Models.Domain.Restaurant;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder> {

    List<Restaurant> restaurants;
    Context context;

    public RestaurantListAdapter(Context ct, List<Restaurant> restaurantsList) {

        context = ct;

        restaurants = restaurantsList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rest_list_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.restName.setText(restaurants.get(position).getName());



        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantDetails.class);

                intent.putExtra("restName", restaurants.get(position).getName());
                intent.putExtra("restAddress", restaurants.get(position).getAddress());
                intent.putExtra("restPhoneNumber", restaurants.get(position).getPhoneNumbers());
                intent.putExtra("restTimings", restaurants.get(position).getTimings());
                intent.putExtra("restURL", restaurants.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView restName;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            restName = itemView.findViewById(R.id.restNameRow);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
