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

import com.example.findthetime.Activities.AcceptOrDecline;
import com.example.findthetime.Activities.ActivityOverview;
import com.example.findthetime.Activities.ActivityStatus;
import com.example.findthetime.Activities.Homepage;
import com.example.findthetime.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Backendless.ActivityRepository;
import Backendless.UserActivityRepository;
import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.User_Activity;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.activityName.setText(activities.get(position).getName());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserActivityRepository userActivityRepository = new UserActivityRepository();
                List<User_Activity> user_activities =
                        userActivityRepository.getJoinedUserActivity(activities.get(position).getObjectId(), CurrentUser.getCurrentUser().objectId);
                System.out.println(user_activities.size());
                // Goes to accept or decline page if user has not joined
                if(user_activities.size() >= 1){
                    Intent intent = new Intent(context, AcceptOrDecline.class);
                    intent.putExtra("activity", (Serializable) activities.get(position));
                    context.startActivity(intent);
                }else{
                    ActivityRepository activityRepository = new ActivityRepository();
                    List<Activity> activitiesLst = activityRepository.getActivityPendingStatusByActivityId(activities.get(position).getObjectId());
                    //if activity is still pending
                    if (activitiesLst.size() >= 1){
                        // direct user to page saying they have already accepted/declined
                        Intent intent = new Intent(context, ActivityStatus.class);
                        intent.putExtra("activity", (Serializable) activities.get(position));
                        context.startActivity(intent);
                    }else{
                        // direct user to activity overview page where they can add to calendar
                        Intent intent = new Intent(context, ActivityOverview.class);
                        intent.putExtra("activity", (Serializable) activities.get(position));
                        context.startActivity(intent);
                    }
                }

        //if user has not accepted activity
            // direct user to page to accept of decline activity
        //else
            //if activity is still pending
                // direct user to page saying they have already accepted/declined
            //else
                // direct user to activity overview page where they can add to calendar


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
