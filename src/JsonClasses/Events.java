package JsonClasses;

import java.util.ArrayList;

public class Events {
	
	static ArrayList<Event> events = new ArrayList<Event>();
 
    public ArrayList<Event> getEvents() {
        return events;
    }
    public void setEvents(ArrayList<Event> event) {
        this.events = event;
    }
}