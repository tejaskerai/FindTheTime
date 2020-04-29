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
import com.example.findthetime.Activities.AvailTimesList;
import com.example.findthetime.R;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Backendless.EventRepository;
import Models.CurrentUser;
import Models.Database.Activity;
import Models.Database.Event;
import Models.Database.User;

public class DatesListAdapter extends RecyclerView.Adapter<DatesListAdapter.MyViewHolder> {

    EventRepository eventRepository = new EventRepository();
    List<Date> dates;
    ArrayList<User> users;
    Context context;
    Activity activity;


    public DatesListAdapter(Context ct, List<Date> datesLst, ArrayList<User> usersLst, Activity activitychosen) {
        context = ct;
        dates = datesLst;
        users = usersLst;
        activity = activitychosen;
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


        DateFormat df = new SimpleDateFormat("E dd/MM/yy");
        String formattedDate = df.format(dates.get(position));
        holder.date.setText(formattedDate);
        final long epochTime = dates.get(position).getTime();
        final String date = String.valueOf(epochTime);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Set<Integer>> listsOfListofTimes = new ArrayList<>();
                for (int i = 0; i < users.size(); i++) {
                    System.out.println("Current Time in Epoch: " + epochTime);
                    List<Event> events = eventRepository.getEventByUserIdandDate(users.get(i).getObjectId(), date);
                    // This will run only once, as there will only be one element
                    for (int j = 0; j < events.size(); j++) {
                        String numbers = events.get(j).getAvailTimes();
                        System.out.println("Numbers: " + numbers);
                        // Converts string to list
                        Set<Integer> listOfTimes = new HashSet<>();
                        for (String field : numbers.split(", "))
                            listOfTimes.add(Integer.parseInt(field));
                        System.out.println("ListL " + listOfTimes);
                        listsOfListofTimes.add(listOfTimes);
                    }
                }


                System.out.println("List of list of times: "+ listsOfListofTimes);

                List<Event> events1 = eventRepository.getEventByUserIdandDate(CurrentUser.getCurrentUser().objectId, date);

                // This will run only once, as there will only be one element
                for (int i = 0; i < events1.size(); i++) {
                    String numbers = events1.get(i).getAvailTimes();
                    System.out.println("Numbers: " + numbers);
                    // Converts string to list
                    Set<Integer> listOfTimes = new HashSet<>();
                    for (String field : numbers.split(", "))
                        listOfTimes.add(Integer.parseInt(field));
                    System.out.println("ListL " + listOfTimes);
                    listsOfListofTimes.add(listOfTimes);
                }

                // Intersecting list of dates
                Set<Integer> intersection = listsOfListofTimes.get(0);
                for (Set<Integer> scan : listsOfListofTimes.subList(1, listsOfListofTimes.size())) {
                    intersection = Sets.intersection(intersection, scan);
                }
                List<Integer> times = Lists.newArrayList(intersection);
                System.out.println("Intersected list: " + times);

                Intent intent = new Intent(context, AvailTimesList.class);

                intent.putExtra("times", (Serializable) times);
                intent.putExtra("date",  dates.get(position));
                intent.putExtra("activity", (Serializable) activity);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView date;
        ConstraintLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.rowName);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
