package com.example.findthetime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import JSONService.ZomatoService;
import Models.Domain.Location;
import Models.Domain.Restaurant;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder> {

    List<String> restaurantName;
    Context context;



//    ZomatoService zomatoService = new ZomatoService();
//
//    List<Restaurant> restaurants = zomatoService.getRestaurants(cuisineId, lat, lon);

    public RestaurantListAdapter(Context ct, List<String> restName){

        context = ct;
        restaurantName = restName;

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

        holder.restName.setText(restaurantName.get(position));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantDetails.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView restName;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            restName = itemView.findViewById(R.id.restName);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
