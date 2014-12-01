package JsonClasses;

import java.util.ArrayList;

public class Events {
	
	@SuppressWarnings("unused")
	private String overallID = "getCalendar";
	ArrayList<Event> events = new ArrayList<Event>();
 
    public ArrayList<Event> getEvents() {
        return events;
    }
}