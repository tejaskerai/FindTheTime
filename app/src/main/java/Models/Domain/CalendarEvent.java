package Models.Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarEvent {

    Date start;
    Date end;
    Date startDate;
    Date endDate;

    public CalendarEvent(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Date getDate(Date date) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime;

        {
            try {
                dateWithoutTime = sdf.parse(sdf.format(date));
                return dateWithoutTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Date getStartDate(){

        return getDate(getStart());
    }

    public Date getEndDate(){
        return getDate(getEnd());
    }

}
