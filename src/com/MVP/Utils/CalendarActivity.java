package com.MVP.Utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarActivity {
    private ZonedDateTime date;
    private String eventName;

    public CalendarActivity(ZonedDateTime date, String eventName) {
        this.date = date;
        this.eventName = eventName;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return eventName + "["+ date.format(formatter) + "]";
    }

    




    

}
