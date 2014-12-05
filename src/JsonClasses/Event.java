package JsonClasses;

import java.util.ArrayList;
import java.util.Date;

public class Event {
	private String activityid;
	private String eventid;
	private String type;
	private String description;
	private String location;
	private String calendar;
	private ArrayList<String> start;
	private ArrayList<String> end;
	private Date startDate;
	private Date endDate;

	public Event(String activityid, String eventid, String type,
			String description, String location, ArrayList<String> start,
			ArrayList<String> end) {
		this.activityid = activityid;
		this.eventid = eventid;
		this.type = type;
		this.description = description;
		this.location = location;
		this.start = start;
		this.end = end;
	}

	public String getActivityid(){
		return activityid;
	}

	public String getEventid(){
		return eventid;
	}

	public String getType(){
		return type;
	}

	public String getDescription(){
		return description;
	}

	public String getLocation(){
		return location;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getStart() {
		return start;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getEnd() {
		return end;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}
	public String getCalendar() {
		return this.calendar;
	}
	
	public void setStartdate(Date start) {
		this.startDate = start;
	}
	public Date getStartdate(){
		return startDate;
	}

	public void setEnddate(Date end) {
		this.endDate = end;
	}
	public Date getEnddate(){
		return endDate;
	}
}
