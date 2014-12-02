package JsonClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Event {
	private String activityid;
	private String eventid;
	private String type;
	private String title;
	private String description;
	private String location;
	private String createdby;
	private Date start;
	private Date end;

	public Event(String activityid, String eventid, String type, String title,
			String description, String location, String createdby, ArrayList<String> start,
			ArrayList<String> end, Date startdate, Date enddate) {
		this.activityid = activityid;
		this.eventid = eventid;
		this.type = type;
		this.title = title;
		this.description = description;
		this.location = location;
		this.createdby = createdby;

		if(start != null && end != null)
		{
			try {
				String tempStringStart = start.get(0)+"-"+start.get(2)+"-"+start.get(1)+" "+start.get(3)+":"+start.get(4);
				Date tempDateStart;

				tempDateStart = new SimpleDateFormat("yyyy-dd-MM HH:mm").parse(tempStringStart);
				this.start = tempDateStart;

				String tempStringEnd = end.get(0)+"-"+end.get(2)+"-"+end.get(1)+" "+end.get(3)+":"+end.get(4);
				Date tempDateEnd;

				tempDateEnd = new SimpleDateFormat("yyyy-dd-MM HH:mm").parse(tempStringEnd);
				this.end = tempDateEnd;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(startdate != null && enddate != null)
		{
			this.start = startdate;
			this.end = enddate;
		}
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

	public Date getStartdate(){
		return start;
	}

	public Date getEnddate(){
		return end;
	}
}
