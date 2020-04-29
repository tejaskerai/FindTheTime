package JSONService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Models.Domain.CalendarEvent;


public class EventService {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
    public List<CalendarEvent> getEvent (JSONObject response) {
        List<CalendarEvent> events = new ArrayList<>();
        try {
            JSONArray value = response.getJSONArray("value");

            for (int i = 0; i < value.length(); i++){
                JSONObject element = value.getJSONObject(i);
                JSONObject start = element.getJSONObject("start");
                String startDateTime = start.getString("dateTime");
                JSONObject end = element.getJSONObject("end");
                String endDateTime = end.getString("dateTime");
                try {
                    Date start1D = sdf.parse(startDateTime);
                    Date end1D = sdf.parse(endDateTime);
                    events.add(new CalendarEvent(start1D, end1D));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return events;
    }

}
