package JsonClasses;

import java.util.ArrayList;

public class Events {
	
	private String overallID = "getCalendar";
	ArrayList<Event> events = new ArrayList<Event>();
 
    public ArrayList<Event> getEvents() {
        return events;
    }
}