package JSONService;


import androidx.annotation.NonNull;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CalendarJSON {

    private static String subject;

    public String getSubject() {
        return subject;
    }

    public String getStartTime() {
        return startTime;
    }

    public static void setStartTime(String startTime) {
        CalendarJSON.startTime = startTime;
    }

    private static String startTime;

    public String getEndTime() {
        return endTime;
    }

    public static void setEndTime(String endTime) {
        CalendarJSON.endTime = endTime;
    }

    private static String endTime;

    public static void setSubject(String name) {
        CalendarJSON.subject = subject;
    }

    public void parseSubject(@NonNull final JSONObject graphResponse) {
        try {
            JSONArray obj_Value = graphResponse.getJSONArray("value");

            JSONObject obj_ValueIndex = obj_Value.getJSONObject(1);
            subject = obj_ValueIndex.getString("subject");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setSubject(subject);
    }

    public void parseStart(@NonNull final JSONObject graphResponse) {
        try {
            JSONArray obj_Value = graphResponse.getJSONArray("value");

            JSONObject obj_ValueIndex = obj_Value.getJSONObject(1);
            JSONObject obj_ValueStart = obj_ValueIndex.getJSONObject("start");
            startTime = obj_ValueStart.getString("dateTime");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setStartTime(startTime);
    }

    public void parseEnd(@NonNull final JSONObject graphResponse) {
        try {
            JSONArray obj_Value = graphResponse.getJSONArray("value");

            JSONObject obj_ValueIndex = obj_Value.getJSONObject(1);
            JSONObject obj_ValueEnd = obj_ValueIndex.getJSONObject("end");
            startTime = obj_ValueEnd.getString("dateTime");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setEndTime(endTime);
    }

}