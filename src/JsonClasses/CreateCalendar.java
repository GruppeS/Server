package JsonClasses;

import java.io.Serializable;

public class CreateCalendar implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String overallID = "createCalendar";
	private String calendarName;
	private String userName;
	private int publicOrPrivate;

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
	public int getPublicOrPrivate() {
		return publicOrPrivate;
	}
	public void setPublicOrPrivate(int publicPrivate) {
		this.publicOrPrivate = publicPrivate;
	}
}
