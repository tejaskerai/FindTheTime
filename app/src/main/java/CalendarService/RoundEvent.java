package CalendarService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class RoundEvent {

    public int toNearestWholeHourForEnd(Calendar c) {
//        Calendar c = new GregorianCalendar();
//        c.setTime(d);

        if (c.get(Calendar.MINUTE) >= 30)
            c.add(Calendar.HOUR, 1);

        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
//        System.out.println("To nearest hour: " + c.getTime());
//        System.out.println("hour: " + c.get(Calendar.HOUR_OF_DAY));

        return c.get(Calendar.HOUR_OF_DAY);
    }

    public int toNearestWholeHourForStart(Calendar c) {
//        Calendar c = new GregorianCalendar();
//        c.setTime(d);

        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
//        System.out.println("To nearest hour: " + c.getTime());
//        System.out.println("hour: " + c.get(Calendar.HOUR_OF_DAY));

        return c.get(Calendar.HOUR_OF_DAY);
    }


    Calendar start = new GregorianCalendar();
    Calendar end = new GregorianCalendar();

    List<Integer> hours = new ArrayList<Integer>();


    public List<Integer> subtractHours(List<Integer> times, Date startT, Date endT){

        start.setTime(startT);
        end.setTime(endT);

        int startTime = toNearestWholeHourForStart(start); //start.get(Calendar.HOUR_OF_DAY);
        int endTime = toNearestWholeHourForEnd(end); //end.get(Calendar.HOUR_OF_DAY);
        int diff = endTime - startTime;
        for (int j = 0; j < diff + 1; j++) {
            hours.add(startTime + j);
        }
        // Subtract hours from the start array
        times.removeAll(hours);
        hours.clear();
        return times;
    }
}
