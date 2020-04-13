package CalendarService;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.findthetime.Activities.MSGraphRequestWrapper;
import com.example.findthetime.Activities.MainActivity;
import com.microsoft.identity.client.IAuthenticationResult;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Backendless.UserRepository;
import JSONService.EventService;
import JSONService.UserService;
import Models.CurrentUser;
import Models.Database.User;
import Models.Domain.CalendarEvent;

public class MSGraph {

    private static final String TAG = MSGraph.class.getSimpleName();

    public void callGraphAPI(final IAuthenticationResult authenticationResult, final String url, Context ct) {
        MSGraphRequestWrapper.callGraphAPIUsingVolley(
                ct,
//                graphResourceTextView.getText().toString()
                url,
                authenticationResult.getAccessToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        /* Successfully called graph, process data and send to UI */
                        UserService userService = new UserService();
                        List<User> user = userService.getUser(response);
                        String name = user.get(0).getName();
                        String email = user.get(0).getEmail();
                        String id = user.get(0).getId();

                        System.out.println("name: " + name);
                        System.out.println("email: " + email);
                        System.out.println("id: " + id);

                        UserRepository userRepository = new UserRepository();

                        User userFound = userRepository.getUserByEmail(email);
                        if (userFound == null) {

                            User userCreated = userRepository.saveUser(name, email, id);
                            CurrentUser.setCurrentUser(
                                    userCreated.email,
                                    userCreated.objectId,
                                    authenticationResult);

                        } else {
                            CurrentUser.setCurrentUser(
                                    userFound.email,
                                    userFound.objectId,
                                    authenticationResult);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.toString());
                        displayError(error);
                    }
                });
    }




    public void callGraphCalendarAPI(final IAuthenticationResult authenticationResult, String url, final Date start, final Date end, Context ct) {
        MSGraphRequestWrapper.callGraphAPIUsingVolley(
                ct,
//                graphResourceTextView.getText().toString()
                url,
                authenticationResult.getAccessToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        /* Successfully called graph, process data and send to UI */
                        EventService eventService = new EventService();
                        List<CalendarEvent> events = eventService.getEvent(response);

                        for (int i = 0; i < events.size(); i++) {
                            System.out.println("Start: " + events.get(i).getStart());
                            System.out.println("End: " + events.get(i).getEnd());
                        }

                        RoundEvent roundEvent = new RoundEvent();

                        // Initialise availTimes array
                        HashMap<Date, List<Integer>> availTimes = new HashMap<Date, List<Integer>>();

                        for (int i = 0; i < events.size(); i++) {

                            if (availTimes.containsKey(events.get(i).getStartDate()) == false) {

                                System.out.println("not in map");

                                // Initialise start array of times
                                List<Integer> startarr = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24));

                                List<Integer> hours = roundEvent.subtractHours(startarr, events.get(i).getStart(), events.get(i).getEnd());
                                availTimes.put(events.get(i).getStartDate(), hours);

                            } else {
                                if (events.get(i - 1).getStartDate().equals(events.get(i).getStartDate())) {

                                    List<Integer> hours = roundEvent.subtractHours(availTimes.get(events.get(i).getStartDate()), events.get(i).getStart(), events.get(i).getEnd());

                                    availTimes.put(events.get(i).getStartDate(), hours);
                                } else {
                                    // Initialise start array of times
                                    List<Integer> startarr = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24));

                                    List<Integer> hours = roundEvent.subtractHours(startarr, events.get(i).getStart(), events.get(i).getEnd());
                                    availTimes.put(events.get(i).getStartDate(), hours);
                                }
                            }
                        }

                        System.out.println(availTimes);


                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {

                            // Gets the date 1 days after the start date
                            List<Integer> startarr = new ArrayList<Integer>(Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24));
                            Date endDate = formatter.parse(formatter.format(start));
                            int count = 0;

                            Date endDateWithNoTime = formatter.parse(formatter.format(end));

                            System.out.println("endDateWithNoTime: " + endDateWithNoTime);

                            System.out.println("enddate not added: " + endDate);

//                            Calendar calendar2 = Calendar.getInstance();
//                            calendar2.setTime(endDate);
//                            calendar2.add(Calendar.DAY_OF_YEAR, 1);
//                            endDate = formatter.parse(formatter.format(calendar2.getTime()));

                            System.out.println("enddate added: " + endDate);


                            if (endDateWithNoTime.equals(endDate)) {
                                System.out.println("dates are equal");
                            } else {
                                System.out.println("not equal");
                            }

                            while (!endDateWithNoTime.equals(endDate)) {
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(endDate);
                                calendar2.add(Calendar.DAY_OF_YEAR, 1);
                                endDate = formatter.parse(formatter.format(calendar2.getTime()));
                                System.out.println("in loop: " + endDate);
                                count++;
                                if (availTimes.containsKey(endDate)){
                                    System.out.println("already in");
                                }else{
                                    availTimes.put(endDate, startarr);
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        System.out.println("final: " + availTimes);

                        // Getting an iterator
                        Iterator hmIterator = availTimes.entrySet().iterator();
                        DateFormat df = new SimpleDateFormat("E dd/MM/yy");

//
//                        while (hmIterator.hasNext()) {
//                            Map.Entry mapElement = (Map.Entry)hmIterator.next();
//                            Date date = (Date)mapElement.getKey();
//                            List<Integer> times = (List<Integer>)mapElement.getValue();
//                            //System.out.println("Formatred: " + df.format(date));
//                            System.out.println("Times list: " + times);
//                            String joined = TextUtils.join(", ", times);
//                            System.out.println("String: " + joined);
//                            //eventRepository.createEvent(CurrentUser.getCurrentUser().objectId, date, joined);
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.toString());
                        displayError(error);
                    }
                });

    }



    private void displayError(@NonNull final Exception exception) {
        System.out.println("An error occurred on log in");
    }
}
