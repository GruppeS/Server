package JsonClasses;

public class CalendarInfo {

	@SuppressWarnings("unused")
	private String overallID = "createCourse";
	private String activityID;
	private String eventID;
	private String type;
	private String title;
	private String description;
	private String start;
	private String end;
	private String location;

	public String getActivityID() {
		return activityID;
	}
	public  void setActivityID(String activityID) {
		this.activityID = activityID;
	}
	public  String getEventID() {
		return eventID;
	}
	public  void setEventID(String eventID) {
		this.eventID = eventID;
	}
	public  String getType() {
		return type;
	}
	public  void setType(String type) {
		this.type = type;
	}
	public  String getTitle() {
		return title;
	}
	public  void setTitle(String title) {
		this.title = title;
	}
	public  String getDescription() {
		return description;
	}
	public  void setDescription(String description) {
		this.description = description;
	}
	public  String getStart() {
		return start;
	}
	public  void setStart(String start) {
		this.start = start;
	}
	public  String getEnd() {
		return end;
	}
	public  void setEnd(String end) {
		this.end = end;
	}
	public  String getLocation() {
		return location;
	}
	public  void setLocation(String location) {
		this.location = location;
	}
}
