package JsonClasses;

import java.util.ArrayList;

public class Event {
	private String activityid;
	private String eventid;
	private String type;
	private String title;
	private String description;
	private String location;
	private String createdby;
	private ArrayList<String> start;
	private ArrayList<String> end;

	public Event(String activityid, String eventid, String type, String title,
			String description, String location, String createdby, ArrayList<String> start,
			ArrayList<String> end) {
		this.activityid = activityid;
		this.eventid = eventid;
		this.type = type;
		this.title = title;
		this.description = description;
		this.location = location;
		this.createdby = createdby;
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

	public String getTitle(){
		return title;
	}

	public String getDescription(){
		return description;
	}

	public String getLocation(){
		return location;
	}

	public String getCreatedby(){
		return createdby;
	}

	public ArrayList<String> getStart(){
		return start;
	}

	public ArrayList<String> getEnd(){
		return end;
	}
}
