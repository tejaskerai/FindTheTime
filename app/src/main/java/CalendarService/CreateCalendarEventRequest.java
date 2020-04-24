package CalendarService;

public class CreateCalendarEventRequest {

    String subject;
    String startDateTime;
    String endDateTime;
    String timeZone;


    public CreateCalendarEventRequest(String subject, String startDateTime, String endDateTime, String timeZone) {
        this.subject = subject;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.timeZone = timeZone;
    }



}
