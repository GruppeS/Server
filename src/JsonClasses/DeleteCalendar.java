package JsonClasses;

import java.io.Serializable;

public class DeleteCalendar implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private String overallID = "deleteCalendar";
	private String calendarName;
	private String userName;

	public String getOverallID() {
		return overallID;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
